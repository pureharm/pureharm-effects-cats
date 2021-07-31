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
import cats.syntax.all._

abstract class RandomImpl[F[_]](implicit F: Monad[F], r: CERandom[F]) extends Random[F] {

  override def betweenDouble(minInclusive: Double, maxExclusive: Double): F[Double] =
    r.betweenDouble(minInclusive, maxExclusive)

  override def betweenFloat(minInclusive: Float, maxExclusive: Float): F[Float] =
    r.betweenFloat(minInclusive, maxExclusive)

  override def betweenInt(minInclusive: Int, maxExclusive: Int): F[Int] =
    r.betweenInt(minInclusive, maxExclusive)

  override def betweenLong(minInclusive: Long, maxExclusive: Long): F[Long] =
    r.betweenLong(minInclusive, maxExclusive)
  override def nextAlphaNumeric: F[Char]    = r.nextAlphaNumeric
  override def nextBoolean:      F[Boolean] = r.nextBoolean
  override def nextBytes(n: Int): F[Array[Byte]] = r.nextBytes(n)
  override def nextDouble:   F[Double] = r.nextDouble
  override def nextFloat:    F[Float]  = r.nextFloat
  override def nextGaussian: F[Double] = r.nextGaussian
  override def nextInt:      F[Int]    = r.nextInt
  override def nextIntBounded(n: Int): F[Int] = r.nextIntBounded(n)
  override def nextLong: F[Long] = r.nextLong
  override def nextLongBounded(n: Long): F[Long] = r.nextLongBounded(n)
  override def nextPrintableChar: F[Char] = r.nextPrintableChar
  override def nextString(length:  Int):       F[String]    = r.nextString(length)
  override def shuffleList[A](l:   List[A]):   F[List[A]]   = r.shuffleList(l)
  override def shuffleVector[A](v: Vector[A]): F[Vector[A]] = r.shuffleVector(v)

  override def printableString(length: Int): F[String] =
    List.range(0, length.max(1)).traverse(_ => r.nextPrintableChar).map(_.mkString)

  // see point 2 of the procedure:
  //  https://www.cryptosys.net/pki/uuid-rfc4122.html#note1
  // Adjust certain bits according to RFC 4122 section 4.4 as follows:
  //    1. set the four most significant bits of the 7th byte to 0100'B, so the high nibble is "4"
  private[this] val clearBits49_52: Long = ~((1L << 49L) | (1L << 50L) | (1L << 51L) | (1L << 52L))
  private[this] val setBits49_52:   Long = 1L << 50L

  /* the ninth byte is the 1st byte of the most significant Long :)
   *
   * 2. set the two most significant bits of the 9th byte to 10'B, so the high nibble will be one of "8", "9", "A", or "B" (see Note 1).
   */
  private[this] val clearBits79_80: Long = ~((1L << 15L) | (1L << 16L))
  private[this] val setBits79_80:   Long = 1L << 15L

  override def uuid: F[UUID] =
    for {
      msb <- r.nextLong
      lsb <- r.nextLong
    } yield new java.util.UUID(
      (msb & clearBits79_80) ^ setBits79_80, //most significant bits
      (lsb & clearBits49_52) ^ setBits49_52, //least significant bits
    )
}
