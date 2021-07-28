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

  type Random[F[_]] = busymachines.pureharm.capabilities.Random[F]
  val Random: busymachines.pureharm.capabilities.Random.type = busymachines.pureharm.capabilities.Random

}
