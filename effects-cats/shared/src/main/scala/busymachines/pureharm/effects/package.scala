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

package busymachines.pureharm

package object effects extends aliases.CatsAliases with aliases.CatsEffectAliases with aliases.ScalaStdAliases {
  type Attempt[+R] = Either[Throwable, R]
  val Attempt: Either.type = Either

  type AttemptT[F[_], R] = cats.data.EitherT[F, Throwable, R]
  val AttemptT: cats.data.EitherT.type = cats.data.EitherT

  /** Useful since we don't have partial kind application by default
    * Usage:
    * {{{
    *   def canFail[F[_]: ApplicativeAttempt, T](p1: T) : F[T] = ???
    * }}}
    */
  @scala.deprecated("Use ApplicativeThrow instead", "0.1.0")
  type ApplicativeAttempt[F[_]] = cats.ApplicativeError[F, Throwable]

  /** Useful since we don't have partial kind application by default
    * Usage:
    * {{{
    *   def canFail[F[_]: MonadAttempt, T](p1: T) : F[T] = ???
    * }}}
    */
  @scala.deprecated("Use MonadThrow instead", "0.1.0")
  type MonadAttempt[F[_]] = cats.MonadError[F, Throwable]

  @scala.deprecated("Use BracketThrow instead", "0.1.0")
  type BracketAttempt[F[_]] = cats.effect.Bracket[F, Throwable]

  /** !!! N.B. !!!
    * NEVER, EVER wildcard import this, AND, cats.implicits, or anything from the cats packages.
    *
    * This object is meant to bring in everything that is in cats + some extra, without burdening
    * the user with two different imports.
    */
  object implicits extends PureharmEffectsAllImplicits
}
