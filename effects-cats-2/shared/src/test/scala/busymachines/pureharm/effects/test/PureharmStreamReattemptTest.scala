/*
 * Copyright 2019 BusyMachines
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package busymachines.pureharm.effects.test

import busymachines.pureharm.anomaly._
import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._

import munit._

final class PureharmStreamReattemptTest extends CatsEffectSuite {

  implicit private val cs:    ContextShift[IO] = IO.contextShift(scala.concurrent.ExecutionContext.global)
  implicit private val timer: Timer[IO]        = IO.timer(scala.concurrent.ExecutionContext.global)

  import scala.concurrent.duration._

  private def failWhenEval(maxEmits: Long, numberOfStreams: Ref[IO, Int]): IO[(IO[Unit], Stream[IO, Long])] =
    for {
      queue <- Queue.noneTerminated[IO, Option[Throwable]]
      failWhen = queue.enqueue1(Option(Option(InconsistentStateCatastrophe("""Stopped the stream"""): Throwable)))
      _ <- Stream
        .awakeEvery[IO](10.millis)
        .zipWithIndex
        .evalMap(t =>
          if (t._2 >= maxEmits) {
            queue.enqueue1(Option.empty)
          }
          else {
            queue.enqueue1(Option(Option.empty))
          }
        )
        .compile
        .drain
        .start
        .void
    } yield (
      failWhen,
      Stream.eval(numberOfStreams.update(i => i + 1)).flatMap { _ =>
        queue.dequeue
          .evalMap {
            case None    => ().pure[IO]
            case Some(e) => e.raiseError[IO, Unit]
          }
          .zipWithIndex
          .map(t => t._2)
      },
    )

  test("reattempt does nothing when no error is thrown") {
    for {
      numberOfStreamsRef <- Ref.of[IO, Int](0)
      failWhen           <- failWhenEval(maxEmits = 10, numberOfStreams = numberOfStreamsRef)
      (_, stream) = failWhen
      attempt <- stream
        .reattempt(
          errorLog = (_, _) => IO(println("Failed stream"))
        )(
          retries        = 0,
          betweenRetries = 10.millis,
        )
        .compile
        .drain
        .attempt

      _ = assertEquals(attempt, ().pure[Attempt])

      nrOfStream <- numberOfStreamsRef.get
    } yield assertEquals(nrOfStream, 1)
  }

  test("reattempt does not try again if retries = 0") {
    for {
      numberOfStreamsRef <- Ref.of[IO, Int](0)
      t                  <- failWhenEval(maxEmits = 100, numberOfStreams = numberOfStreamsRef)
      (failStream, stream) = t
      fiber   <- stream
        .reattempt(
          errorLog = (_, _) => IO(println("Failed stream"))
        )(
          retries        = 0,
          betweenRetries = 10.millis,
        )
        .compile
        .drain
        .start
      _       <- failStream
      attempt <- fiber.join.attempt

      _ = intercept[InconsistentStateCatastrophe](attempt.unsafeGet())
      nrOfStream <- numberOfStreamsRef.get
    } yield assertEquals(nrOfStream, 1)
  }

  test("reattempt after one failure") {
    for {
      numberOfStreamsRef <- Ref.of[IO, Int](0)
      t                  <- failWhenEval(maxEmits = 10, numberOfStreams = numberOfStreamsRef)
      (failStream, stream) = t
      fiber <- stream
        .reattempt(
          errorLog = (_, _) => IO(println("Failed stream"))
        )(
          retries        = 10,
          betweenRetries = 10.millis,
        )
        .compile
        .drain
        .start
      _     <- failStream

      attempt <- fiber.join.attempt

      _ = assertEquals(attempt, ().pure[Attempt])
      nrOfStream <- numberOfStreamsRef.get
    } yield assertEquals(nrOfStream, 2)
  }

  test("reattempt after two failures") {
    for {
      numberOfStreamsRef <- Ref.of[IO, Int](0)
      t                  <- failWhenEval(maxEmits = 20, numberOfStreams = numberOfStreamsRef)
      (failStream, stream) = t
      fiber   <- stream
        .reattempt(
          errorLog = (_, _) => IO(println("Failed stream"))
        )(
          retries        = 10,
          betweenRetries = 10.millis,
        )
        .compile
        .drain
        .start
      _       <- failStream
      _       <- Timer[IO].sleep(100.millis)
      _       <- failStream
      attempt <- fiber.join.attempt

      _ = assertEquals(attempt, ().pure[Attempt])
      nrOfStreams <- numberOfStreamsRef.get
    } yield assertEquals(nrOfStreams, 3)
  }

  test("fail if we exceed reattempt count") {
    for {
      numberOfStreamsRef <- Ref.of[IO, Int](0)
      t                  <- failWhenEval(maxEmits = 20, numberOfStreams = numberOfStreamsRef)
      (failStream, stream) = t
      fiber <- stream
        .reattempt(
          errorLog = (_, s) => IO(println(s))
        )(
          retries        = 3,
          betweenRetries = 10.millis,
        )
        .evalMap(_ => failStream)
        .compile
        .drain
        .start

      attempt <- fiber.join.attempt

      _ = intercept[InconsistentStateCatastrophe](attempt.unsafeGet())
      nrOfStreams <- numberOfStreamsRef.get
    } yield assertEquals(nrOfStreams, 4)
  }
}
