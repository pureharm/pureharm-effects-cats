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
import cats.effect.{kernel => cekernel}

trait CatsEffectAliases {

  //----------- cats-effect types -----------
  type Outcome[F[_], E, A] = cekernel.Outcome[F, E, A]
  val Outcome: cekernel.Outcome.type = cekernel.Outcome

  type MonadCancel[F[_], E] = cekernel.MonadCancel[F, E]
  val MonadCancel: cekernel.MonadCancel.type = cekernel.MonadCancel

  @scala.deprecated(
    "Bracket was removed from cats-effect 3, in pureharm we aliased it to MonadCancel, it's spiritual successor. Please use that instead",
    "0.5.0",
  )
  type Bracket[F[_], E] = cekernel.MonadCancel[F, E]

  @scala.deprecated(
    "Bracket was removed from cats-effect 3, in pureharm we aliased it to MonadCancel, it's spiritual successor. Please use that instead",
    "0.5.0",
  )
  def Bracket[F[_], E](implicit F: MonadCancel[F, E]): MonadCancel[F, E] = F

  @scala.deprecated(
    "BracketThrow was removed from cats-effect 3, in pureharm we aliased it to MonadCancel, it's spiritual successor. Please use that instead",
    "0.5.0",
  )
  type BracketThrow[F[_]] = cekernel.MonadCancelThrow[F]

  @scala.deprecated(
    "BracketThrow was removed from cats-effect 3, in pureharm we aliased it to MonadCancel, it's spiritual successor. Please use that instead",
    "0.5.0",
  )
  def BracketThrow[F[_]](implicit F: MonadCancelThrow[F]): MonadCancelThrow[F] = F

  type MonadCancelThrow[F[_]] = cekernel.MonadCancelThrow[F]
  val MonadCancelThrow: cekernel.MonadCancelThrow.type = cekernel.MonadCancelThrow

  type GenSpawn[F[_], E] = cekernel.GenSpawn[F, E]
  val GenSpawn: cekernel.GenSpawn.type = cekernel.GenSpawn

  type Fiber[F[_], E, A] = cekernel.Fiber[F, E, A]
  type Poll[F[_]]        = cekernel.Poll[F]

  type GenConcurrent[F[_], E] = cekernel.GenConcurrent[F, E]
  val GenConcurrent: cekernel.GenConcurrent.type = cekernel.GenConcurrent

  type Clock[F[_]] = cekernel.Clock[F]
  val Clock: cekernel.Clock.type = cekernel.Clock

  type GenTemporal[F[_], E] = cekernel.GenTemporal[F, E]
  val GenTemporal: cekernel.GenTemporal.type = cekernel.GenTemporal

  type Unique[F[_]] = cekernel.Unique[F]
  val Unique: cekernel.Unique.type = cekernel.Unique

  type Sync[F[_]] = cekernel.Sync[F]
  val Sync: cekernel.Sync.type = cekernel.Sync

  type Async[F[_]] = cekernel.Async[F]
  val Async: cekernel.Async.type = cekernel.Async

  type Spawn[F[_]] = cekernel.Spawn[F]
  val Spawn: cekernel.Spawn.type = cekernel.Spawn

  type Concurrent[F[_]] = cekernel.Concurrent[F]
  val Concurrent: cekernel.Concurrent.type = cekernel.Concurrent

  type Temporal[F[_]] = cekernel.Temporal[F]
  val Temporal: cekernel.Temporal.type = cekernel.Temporal

  type ParallelF[F[_], A] = cekernel.Par.ParallelF[F, A]
  val ParallelF: cekernel.Par.ParallelF.type = cekernel.Par.ParallelF

  type Resource[F[_], +A] = cekernel.Resource[F, A]
  val Resource: cekernel.Resource.type = cekernel.Resource

  type OutcomeIO[A]  = ce.Outcome[IO, Throwable, A]
  type FiberIO[A]    = ce.Fiber[IO, Throwable, A]
  type ResourceIO[A] = ce.Resource[IO, A]

  type Deferred[F[_], A] = cekernel.Deferred[F, A]
  val Deferred: cekernel.Deferred.type = cekernel.Deferred

  type DeferredSink[F[_], A] = cekernel.DeferredSink[F, A]
  val DeferredSink: cekernel.DeferredSink.type = cekernel.DeferredSink

  type Ref[F[_], A] = cekernel.Ref[F, A]
  val Ref: cekernel.Ref.type = cekernel.Ref

  type SyncIO[+A] = ce.SyncIO[A]
  val SyncIO: ce.SyncIO.type = ce.SyncIO

  type IO[+A] = ce.IO[A]
  val IO: ce.IO.type = ce.IO

  type LiftIO[F[_]] = ce.LiftIO[F]
  val LiftIO: ce.LiftIO.type = ce.LiftIO

  type IOApp = ce.IOApp
  val IOApp: ce.IOApp.type = ce.IOApp

  type ResourceApp = ce.ResourceApp
  val ResourceApp: ce.ResourceApp.type = ce.ResourceApp

  type ExitCode = ce.ExitCode
  val ExitCode: ce.ExitCode.type = ce.ExitCode

  //----------- cats-effect std -----------

  type Console[F[_]] = ce.std.Console[F]
  val Console: ce.std.Console.type = ce.std.Console

  type Semaphore[F[_]] = ce.std.Semaphore[F]
  val Semaphore: ce.std.Semaphore.type = ce.std.Semaphore

  type CountDownLatch[F[_]] = ce.std.CountDownLatch[F]
  val CountDownLatch: ce.std.CountDownLatch.type = ce.std.CountDownLatch

  type CyclicBarrier[F[_]] = ce.std.CyclicBarrier[F]
  val CyclicBarrier: ce.std.CyclicBarrier.type = ce.std.CyclicBarrier

  type Hotswap[F[_], R] = ce.std.Hotswap[F, R]
  val Hotswap: ce.std.Hotswap.type = ce.std.Hotswap

  type Queue[F[_], A] = ce.std.Queue[F, A]
  val Queue: ce.std.Queue.type = ce.std.Queue

  type QueueSink[F[_], A] = ce.std.QueueSink[F, A]
  val QueueSink: ce.std.QueueSink.type = ce.std.QueueSink

  type QueueSource[F[_], A] = ce.std.QueueSource[F, A]
  val QueueSource: ce.std.QueueSource.type = ce.std.QueueSource

  type Dequeue[F[_], A] = ce.std.Dequeue[F, A]
  val Dequeue: ce.std.Dequeue.type = ce.std.Dequeue

  type DequeueSink[F[_], A] = ce.std.DequeueSink[F, A]
  val DequeueSink: ce.std.DequeueSink.type = ce.std.DequeueSink

  type DequeueSource[F[_], A] = ce.std.DequeueSource[F, A]
  val DequeueSource: ce.std.DequeueSource.type = ce.std.DequeueSource

  type PQueue[F[_], A] = ce.std.PQueue[F, A]
  val PQueue: ce.std.PQueue.type = ce.std.PQueue

  type PQueueSink[F[_], A] = ce.std.PQueueSink[F, A]
  val PQueueSink: ce.std.PQueueSink.type = ce.std.PQueueSink

  type PQueueSource[F[_], A] = ce.std.PQueueSource[F, A]
  val PQueueSource: ce.std.PQueueSource.type = ce.std.PQueueSource

  type Supervisor[F[_]] = ce.std.Supervisor[F]
  val Supervisor: ce.std.Supervisor.type = ce.std.Supervisor

  type Dispatcher[F[_]] = ce.std.Dispatcher[F]
  val Dispatcher: ce.std.Dispatcher.type = ce.std.Dispatcher

  type CERandom[F[_]] = ce.std.Random[F]
  val CERandom: ce.std.Random.type = ce.std.Random

  type UUIDGen[F[_]] = ce.std.UUIDGen[F]
  val UUIDGen: ce.std.UUIDGen.type = ce.std.UUIDGen

}
