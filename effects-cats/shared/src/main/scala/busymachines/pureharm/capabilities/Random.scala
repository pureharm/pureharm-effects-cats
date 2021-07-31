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

import busymachines.pureharm.effects._

trait Random[F[_]] extends CERandom[F] { self =>

  /** @param length
    *   will default to 1 if parameter is (length <= 0)
    * @return
    *   a string of specified length, where every character is consistent with [[nextPrintableChar]]
    */
  def printableString(length: Int): F[String]

  /** Generates a Version 4 java.util.UUID
    */
  def uuid: F[UUID]

  /** Modifies the context in which this [[Random]] operates using the natural transformation `f`.
    *
    * @return
    *   a [[Random]] in the new context obtained by mapping the current one using `f`
    */
  override def mapK[G[_]](f: F ~> G): Random[G] =
    new Random[G] {

      override def betweenDouble(minInclusive: Double, maxExclusive: Double): G[Double] =
        f(self.betweenDouble(minInclusive, maxExclusive))

      override def betweenFloat(minInclusive: Float, maxExclusive: Float): G[Float] =
        f(self.betweenFloat(minInclusive, maxExclusive))

      override def betweenInt(minInclusive: Int, maxExclusive: Int): G[Int] =
        f(self.betweenInt(minInclusive, maxExclusive))

      override def betweenLong(minInclusive: Long, maxExclusive: Long): G[Long] =
        f(self.betweenLong(minInclusive, maxExclusive))
      override def nextAlphaNumeric: G[Char]    = f(self.nextAlphaNumeric)
      override def nextBoolean:      G[Boolean] = f(self.nextBoolean)
      override def nextBytes(n: Int): G[Array[Byte]] = f(self.nextBytes(n))
      override def nextDouble:   G[Double] = f(self.nextDouble)
      override def nextFloat:    G[Float]  = f(self.nextFloat)
      override def nextGaussian: G[Double] = f(self.nextGaussian)
      override def nextInt:      G[Int]    = f(self.nextInt)
      override def nextIntBounded(n: Int): G[Int] = f(self.nextIntBounded(n))
      override def nextLong: G[Long] = f(self.nextLong)
      override def nextLongBounded(n: Long): G[Long] = f(self.nextLongBounded(n))
      override def nextPrintableChar: G[Char] = f(self.nextPrintableChar)
      override def nextString(length:       Int):       G[String]    = f(self.nextString(length))
      override def shuffleList[A](l:        List[A]):   G[List[A]]   = f(self.shuffleList(l))
      override def shuffleVector[A](v:      Vector[A]): G[Vector[A]] = f(self.shuffleVector(v))
      override def printableString(maxSize: Int):       G[String]    = f(self.printableString(maxSize))
      override def uuid: G[UUID] = f(self.uuid)

    }

}

object Random extends PlatformSpecificRandom {

  def apply[F[_]](implicit r: Random[F]): Random[F] = r

  def resource[F[_]](implicit F: Sync[F]): Resource[F, Random[F]] = Resource.pure[F, Random[F]](threadLocalRandom[F])

  /** Provides a Random[F] instance backed up by java.util.concurrent.ThreadLocalRandom.current()
    *
    * This reduces contention on the same random instance in a concurrent setting.
    */
  def threadLocalRandom[F[_]](implicit F: Sync[F]): Random[F] = {
    implicit val ce3Random: CERandom[F] = CERandom.javaUtilConcurrentThreadLocalRandom[F]
    new RandomImpl[F] {}
  }

  implicit def randomForResource[F[_]](implicit random: Random[F]): Random[Resource[F, *]] =
    random.mapK(Resource.liftK[F])

}
