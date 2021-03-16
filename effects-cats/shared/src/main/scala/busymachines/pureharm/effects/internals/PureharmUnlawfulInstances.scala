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

package busymachines.pureharm.effects.internals

import cats._
import cats.implicits._

/** @author Lorand Szakacs, https://github.com/lorandszakacs
  * @since 23 Jun 2020
  */
trait PureharmUnlawfulInstances {

  implicit val pureharmUnlawfulTraverseIterable: Traverse[Iterable] = new Traverse[Iterable] {

    override def foldLeft[A, B](fa: Iterable[A], b: B)(f: (B, A) => B): B =
      fa.foldLeft(b)(f)

    override def foldRight[A, B](fa: Iterable[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.foldRight(lb)(f)

    override def traverse[G[_], A, B](fa: Iterable[A])(f: A => G[B])(implicit G: Applicative[G]): G[Iterable[B]] = {
      import scala.collection.mutable
      foldLeft[A, G[mutable.Builder[B, mutable.Iterable[B]]]](fa, G.pure(mutable.Iterable.newBuilder[B]))((lglb, a) =>
        G.map2(f(a), lglb)((b: B, lb: mutable.Builder[B, mutable.Iterable[B]]) => lb.+=(b))
      ).map(_.result().toIterable)
    }
  }
}
