/** Copyright (c) 2019 BusyMachines
  *
  * See company homepage at: https://www.busymachines.com/
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package busymachines.pureharm.effects

import busymachines.pureharm.effects.pools.aliases.PureharmPoolAliases
import busymachines.pureharm.internals.effects.aliases

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 13 Jun 2019
  */
trait PureharmEffectsAllTypes
  extends aliases.PureharmTypeDefinitions with PureharmPoolAliases with aliases.CatsTypeAliases
