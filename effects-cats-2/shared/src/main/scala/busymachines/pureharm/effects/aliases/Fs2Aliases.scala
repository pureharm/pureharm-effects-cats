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

trait Fs2Aliases {
  type Stream[+F[_], +O] = fs2.Stream[F, O]
  val Stream: fs2.Stream.type = fs2.Stream

  type Pipe[F[_], -I, +O] = fs2.Pipe[F, I, O]

  type Collector[-A] = fs2.Collector[A]
  val Collector: fs2.Collector.type = fs2.Collector

  type Pull[+F[_], +O, +R] = fs2.Pull[F, O, R]
  val Pull: fs2.Pull.type = fs2.Pull

  type Chunk[+O] = fs2.Chunk[O]
  val Chunk: fs2.Chunk.type = fs2.Chunk

  type Enqueue[F[_], A]             = fs2.concurrent.Enqueue[F, A]
  type Dequeue[F[_], A]             = fs2.concurrent.Dequeue[F, A]
  type Dequeue1[F[_], A]            = fs2.concurrent.Dequeue1[F, A]
  type DequeueChunk1[F[_], G[_], A] = fs2.concurrent.DequeueChunk1[F, G, A]

  type Queue[F[_], A] = fs2.concurrent.Queue[F, A]
  val Queue: fs2.concurrent.Queue.type = fs2.concurrent.Queue

  type NoneTerminatedQueue[F[_], A] = fs2.concurrent.NoneTerminatedQueue[F, A]

  type InspectableQueue[F[_], A] = fs2.concurrent.InspectableQueue[F, A]
  val InspectableQueue: fs2.concurrent.InspectableQueue.type = fs2.concurrent.InspectableQueue

  val Balance: fs2.concurrent.Balance.type = fs2.concurrent.Balance
}
