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

import scala.{concurrent => sc}

private[pureharm] trait ScalaStdAliases {

  //brought in for easy pattern matching. Failure, and Success are used way too often
  //in way too many libraries, so we just alias the std Scala library ones
  final type Try[+A] = scala.util.Try[A]
  final val Try:        scala.util.Try.type     = scala.util.Try
  final val TryFailure: scala.util.Failure.type = scala.util.Failure
  final val TrySuccess: scala.util.Success.type = scala.util.Success

  final val NonFatal: scala.util.control.NonFatal.type = scala.util.control.NonFatal
  
  type NoStackTrace = scala.util.control.NoStackTrace

  //----------- scala Future -----------
  final type Future[+A] = sc.Future[A]
  final val Future: sc.Future.type = sc.Future

  final type ExecutionContext = sc.ExecutionContext
  final val ExecutionContext: sc.ExecutionContext.type = sc.ExecutionContext

  final type ExecutionContextExecutor        = sc.ExecutionContextExecutor
  final type ExecutionContextExecutorService = sc.ExecutionContextExecutorService

  final val Await: sc.Await.type = sc.Await
}
