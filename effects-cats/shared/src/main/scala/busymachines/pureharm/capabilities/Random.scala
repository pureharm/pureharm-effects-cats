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

import java.util.UUID
import scala.util.{Random => ScalaRandom}

import busymachines.pureharm.effects._
import cats.syntax.all._

trait Random[F[_]] {
  def boolean: F[Boolean]

  def int: F[Int]
  def int(l: Int): F[Int]
  def int(l: Int, h: Int): F[Int]

  def long: F[Long]
  def long(l: Long): F[Long]
  def long(l: Long, h: Long): F[Long]

  def double(l: Double): F[Double]

  def double(l: Double, h: Double): F[Double]

  def string(maxSize: Int): F[String]

  def uuid: F[UUID]

  final def mapK[G[_]](fk: F ~> G): Random[G] = new Random[G] {
    override def boolean: G[Boolean] = fk(Random.this.boolean)

    override def int: G[Int] = fk(Random.this.int)

    override def int(l: Int): G[Int] = fk(Random.this.int(l))

    override def int(l: Int, h: Int): G[Int] = fk(Random.this.int(l, h))

    override def long: G[Long] = fk(Random.this.long)

    override def long(l: Long): G[Long] = fk(Random.this.long(l))

    override def long(l: Long, h: Long): G[Long] = fk(Random.this.long(l, h))

    override def double(l: Double): G[Double] = fk(Random.this.double(l))

    override def double(l: Double, h: Double): G[Double] = fk(Random.this.double(l, h))

    override def string(maxSize: Int): G[String] = fk(Random.this.string(maxSize))

    /** Generates a Version 4 UUID
      */
    override def uuid: G[UUID] = fk(Random.this.uuid)
  }
}

object Random extends PlatformSpecificRandom {

  def apply[F[_]](implicit r: Random[F]): Random[F] = r

  def resource[F[_]](implicit F: Sync[F]): Resource[F, Random[F]] = Resource.pure[F, Random[F]](threadLocalRandom[F])

  /** Provides a Random[F] instance backed up by java.util.concurrent.ThreadLocalRandom.current()
    *
    * This reduces contention on the same random instance in a concurrent setting.
    */
  def threadLocalRandom[F[_]](implicit F: Sync[F]): Random[F] = new RandomImpl[F] {

    override protected val randomF: F[ScalaRandom] = {
      for {
        javaTLR <- F.delay(java.util.concurrent.ThreadLocalRandom.current())
      } yield new ScalaRandom(javaTLR)
    }
  }

  implicit def randomForResource[F[_]: Applicative](implicit random: Random[F]): Random[Resource[F, *]] =
    random.mapK(Resource.liftK[F])

}
