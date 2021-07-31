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

package busymachines.pureharm.capabilities

import busymachines.pureharm.effects._
import cats.syntax.all._

trait PlatformSpecificRandom {

  /** Provides a Random[F] instance backed up by a java.util.security.SecureRandom()
    *
    * Available only on JVM, JS does not have secure random.
    */
  def secureRandom[F[_]](implicit F: Sync[F]): F[Random[F]] =
    CERandom.javaSecuritySecureRandom[F].map { implicit ceRandom: CERandom[F] => new RandomImpl[F] {} }
}
