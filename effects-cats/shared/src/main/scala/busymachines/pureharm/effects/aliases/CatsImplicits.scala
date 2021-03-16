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

import cats.{instances, syntax}

private[pureharm] trait CatsImplicits
  extends syntax.AllSyntax with syntax.AllSyntaxBinCompat0 with syntax.AllSyntaxBinCompat1
  with syntax.AllSyntaxBinCompat2 with syntax.AllSyntaxBinCompat3 with syntax.AllSyntaxBinCompat4
  with syntax.AllSyntaxBinCompat5 with syntax.AllSyntaxBinCompat6 with instances.AllInstances
  with instances.AllInstancesBinCompat0 with instances.AllInstancesBinCompat1 with instances.AllInstancesBinCompat2
  with instances.AllInstancesBinCompat3 with instances.AllInstancesBinCompat4 with instances.AllInstancesBinCompat5
  with instances.AllInstancesBinCompat6 with cats.effect.syntax.AllCatsEffectSyntax
