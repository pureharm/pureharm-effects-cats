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

import busymachines.pureharm.anomaly._
import busymachines.pureharm.effects._
import cats.syntax.all._

abstract class RandomImpl[F[_]](implicit F: Sync[F]) extends Random[F] {

  protected def randomF: F[ScalaRandom]

  protected def delay[A](f: ScalaRandom => A): F[A] = randomF.flatMap(sr => F.delay(f(sr)))

  override def boolean: F[Boolean] = delay(_.nextBoolean())

  override def int: F[Int] = delay(_.nextInt())
  override def int(i: Int): F[Int] = delay(_.nextInt(i.max(1)))

  override def int(l: Int, h: Int): F[Int] = for {
    _ <-
      if (l > h) InvalidInputAnomaly(s"Random.int(l: Int, h: Int) - l < h, but l=$l, h=$h").raiseError[F, Unit]
      else F.unit
    diff = h - l
    lower <- int(diff)
  } yield lower + diff

  override def long: F[Long] = delay(_.nextLong())

  override def long(l: Long): F[Long] = delay(_.nextLong(l.max(1L)))

  override def long(l: Long, h: Long): F[Long] = for {
    _ <-
      if (l > h) InvalidInputAnomaly(s"Random.long(l: Int, h: Int) - l < h, but l=$l, h=$h").raiseError[F, Unit]
      else F.unit
    diff = h - l
    lower <- long(diff)
  } yield lower + diff

  override def double(h: Double): F[Double] = this.double(0.0d, h)
  override def double(l: Double, h: Double): F[Double] = delay(_.between(l, h))

  override def string(length: Int): F[String] =
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
