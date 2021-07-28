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

package busymachines.pureharm.effects.aliases

import busymachines.pureharm.effects

/** @author
  *   Lorand Szakacs, https://github.com/lorandszakacs
  * @since 13
  *   Jun 2019
  */
trait PureharmEffectsTypeDefinitions {

  //----------- handy custom types -----------
  type Attempt[+R] = effects.Attempt[R]

  type AttemptT[F[_], R] = effects.AttemptT[F, R]
  val AttemptT: effects.AttemptT.type = effects.AttemptT

  /** Useful since we don't have partial kind application by default Usage:
    * {{{
    *   def canFail[F[_]: ApplicativeAttempt, T](p1: T) : F[T] = ???
    * }}}
    */
  @scala.deprecated("Use ApplicativeThrow instead", "0.1.0")
  type ApplicativeAttempt[F[_]] = cats.ApplicativeError[F, Throwable]

  /** Useful since we don't have partial kind application by default Usage:
    * {{{
    *   def canFail[F[_]: MonadAttempt, T](p1: T) : F[T] = ???
    * }}}
    */
  @scala.deprecated("Use MonadThrow instead", "0.1.0")
  type MonadAttempt[F[_]] = cats.MonadError[F, Throwable]

  @scala.deprecated("Use BracketThrow instead", "0.1.0")
  type BracketAttempt[F[_]] = cats.effect.Bracket[F, Throwable]

  val BracketThrow: busymachines.pureharm.effects.BracketThrow.type = busymachines.pureharm.effects.BracketThrow

  /** Used to block on an F[A], and ensure that all recovery and shifting back is always done.
    *
    * For instance, always ensure that any F[A] that talks to, say, amazon S3, is wrapped in such a
    * {{{
    *   blockingShifter.blockOn(S3Util.putSomething(...))
    * }}}
    *
    * Libraries in the typelevel eco-system tend to already do this, so you don't need to be careful. For instance,
    * doobie will always ensure that this is done to and from the EC that you provide specifically for accessing the DB.
    * But you always need to double check, and be careful that you NEVER execute blocking IO on the same thread pool as
    * the CPU bound one dedicated to your ContextShift[A]
    */
  type BlockingShifter[F[_]] = busymachines.pureharm.effects.BlockingShifter[F]

  val BlockingShifter: busymachines.pureharm.effects.BlockingShifter.type =
    busymachines.pureharm.effects.BlockingShifter

  type PureharmIOApp = busymachines.pureharm.effects.PureharmIOApp

}
