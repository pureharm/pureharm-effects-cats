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

import cats.{effect => ce}

trait CatsEffectAliases {

  //----------- cats-effect types -----------
  final type Sync[F[_]] = ce.Sync[F]
  final val Sync: ce.Sync.type = ce.Sync

  final type Async[F[_]] = ce.Async[F]
  final val Async: ce.Async.type = ce.Async

  final type Effect[F[_]] = ce.Effect[F]
  final val Effect: ce.Effect.type = ce.Effect

  final type Concurrent[F[_]] = ce.Concurrent[F]
  final val Concurrent: ce.Concurrent.type = ce.Concurrent

  final type ConcurrentEffect[F[_]] = ce.ConcurrentEffect[F]
  final val ConcurrentEffect: ce.ConcurrentEffect.type = ce.ConcurrentEffect

  final type Timer[F[_]] = ce.Timer[F]
  final val Timer: ce.Timer.type = ce.Timer

  final type ContextShift[F[_]] = ce.ContextShift[F]
  final val ContextShift: ce.ContextShift.type = ce.ContextShift

  final type Blocker = ce.Blocker
  final val Blocker: ce.Blocker.type = ce.Blocker

  final type CancelToken[F[_]] = ce.CancelToken[F]
  final type Fiber[F[_], A]    = ce.Fiber[F, A]
  final val Fiber: ce.Fiber.type = ce.Fiber

  final type SyncIO[+A] = ce.SyncIO[A]
  final val SyncIO: ce.SyncIO.type = ce.SyncIO

  final type IO[+A] = ce.IO[A]
  final val IO: ce.IO.type = ce.IO

  final type LiftIO[F[_]] = ce.LiftIO[F]
  final val LiftIO: ce.LiftIO.type = ce.LiftIO

  final type IOApp = ce.IOApp
  final val IOApp: ce.IOApp.type = ce.IOApp

  final type ExitCode = ce.ExitCode
  final val ExitCode: ce.ExitCode.type = ce.ExitCode

  final type Bracket[F[_], E] = ce.Bracket[F, E]
  final val Bracket: ce.Bracket.type = ce.Bracket

  final type Resource[F[_], A] = ce.Resource[F, A]
  final val Resource: ce.Resource.type = ce.Resource

  final type ExitCase[+E] = ce.ExitCase[E]
  final val ExitCase: ce.ExitCase.type = ce.ExitCase

  //----------- cats-effect concurrent -----------

  final type Semaphore[F[_]] = ce.concurrent.Semaphore[F]
  final val Semaphore: ce.concurrent.Semaphore.type = ce.concurrent.Semaphore

  final type Deferred[F[_], A] = ce.concurrent.Deferred[F, A]
  final val Deferred: ce.concurrent.Deferred.type = ce.concurrent.Deferred

  final type TryableDeferred[F[_], A] = ce.concurrent.TryableDeferred[F, A]
  //intentionally has same companion object as Deferred.
  final val TryableDeferred: ce.concurrent.Deferred.type = ce.concurrent.Deferred

  @deprecated(
    "`MVar` is now deprecated in favour of a new generation `MVar2` with `tryRead` and `swap` support",
    "2.2.0",
  )
  final type MVar[F[_], A] = ce.concurrent.MVar[F, A]

  final type MVar2[F[_], A] = ce.concurrent.MVar2[F, A]
  final val MVar: ce.concurrent.MVar.type = ce.concurrent.MVar

  final type Ref[F[_], A] = ce.concurrent.Ref[F, A]
  final val Ref: ce.concurrent.Ref.type = ce.concurrent.Ref
}
