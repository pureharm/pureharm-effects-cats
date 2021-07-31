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

trait RetryAliases {
  type PolicyDecision = busymachines.pureharm.retry.PolicyDecision
  val PolicyDecision: busymachines.pureharm.retry.PolicyDecision.type = busymachines.pureharm.retry.PolicyDecision

  type RetryPolicy[M[_]] = busymachines.pureharm.retry.RetryPolicy[M]
  val RetryPolicy: busymachines.pureharm.retry.RetryPolicy.type = busymachines.pureharm.retry.RetryPolicy

  val RetryPolicies: busymachines.pureharm.retry.RetryPolicies.type = busymachines.pureharm.retry.RetryPolicies

  type RetryStatus = busymachines.pureharm.retry.RetryStatus
  val RetryStatus: busymachines.pureharm.retry.RetryStatus.type = busymachines.pureharm.retry.RetryStatus

  type RetryDetails = busymachines.pureharm.retry.RetryDetails
  val RetryDetails: busymachines.pureharm.retry.RetryDetails.type = busymachines.pureharm.retry.RetryDetails

  type Sleep[M[_]] = busymachines.pureharm.retry.Sleep[M]
  val Sleep: busymachines.pureharm.retry.Sleep.type = busymachines.pureharm.retry.Sleep
}
