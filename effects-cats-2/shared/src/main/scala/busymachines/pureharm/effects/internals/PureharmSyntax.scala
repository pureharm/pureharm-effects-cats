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

package busymachines.pureharm.effects.internals

import busymachines.pureharm.anomaly._
import busymachines.pureharm.effects.Attempt

import cats._
import cats.implicits._
import cats.effect.{MonadThrow => _, ApplicativeThrow => _, _}
import scala.concurrent.duration._
import scala.concurrent._
import scala.util.control.NonFatal
import scala.collection.BuildFrom
import fs2._

/** @author
  *   Lorand Szakacs, https://github.com/lorandszakacs
  * @since 10
  *   May 2019
  */
object PureharmSyntax {

  trait Implicits {

    implicit final def pureharmFOps[F[_], A](fa: F[A]): FOps[F, A] = new FOps[F, A](fa)

    implicit final def pureharmFOptionOps[F[_], A](foa: F[Option[A]]): FOptionOps[F, A] = new FOptionOps[F, A](foa)
    implicit final def pureharmPureOptionOps[A](oa:     Option[A]):    PureOptionOps[A] = new PureOptionOps[A](oa)

    implicit final def pureharmPureAttemptOps[A](aa: Attempt[A]): PureAttemptOps[A] = new PureAttemptOps[A](aa)

    implicit final def pureharmFBooleanOps[F[_]](fb: F[Boolean]): FBooleanOps[F] = new FBooleanOps[F](fb)
    implicit final def pureharmPureBooleanOps(b:     Boolean):    PureBooleanOps = new PureBooleanOps(b)

    // implicit final def pureharmAnyFOps[F[_], A](fa: F[A]): AnyFOps[F, A] = new AnyFOps[F, A](fa)

    implicit final def pureharmFuturePseudoCompanionOps(c: Future.type): FuturePseudoCompanionOps =
      new FuturePseudoCompanionOps(c)

    implicit final def pureharmFutureReferenceEagerOps[A](f: Future[A]): FutureReferenceEagerOps[A] =
      new FutureReferenceEagerOps[A](f)

    implicit final def pureharmTimedAttemptReattemptSyntaxOPS[F[_], A](
      fa: F[A]
    ): PureharmTimedAttemptReattemptSyntaxOps[F, A] =
      new PureharmTimedAttemptReattemptSyntaxOps[F, A](fa)

    implicit final def pureharmStreamOps[F[_], A](stream: Stream[F, A]): PureharmStreamOps[F, A] =
      new PureharmStreamOps(stream)
  }

  //---------------------------- FOps ----------------------------

  final class FOps[F[_], A] private[PureharmSyntax] (val fa: F[A]) extends AnyVal {

    /** Wraps any non-anomaly as an UnhandledCatastrophe, this usually signals a bug, as pureharm encourages that any
      * throwables be such anomalies.
      */
    def attemptAnomaly(implicit F: ApplicativeThrow[F]): F[Either[AnomalyLike, A]] =
      F.map(
        F.attempt[A](fa)
      )(_.leftMap[AnomalyLike] {
        case a: AnomalyLike => a
        case NonFatal(e) => UnhandledCatastrophe(e): AnomalyLike
      })

  }

  //--------------------------- OPTION ---------------------------

  final class FOptionOps[F[_], A] private[PureharmSyntax] (val foa: F[Option[A]]) extends AnyVal {

    def flattenOption(ifNone: => Throwable)(implicit F: MonadThrow[F]): F[A] =
      foa.flatMap(_.liftTo[F](ifNone))

    def ifSomeRaise(ifSome: A => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      foa.flatMap {
        case None    => F.unit
        case Some(v) => F.raiseError(ifSome(v))
      }

    def ifSomeRaise_(ifSome: => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      foa.flatMap {
        case None    => F.unit
        case Some(_) => F.raiseError(ifSome)
      }

    def ifNoneRun(fu: F[Unit])(implicit F: Monad[F]): F[Unit] = foa.flatMap {
      case None    => fu
      case Some(_) => F.unit
    }

    def ifSomeRun_(fu: F[Unit])(implicit F: Monad[F]): F[Unit] = foa.flatMap {
      case None    => F.unit
      case Some(_) => fu
    }

    def ifSomeRun(fuf: A => F[Unit])(implicit F: Monad[F]): F[Unit] = foa.flatMap {
      case None    => F.unit
      case Some(a) => fuf(a)
    }
  }

  final class PureOptionOps[A] private[PureharmSyntax] (val oa: Option[A]) extends AnyVal {

    def ifSomeRaise[F[_]](ifSome: A => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      oa match {
        case None    => F.unit
        case Some(v) => F.raiseError(ifSome(v))
      }

    def ifSomeRaise[F[_]](ifSome: => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      oa match {
        case None    => F.unit
        case Some(_) => F.raiseError(ifSome)
      }

    def ifNoneRun[F[_]](fu: F[Unit])(implicit F: Monad[F]): F[Unit] = oa match {
      case None    => fu
      case Some(_) => F.unit
    }

    def ifSomeRun[F[_]](fu: F[Unit])(implicit F: Monad[F]): F[Unit] = oa match {
      case None    => F.unit
      case Some(_) => fu
    }

    def ifSomeRun[F[_]](fuf: A => F[Unit])(implicit F: Monad[F]): F[Unit] = oa match {
      case None    => F.unit
      case Some(a) => fuf(a)
    }
  }

  // //--------------------------- ATTEMPT ---------------------------

  final class PureAttemptOps[A] private[PureharmSyntax] (val fa: Attempt[A]) extends AnyVal {

    /** !!! Warning !!! Throws exceptions in your face
      *
      * @return
      */
    def unsafeGet(): A = fa match {
      case Left(e)  => throw e
      case Right(v) => v
    }
  }

  //--------------------------- BOOLEAN ---------------------------

  final class FBooleanOps[F[_]] private[PureharmSyntax] (val fb: F[Boolean]) extends AnyVal {

    def ifFalseRaise(ifFalse: => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      fb.ifM(ifTrue = F.unit, ifFalse = F.raiseError(ifFalse))

    def ifTrueRaise(ifFalse:  => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      fb.ifM(ifTrue = F.raiseError(ifFalse), ifFalse = F.unit)

    def ifFalseRun(fu:        F[Unit])(implicit F:      Monad[F]):      F[Unit] =
      fb.ifM(ifTrue = F.unit, ifFalse = fu)

    def ifTrueRun(fu:         F[Unit])(implicit F:      Monad[F]):      F[Unit] =
      fb.ifM(ifTrue = fu, ifFalse = F.unit)
  }

  final class PureBooleanOps private[PureharmSyntax] (val b: Boolean) extends AnyVal {

    def ifFalseRaise[F[_]](ifFalse: => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      if (b) F.unit else F.raiseError(ifFalse)

    def ifTrueRaise[F[_]](ifFalse: => Throwable)(implicit F: MonadThrow[F]): F[Unit] =
      if (b) F.raiseError(ifFalse) else F.unit

    def ifFalseRun[F[_]](fu: F[Unit])(implicit F: Monad[F]): F[Unit] =
      if (b) F.unit else fu

    def ifTrueRun[F[_]](fu: F[Unit])(implicit F: Monad[F]): F[Unit] =
      if (b) fu else F.unit
  }

  //--------------------------- Future --------------------------

  /** The scala standard library is extremely annoying because various effects don't have similar syntax for essentially
    * the same operation.
    */
  final class FuturePseudoCompanionOps private[PureharmSyntax] (val companion: Future.type) extends AnyVal {
    def pure[A](a: A): Future[A] = companion.successful(a)

    def raiseError[A](t: Throwable): Future[A] = companion.failed(t)

    //=========================================================================
    //=============================== Traversals ==============================
    //=========================================================================

    /** Similar to scala.concurrent.Future.traverse, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.traverse
      */
    @inline def traverse_[A, B](in: Seq[A])(fn: A => Future[B])(implicit ec: ExecutionContext): Future[Unit] =
      FutureOps.traverse_(in)(fn)

    /** Similar to scala.concurrent.Future.traverse, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.traverse
      */
    @inline def traverse_[A, B](in: Set[A])(fn: A => Future[B])(implicit ec: ExecutionContext): Future[Unit] =
      FutureOps.traverse_(in)(fn)

    /** Similar to scala.concurrent.Future.sequence, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.sequence
      */
    @inline def sequence_[A](in: Seq[Future[A]])(implicit ec: ExecutionContext): Future[Unit] =
      FutureOps.sequence_(in)

    /** Similar to scala.concurrent.Future.sequence, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.sequence
      */
    @inline def sequence_[A](in: Set[Future[A]])(implicit ec: ExecutionContext): Future[Unit] =
      FutureOps.sequence_(in)

    /** Syntactically inspired from Future.traverse, but it differs semantically insofar as this method does not attempt
      * to run any futures in parallel. "M" stands for "monadic", as opposed to "applicative" which is the foundation
      * for the formal definition of "traverse" (even though in Scala it is by accident-ish)
      *
      * For the vast majority of cases you should prefer this method over Future.sequence and Future.traverse, since
      * even small collections can easily wind up queuing so many Futures that you blow your execution context.
      *
      * Usage:
      * {{{
      *   import busymachines.pureharm.effects.implicits._
      *   val patches: Seq[Patch] = //...
      *
      *   //this ensures that no two changes will be applied in parallel.
      *   val allPatches: Future[Seq[Patch]] = Future.serialize(patches){ patch: Patch =>
      *     Future {
      *       //apply patch
      *     }
      *   }
      *   //... and so on, and so on!
      * }}}
      */
    @inline def serialize[A, B, C[X] <: IterableOnce[X]](col: C[A])(fn: A => Future[B])(implicit
      cbf:                                                    BuildFrom[C[A], B, C[B]],
      ec:                                                     ExecutionContext,
    ): Future[C[B]] = FutureOps.serialize(col)(fn)

    /** @see
      *   serialize
      *
      * Similar to serialize, but discards all content. i.e. used only for the combined effects.
      */
    @inline def serialize_[A, B, C[X] <: IterableOnce[X]](col: C[A])(fn: A => Future[B])(implicit
      cbf:                                                     BuildFrom[C[A], B, C[B]],
      ec:                                                      ExecutionContext,
    ): Future[Unit] = FutureOps.serialize_(col)(fn)
  }

  final class FutureReferenceEagerOps[A] private[PureharmSyntax] (val f: Future[A]) extends AnyVal {
    def unsafeRunSync(atMost: Duration = Duration.Inf): A = FutureOps.unsafeRunSync(f, atMost)
  }

  //---------------------------- Future Ops -------------------------------

  private[pureharm] object FutureOps {
    private val unitFunction: Any => Unit = _ => ()

    @inline def void[A](f: Future[A])(implicit ec: ExecutionContext): Future[Unit] = f.map(unitFunction)

    /** See Await.result
      */
    @inline def unsafeRunSync[A](f: Future[A], atMost: Duration = Duration.Inf): A = Await.result(f, atMost)

    /** Similar to scala.concurrent.Future.traverse, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.traverse
      */
    @inline def traverse_[A, B](
      in: Seq[A]
    )(fn: A => Future[B])(implicit executor: ExecutionContext): Future[Unit] =
      this.void(Future.traverse(in)(fn))

    /** Similar to scala.concurrent.Future.traverse, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.traverse
      */
    @inline def traverse_[A, B](
      in: Set[A]
    )(fn: A => Future[B])(implicit executor: ExecutionContext): Future[Unit] =
      this.void(Future.traverse(in)(fn))

    /** Similar to scala.concurrent.Future.sequence, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.sequence
      */
    @inline def sequence_[A, To](
      in:                Seq[Future[A]]
    )(implicit executor: ExecutionContext): Future[Unit] =
      this.void(Future.sequence(in))

    /** Similar to scala.concurrent.Future.sequence, but discards all content. i.e. used only for the combined effects.
      *
      * @see
      *   scala.concurrent.Future.sequence
      */
    @inline def sequence_[A, To](
      in:                Set[Future[A]]
    )(implicit executor: ExecutionContext): Future[Unit] =
      this.void(Future.sequence(in))

    /** Syntactically inspired from Future.traverse, but it differs semantically insofar as this method does not attempt
      * to run any futures in parallel. "M" stands for "monadic", as opposed to "applicative" which is the foundation
      * for the formal definition of "traverse" (even though in Scala it is by accident-ish)
      *
      * For the vast majority of cases you should prefer this method over Future.sequence and Future.traverse, since
      * even small collections can easily wind up queuing so many Futures that you blow your execution context.
      *
      * Usage:
      * {{{
      *   import busymachines.effects.async._
      *   val patches: Seq[Patch] = //...
      *
      *   //this ensures that no two changes will be applied in parallel.
      *   val allPatches: Future[Seq[Patch]] = Future.serialize(patches){ patch: Patch =>
      *     Future {
      *       //apply patch
      *     }
      *   }
      *   //... and so on, and so on!
      * }}}
      */
    @inline def serialize[A, B, M[X] <: IterableOnce[X]](
      in: M[A]
    )(fn: A => Future[B])(implicit bf: BuildFrom[M[A], B, M[B]], executor: ExecutionContext): Future[M[B]] = {
      import scala.collection.mutable
      if (in.iterator.isEmpty) {
        Future.successful(bf.newBuilder(in).result())
      }
      else {
        val seq  = in.iterator.to(Seq)
        val head = seq.head
        val tail = seq.tail
        val builder: mutable.Builder[B, M[B]] = bf.newBuilder(in)
        val firstBuilder = fn(head).map(z => builder.+=(z))
        val eventualBuilder: Future[mutable.Builder[B, M[B]]] = tail.foldLeft(firstBuilder) {
          (serializedBuilder: Future[mutable.Builder[B, M[B]]], element: A) =>
            serializedBuilder.flatMap[mutable.Builder[B, M[B]]] { (result: mutable.Builder[B, M[B]]) =>
              val f: Future[mutable.Builder[B, M[B]]] = fn(element).map(newElement => result.+=(newElement))
              f
            }
        }
        eventualBuilder.map(b => b.result())
      }
    }

    /** @see
      *   serialize
      *
      * Similar to serialize, but discards all content. i.e. used only for the combined effects.
      */
    @inline def serialize_[A, B, M[X] <: IterableOnce[X]](
      in: M[A]
    )(fn: A => Future[B])(implicit bf: BuildFrom[M[A], B, M[B]], executor: ExecutionContext): Future[Unit] =
      this.void(FutureOps.serialize(in)(fn))
  }

  //----------------------------- Timed Attempt ---------------------------------

  final class PureharmTimedAttemptReattemptSyntaxOps[F[_], A](val fa: F[A]) extends AnyVal {

    /** @param unit
      *   You probably don't want a granularity larger than MILLISECONDS for accurate timing.
      *
      * N.B. you can also use FiniteDuration.toCoarsest to then obtain a more human friendly measurement as possible
      * @return
      *   Never fails and captures the failure of the ``fa`` within the Attempt, times both success and failure case.
      */
    def timedAttempt(
      unit:       TimeUnit = MILLISECONDS
    )(implicit F: MonadThrow[F], timer: Timer[F]): F[(FiniteDuration, Attempt[A])] =
      PureharmTimedAttemptReattemptSyntaxOps.timedAttempt(unit)(fa)

    /** Runs an effect ``F[A]`` a maximum of ``retries`` time, until it is not failed. Between each retry it waits
      * ``betweenRetries``. It also measures the time elapsed in total.
      *
      * @param errorLog
      *   Use this to specify how to log any error that happens within your ``fa``, and any error encountered during
      *   retries
      * @param timeUnit
      *   You probably don't want a granularity larger than MILLISECONDS for accurate timing.
      *
      * N.B. you can also use FiniteDuration.toCoarsest to then obtain a more human friendly measurement as possible
      * @return
      *   Never fails and captures the failure of the ``fa`` within the Attempt, times all successes and failures, and
      *   returns their sum. N.B. It only captures the latest failure, if it encounters one.
      */
    def timedReattempt(
      errorLog:       (Throwable, String) => F[Unit],
      timeUnit:       TimeUnit,
    )(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(implicit
      F:              MonadThrow[F],
      timer:          Timer[F],
    ): F[(FiniteDuration, Attempt[A])] =
      PureharmTimedAttemptReattemptSyntaxOps.timedReattempt(errorLog, timeUnit)(retries, betweenRetries)(fa)

    /** Same as overload timedReattempt, but does not report any failures.
      */
    def timedReattempt(
      timeUnit:       TimeUnit
    )(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(implicit
      F:              MonadThrow[F],
      timer:          Timer[F],
    ): F[(FiniteDuration, Attempt[A])] =
      PureharmTimedAttemptReattemptSyntaxOps
        .timedReattempt(PureharmTimedAttemptReattemptSyntaxOps.noLog[F], timeUnit)(retries, betweenRetries)(fa)

    /** Runs an effect ``F[A]`` a maximum of ``retries`` time, until it is not failed. Between each retry it waits
      * ``betweenRetries``. It also measures the time elapsed in total.
      *
      * @param errorLog
      *   Use this to specify how to log any error that happens within your ``fa``, and any error encountered during
      *   retries
      *
      * N.B. you can also use FiniteDuration.toCoarsest to then obtain a more human friendly measurement as possible
      * @return
      *   N.B. It only captures the latest failure, if it encounters one.
      */
    def reattempt(
      errorLog:       (Throwable, String) => F[Unit]
    )(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(implicit
      F:              MonadThrow[F],
      timer:          Timer[F],
    ): F[A] =
      PureharmTimedAttemptReattemptSyntaxOps.reattempt(errorLog)(retries, betweenRetries)(fa)

    /** Same semantics as overload reattempt but does not report any error
      */
    def reattempt(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(implicit
      F:              MonadThrow[F],
      timer:          Timer[F],
    ): F[A] =
      PureharmTimedAttemptReattemptSyntaxOps.reattempt(retries, betweenRetries)(fa)
  }

  object PureharmTimedAttemptReattemptSyntaxOps {
    import scala.concurrent.duration._

    /** @param timeUnit
      *   You probably don't want a granularity larger than MILLISECONDS for accurate timing.
      *
      * N.B. you can also use FiniteDuration.toCoarsest to then obtain a more human friendly measurement as possible
      * @return
      *   Never fails and captures the failure of the ``fa`` within the Attempt, times both success and failure case.
      */
    def timedAttempt[F[_], A](
      timeUnit:   TimeUnit
    )(
      fa:         F[A]
    )(implicit F: MonadThrow[F], timer: Timer[F]): F[(FiniteDuration, Attempt[A])] =
      for {
        start <- realTime(timeUnit)(F, timer)
        att   <- fa.attempt
        end   <- realTime(timeUnit)(F, timer)
      } yield (end.minus(start), att)

    /** Runs an effect ``F[A]`` a maximum of ``retries`` time, until it is not failed. Between each retry it waits
      * ``betweenRetries``. It also measures the time elapsed in total.
      *
      * @param errorLog
      *   Use this to specify how to log any error that happens within your ``fa``, and any error encountered during
      *   retries
      * @param timeUnit
      *   You probably don't want a granularity larger than MILLISECONDS for accurate timing.
      *
      * N.B. you can also use FiniteDuration.toCoarsest to then obtain a more human friendly measurement as possible
      * @return
      *   Never fails and captures the failure of the ``fa`` within the Attempt, times all successes and failures, and
      *   returns their sum. N.B. It only captures the latest failure, if it encounters one.
      */
    def timedReattempt[F[_]: MonadThrow: Timer, A](
      errorLog:       (Throwable, String) => F[Unit],
      timeUnit:       TimeUnit,
    )(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(
      fa:             F[A]
    ): F[(FiniteDuration, Attempt[A])] = {
      def recursiveRetry(fa: F[A])(rs: Int, soFar: FiniteDuration): F[(FiniteDuration, Attempt[A])] =
        for {
          timedAtt <- this.timedAttempt[F, A](timeUnit)(fa)
          newSoFar = soFar.plus(timedAtt._1)
          v <- timedAtt._2 match {
            case Right(v) => (newSoFar, v.pure[Attempt]).pure[F]
            case Left(e)  =>
              if (rs > 0) {
                for {
                  _     <- errorLog(
                    e,
                    s"effect failed: ${e.getMessage}. retries left=$rs, but first waiting: $betweenRetries",
                  )
                  _     <- Timer[F].sleep(betweenRetries)
                  value <- recursiveRetry(fa)(rs - 1, newSoFar.plus(betweenRetries))
                } yield value: (FiniteDuration, Attempt[A])
              }
              else {
                errorLog(e, s"all retries failed, abandoning.") >>
                  (newSoFar, e.raiseError[Attempt, A]).pure[F]
              }
          }
        } yield v

      recursiveRetry(fa)(retries, 0.seconds)
    }

    /** Runs an effect ``F[A]`` a maximum of ``retries`` time, until it is not failed. Between each retry it waits
      * ``betweenRetries``. It also measures the time elapsed in total.
      *
      * @param errorLog
      *   Use this to specify how to log any error that happens within your ``fa``, and any error encountered during
      *   retries
      *
      * N.B. you can also use FiniteDuration.toCoarsest to then obtain a more human friendly measurement as possible
      * @return
      *   N.B. It only captures the latest failure, if it encounters one.
      */
    def reattempt[F[_]: MonadThrow: Timer, A](
      errorLog:       (Throwable, String) => F[Unit]
    )(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(
      fa:             F[A]
    ): F[A] =
      this.timedReattempt(errorLog, NANOSECONDS)(retries, betweenRetries)(fa).map(_._2).rethrow

    /** Same semantics as overload reattempt but does not report any error
      */
    def reattempt[F[_]: MonadThrow: Timer, A](
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(
      fa:             F[A]
    ): F[A] =
      this.timedReattempt(noLog[F], NANOSECONDS)(retries, betweenRetries)(fa).map(_._2).rethrow

    private def noLog[F[_]: Applicative]: (Throwable, String) => F[Unit] =
      (_, _) => Applicative[F].unit

    //find appropriate util package for this... looks useful...
    private def realTime[F[_]: Applicative: Timer](unit: TimeUnit): F[FiniteDuration] =
      Timer[F].clock.realTime(unit).map(tl => FiniteDuration(tl, unit))

  }

  final class PureharmStreamOps[F[_], A](val stream: Stream[F, A]) {

    def reattempt(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(implicit F:     MonadThrow[F], timer: Timer[F]): Stream[F, A] =
      this.reattempt(noLog[F])(retries, betweenRetries)

    def reattempt(
      errorLog:       (Throwable, String) => F[Unit]
    )(
      retries:        Int,
      betweenRetries: FiniteDuration,
    )(implicit
      F:              MonadThrow[F],
      timer:          Timer[F],
    ): Stream[F, A] =
      stream.recoverWith { case e: Throwable =>
        if (retries > 0) {
          Stream
            .eval(
              errorLog(
                e,
                s"Stream failed w/: ${e.getMessage}. retries left=$retries, but first waiting: $betweenRetries",
              ) *> timer.sleep(betweenRetries)
            )
            .flatMap(_ => reattempt(errorLog)(retries - 1, betweenRetries))
        }
        else {
          Stream
            .eval(
              errorLog(
                e,
                s"Stream failed even after exhausting all retries w/: ${e.getMessage}.",
              ) *> timer.sleep(betweenRetries)
            )
            .flatMap(_ => e.raiseError[Stream[F, *], A])

        }
      }

    private def noLog[F[_]: Applicative]: (Throwable, String) => F[Unit] =
      (_, _) => Applicative[F].unit
  }

}
