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

  type Pipe[F[_], -I, +O]       = fs2.Pipe[F, I, O]
  type Pipe2[F[_], -I, -I2, +O] = (Stream[F, I], Stream[F, I2]) => Stream[F, O]

  type Collector[-A] = fs2.Collector[A]
  val Collector: fs2.Collector.type = fs2.Collector

  type Compiler[F[_], G[_]] = fs2.Compiler[F, G]
  val Compiler: fs2.Compiler.type = fs2.Compiler

  type Pull[+F[_], +O, +R] = fs2.Pull[F, O, R]
  val Pull: fs2.Pull.type = fs2.Pull

  type Chunk[+O] = fs2.Chunk[O]
  val Chunk: fs2.Chunk.type = fs2.Chunk

  type Channel[F[_], A] = fs2.concurrent.Channel[F, A]
  val Channel: fs2.concurrent.Channel.type = fs2.concurrent.Channel

  type Signal[F[_], A] = fs2.concurrent.Signal[F, A]
  val Signal: fs2.concurrent.Signal.type = fs2.concurrent.Signal

  type SignallingRef[F[_], A] = fs2.concurrent.SignallingRef[F, A]
  val SignallingRef: fs2.concurrent.SignallingRef.type = fs2.concurrent.SignallingRef

  type Topic[F[_], A] = fs2.concurrent.Topic[F, A]
  val Topic: fs2.concurrent.Topic.type = fs2.concurrent.Topic
}
