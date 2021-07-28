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

import busymachines.pureharm.effects._
import busymachines.pureharm.effects.implicits._
import munit.CatsEffectSuite

import scala.concurrent.duration._

final class PureharmReattemptTest extends CatsEffectSuite {

  test("reattempt — succeed on first try") {
    for {
      firstTry <- Ref.of[IO, Int](0)
      _        <- succeedAfter(firstTry).reattempt(retries = 3, betweenRetries = 10.millis)
    } yield ()
  }

  test("reattempt — succeed after more than one try") {
    for {
      threeTries <- Ref.of[IO, Int](3)
      _          <- succeedAfter(threeTries).reattempt(retries = 3, betweenRetries = 10.millis)
    } yield ()

  }

  test("reattempt — fail after more than one try") {
    for {
      threeTries <- Ref.of[IO, Int](3)
      attempted  <- succeedAfter(threeTries).reattempt(retries = 2, betweenRetries = 10.millis).attempt
      _ = attempted match {
        case Left(e)  => assert(e.getMessage == "failed 1")
        case Right(_) => fail("expected failure")
      }
    } yield ()
  }

  private def succeedAfter(tries: Ref[IO, Int]): IO[Unit] =
    for {
      _          <- Temporal[IO].sleep(10.millis)
      triesSoFar <- tries.getAndUpdate(soFar => soFar - 1)
      _          <- IO(println(s"try countdown: #$triesSoFar"))
      _          <-
        if (triesSoFar <= 0) {
          IO.unit
        }
        else
          IO.raiseError(new RuntimeException(s"failed $triesSoFar"))
    } yield ()

}
