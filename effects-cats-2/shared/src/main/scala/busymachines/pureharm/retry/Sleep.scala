/*
 * Copyright 2021 Chris Birchall et. co, contributors of cats-retry
 * https://github.com/cb372/cats-retry
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

package busymachines.pureharm.retry

import cats.effect.Timer

import scala.concurrent.duration.FiniteDuration

trait Sleep[M[_]] {
  def sleep(delay: FiniteDuration): M[Unit]
}

object Sleep {
  def apply[M[_]](implicit sleep: Sleep[M]): Sleep[M] = sleep

  implicit def sleepUsingTimer[F[_]](implicit t: Timer[F]): Sleep[F] =
    (delay: FiniteDuration) => t.sleep(delay)
}
