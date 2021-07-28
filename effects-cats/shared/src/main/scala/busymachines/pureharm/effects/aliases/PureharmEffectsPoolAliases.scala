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

import busymachines.pureharm.effects.pools

trait PureharmEffectsPoolAliases {

  val ExecutionContextFT = pools.ExecutionContextCT

  /** Denotes execution contexts backed by a fixed thread pool
    */
  type ExecutionContextFT = pools.ExecutionContextFT

  val ExecutionContextST = pools.ExecutionContextST
  /** Denotes execution contexts with one single thread
    */
  type ExecutionContextST = pools.ExecutionContextST

  val ExecutionContextCT = pools.ExecutionContextCT
  /** Denotes execution contexts backed by a cached thread pool
    */
  type ExecutionContextCT = pools.ExecutionContextCT

  /** Constructor object for various pools.
    */
  val Pools: pools.Pools.type = pools.Pools

  /** Mirror of Pools constructors, but without proper cleanup.
    *
    * Use with caution
    */
  val UnsafePools: pools.UnsafePools.type = pools.UnsafePools
}
