/*
 * Copyright 2020-2021 Typelevel
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

/** backport integration of CE-3's implementation
  * https://github.com/typelevel/cats-effect/blob/series/3.x/std/shared/src/main/scala/cats/effect/std/Random.scala#L342
  *
  * @param F
  */
abstract class RandomImpl[F[_]](implicit F: Sync[F]) extends Random[F] {

  protected def randomF: F[ScalaRandom]

  protected def delay[A](f: ScalaRandom => A): F[A] = randomF.flatMap(sr => F.delay(f(sr)))

  private[this] def require(condition: Boolean, errorMessage: => String): F[Unit] =
    if (condition) ().pure[F]
    else new IllegalArgumentException(errorMessage).raiseError[F, Unit]

  override def betweenDouble(minInclusive: Double, maxExclusive: Double): F[Double] =
    for {
      _ <- require(minInclusive < maxExclusive, "Invalid bounds")
      d <- nextDouble
    } yield {
      val next = d * (maxExclusive - minInclusive) + minInclusive
      if (next < maxExclusive) next
      else Math.nextAfter(maxExclusive, Double.NegativeInfinity)
    }

  override def betweenFloat(minInclusive: Float, maxExclusive: Float): F[Float] =
    for {
      _ <- require(minInclusive < maxExclusive, "Invalid bounds")
      f <- nextFloat
    } yield {
      val next = f * (maxExclusive - minInclusive) + minInclusive
      if (next < maxExclusive) next
      else Math.nextAfter(maxExclusive, Float.NegativeInfinity)
    }

  override def betweenInt(minInclusive: Int, maxExclusive: Int): F[Int] =
    require(minInclusive < maxExclusive, "Invalid bounds") *> {
      val difference = maxExclusive - minInclusive
      for {
        out <-
          if (difference >= 0) {
            nextIntBounded(difference).map(_ + minInclusive)
          }
          else {
            /* The interval size here is greater than Int.MaxValue,
             * so the loop will exit with a probability of at least 1/2.
             */
            def loop(): F[Int] =
              nextInt.flatMap { n =>
                if (n >= minInclusive && n < maxExclusive) n.pure[F]
                else loop()
              }
            loop()
          }
      } yield out
    }

  override def betweenLong(minInclusive: Long, maxExclusive: Long): F[Long] =
    require(minInclusive < maxExclusive, "Invalid bounds") *> {
      val difference = maxExclusive - minInclusive
      for {
        out <-
          if (difference >= 0) {
            nextLongBounded(difference).map(_ + minInclusive)
          }
          else {
            /* The interval size here is greater than Long.MaxValue,
             * so the loop will exit with a probability of at least 1/2.
             */
            def loop(): F[Long] =
              nextLong.flatMap { n =>
                if (n >= minInclusive && n < maxExclusive) n.pure[F]
                else loop()
              }
            loop()
          }
      } yield out
    }

  override def nextAlphaNumeric: F[Char] = {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    nextIntBounded(chars.length()).map(chars.charAt(_))
  }

  override def nextBoolean: F[Boolean] = delay(_.nextBoolean())

  override def nextBytes(n: Int): F[Array[Byte]] = delay { r =>
    val bytes = new Array[Byte](0.max(n))
    r.nextBytes(bytes)
    bytes
  }
  override def nextDouble: F[Double] = delay(_.nextDouble())
  override def nextFloat:    F[Float]  = delay(_.nextFloat())
  override def nextGaussian: F[Double] = delay(_.nextGaussian())
  override def nextInt:      F[Int]    = delay(_.nextInt())
  override def nextIntBounded(n: Int): F[Int] = delay(_.nextInt(n))
  override def nextLong: F[Long] = delay(_.nextLong())
  override def nextLongBounded(n: Long): F[Long] = delay(_.nextLong(n))
  override def nextPrintableChar: F[Char] = delay(_.nextPrintableChar())
  override def nextString(length:  Int):       F[String]    = delay(_.nextString(length))
  override def shuffleList[A](l:   List[A]):   F[List[A]]   = delay(_.shuffle(l))
  override def shuffleVector[A](v: Vector[A]): F[Vector[A]] = delay(_.shuffle(v))

  override def printableString(length: Int): F[String] =
    delay(sr => List.range(0, length).map(_ => sr.nextPrintableChar()).mkString)

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

  override def uuid: F[UUID] = delay { sr =>
    new java.util.UUID(
      (sr.nextLong() & clearBits79_80) ^ setBits79_80, //most significant bits
      (sr.nextLong() & clearBits49_52) ^ setBits49_52, //least significant bits
    )
  }
}
