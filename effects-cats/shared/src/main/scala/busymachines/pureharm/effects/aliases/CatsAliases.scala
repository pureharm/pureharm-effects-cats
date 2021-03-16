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

trait CatsAliases {

  final type Functor[F[_]] = cats.Functor[F]
  final val Functor: cats.Functor.type = cats.Functor

  final type Applicative[F[_]] = cats.Applicative[F]
  final val Applicative: cats.Applicative.type = cats.Applicative

  final type Apply[F[_]] = cats.Apply[F]
  final val Apply: cats.Apply.type = cats.Apply

  final type FlatMap[F[_]] = cats.FlatMap[F]
  final val FlatMap: cats.FlatMap.type = cats.FlatMap

  final type CoflatMap[F[_]] = cats.CoflatMap[F]
  final val CoflatMap: cats.CoflatMap.type = cats.CoflatMap

  final type Monad[F[_]] = cats.Monad[F]
  final val Monad: cats.Monad.type = cats.Monad

  final type ApplicativeError[F[_], E] = cats.ApplicativeError[F, E]
  final val ApplicativeError: cats.ApplicativeError.type = cats.ApplicativeError

  final type ApplicativeThrow[F[_]] = cats.ApplicativeThrow[F]
  final val ApplicativeThrow: cats.ApplicativeThrow.type = cats.ApplicativeThrow

  final type MonadError[F[_], E] = cats.MonadError[F, E]
  final val MonadError: cats.MonadError.type = cats.MonadError

  final type MonadThrow[F[_]] = cats.MonadThrow[F]
  final val MonadThrow: cats.MonadThrow.type = cats.MonadThrow

  final type Traverse[F[_]] = cats.Traverse[F]
  final val Traverse: cats.Traverse.type = cats.Traverse

  final type NonEmptyTraverse[F[_]] = cats.NonEmptyTraverse[F]
  final val NonEmptyTraverse: cats.NonEmptyTraverse.type = cats.NonEmptyTraverse

  final type UnorderedTraverse[F[_]] = cats.UnorderedTraverse[F]
  final val UnorderedTraverse: cats.UnorderedTraverse.type = cats.UnorderedTraverse

  final type TraverseFilter[F[_]] = cats.TraverseFilter[F]
  final val TraverseFilter: cats.TraverseFilter.type = cats.TraverseFilter

  final type Bitraverse[F[_, _]] = cats.Bitraverse[F]
  final val Bitraverse: cats.Bitraverse.type = cats.Bitraverse

  final type Parallel[F[_]] = cats.Parallel[F]
  final val Parallel: cats.Parallel.type = cats.Parallel

  final type NonEmptyParallel[F[_]] = cats.NonEmptyParallel[F]
  final val NonEmptyParallel: cats.NonEmptyParallel.type = cats.NonEmptyParallel

  final type Semigroupal[F[_]] = cats.Semigroupal[F]
  final val Semigroupal: cats.Semigroupal.type = cats.Semigroupal

  final type Eq[A] = cats.Eq[A]
  final val Eq: cats.Eq.type = cats.Eq

  final type PartialOrder[A] = cats.PartialOrder[A]
  final val PartialOrder: cats.PartialOrder.type = cats.PartialOrder

  final type Comparison = cats.Comparison
  final val Comparison: cats.Comparison.type = cats.Comparison

  final type Order[A] = cats.Order[A]
  final val Order: cats.Order.type = cats.Order

  final type Hash[A] = cats.Hash[A]
  final val Hash: cats.Hash.type = cats.Hash

  final type Monoid[A] = cats.Monoid[A]
  final val Monoid: cats.Monoid.type = cats.Monoid

  final type Group[A] = cats.Group[A]
  final val Group: cats.Group.type = cats.Group

  final type Eval[+A] = cats.Eval[A]
  final val Eval: cats.Eval.type = cats.Eval

  final type Now[A] = cats.Now[A]
  final val Now: cats.Now.type = cats.Now

  final type Later[A] = cats.Later[A]
  final val Later: cats.Later.type = cats.Later

  final type Always[A] = cats.Always[A]
  final val Always: cats.Always.type = cats.Always

  //---------- monad transformers ----------------

  final type EitherT[F[_], L, R] = cats.data.EitherT[F, L, R]
  final val EitherT: cats.data.EitherT.type = cats.data.EitherT

  final type OptionT[F[_], A] = cats.data.OptionT[F, A]
  final val OptionT: cats.data.OptionT.type = cats.data.OptionT

  //---------- cats-data ----------------
  final type NEList[+A] = cats.data.NonEmptyList[A]
  final val NEList: cats.data.NonEmptyList.type = cats.data.NonEmptyList

  final type NonEmptyList[+A] = cats.data.NonEmptyList[A]
  final val NonEmptyList: cats.data.NonEmptyList.type = cats.data.NonEmptyList

  final type NESet[A] = cats.data.NonEmptySet[A]
  final val NESet: cats.data.NonEmptySet.type = cats.data.NonEmptySet

  final type NonEmptySet[A] = cats.data.NonEmptySet[A]
  final val NonEmptySet: cats.data.NonEmptySet.type = cats.data.NonEmptySet

  final type NonEmptyMap[K, +A] = cats.data.NonEmptyMap[K, A]
  final val NonEmptyMap: cats.data.NonEmptyMap.type = cats.data.NonEmptyMap

  final type NEMap[K, +A] = cats.data.NonEmptyMap[K, A]
  final val NEMap: cats.data.NonEmptyMap.type = cats.data.NonEmptyMap

  final type Chain[+A] = cats.data.Chain[A]
  final val Chain: cats.data.Chain.type = cats.data.Chain

  final type NonEmptyChain[+A] = cats.data.NonEmptyChain[A]
  final val NonEmptyChain: cats.data.NonEmptyChain.type = cats.data.NonEmptyChain

  //NE is much shorter than NonEmpty!
  final type NEChain[+A] = cats.data.NonEmptyChain[A]
  final val NEChain: cats.data.NonEmptyChain.type = cats.data.NonEmptyChain

  final type Kleisli[F[_], A, B] = cats.data.Kleisli[F, A, B]
  final val Kleisli: cats.data.Kleisli.type = cats.data.Kleisli

  final type ReaderT[F[_], A, B] = cats.data.ReaderT[F, A, B]
  final val ReaderT: cats.data.ReaderT.type = cats.data.ReaderT

  final type Reader[A, B] = cats.data.Reader[A, B]
  final val Reader: cats.data.Reader.type = cats.data.Reader

  //---------- cats-misc ---------------------

  final type Show[T] = cats.Show[T]
  final val Show: cats.Show.type = cats.Show

  final type Endo[T] = cats.Endo[T]
}
