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

  final type Foldable[F[_]] = cats.Foldable[F]
  final val Foldable: cats.Foldable.type = cats.Foldable

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

  final type Eq[A] = cats.kernel.Eq[A]
  final val Eq: cats.kernel.Eq.type = cats.kernel.Eq

  final type PartialOrder[A] = cats.kernel.PartialOrder[A]
  final val PartialOrder: cats.kernel.PartialOrder.type = cats.kernel.PartialOrder

  final type Comparison = cats.kernel.Comparison
  final val Comparison: cats.kernel.Comparison.type = cats.kernel.Comparison

  final type Order[A] = cats.kernel.Order[A]
  final val Order: cats.kernel.Order.type = cats.kernel.Order

  final type Hash[A] = cats.kernel.Hash[A]
  final val Hash: cats.kernel.Hash.type = cats.kernel.Hash

  final type Semigroup[A] = cats.kernel.Semigroup[A]
  final val Semigroup = cats.kernel.Semigroup

  final type Monoid[A] = cats.kernel.Monoid[A]
  final val Monoid: cats.kernel.Monoid.type = cats.kernel.Monoid

  final type Group[A] = cats.kernel.Group[A]
  final val Group: cats.kernel.Group.type = cats.kernel.Group

  final type Eval[+A] = cats.Eval[A]
  final val Eval: cats.Eval.type = cats.Eval

  final type Now[A] = cats.Now[A]
  final val Now: cats.Now.type = cats.Now

  final type Later[A] = cats.Later[A]
  final val Later: cats.Later.type = cats.Later

  final type Always[A] = cats.Always[A]
  final val Always: cats.Always.type = cats.Always

  final type Representable[F[_]] = cats.Representable[F]
  final val Representable: cats.Representable.type = cats.Representable

  final type Reducible[F[_]] = cats.Reducible[F]
  final val Reducible: cats.Reducible.type = cats.Reducible

  final type MonoidK[F[_]] = cats.MonoidK[F]
  final val MonoidK: cats.MonoidK.type = cats.MonoidK

  final type Bifunctor[F[_, _]] = cats.Bifunctor[F]
  final val Bifunctor: cats.Bifunctor.type = cats.Bifunctor

  final type InvariantSemigroupal[F[_]] = cats.InvariantSemigroupal[F]
  final val InvariantSemigroupal: cats.InvariantSemigroupal.type = cats.InvariantSemigroupal

  final type InvariantMonoidal[F[_]] = cats.InvariantMonoidal[F]
  final val InvariantMonoidal: cats.InvariantMonoidal.type = cats.InvariantMonoidal

  final type Invariant[F[_]] = cats.Invariant[F]
  final val Invariant: cats.Invariant.type = cats.Invariant

  final type InjectK[F[_], G[_]] = cats.InjectK[F, G]
  final val InjectK: cats.InjectK.type = cats.InjectK

  final type Inject[A, B] = cats.Inject[A, B]
  final val Inject: cats.Inject.type = cats.Inject

  final type FunctorFilter[F[_]] = cats.FunctorFilter[F]
  final val FunctorFilter: cats.FunctorFilter.type = cats.FunctorFilter

  final type EvalGroup[A]     = cats.EvalGroup[A]
  final type EvalSemigroup[A] = cats.EvalSemigroup[A]
  final type EvalMonoid[A]    = cats.EvalMonoid[A]

  final type Comonad[F[_]] = cats.Comonad[F]
  final val Comonad: cats.Comonad.type = cats.Comonad

  final type CommutativeMonad[F[_]] = cats.CommutativeMonad[F]
  final val CommutativeMonad: cats.CommutativeMonad.type = cats.CommutativeMonad

  final type CommutativeFlatMap[F[_]] = cats.CommutativeFlatMap[F]
  final val CommutativeFlatMap: cats.CommutativeFlatMap.type = cats.CommutativeFlatMap

  final type CommutativeApply[F[_]] = cats.CommutativeApply[F]
  final val CommutativeApply: cats.CommutativeApply.type = cats.CommutativeApply

  final type CommutativeApplicative[F[_]] = cats.CommutativeApplicative[F]
  final val CommutativeApplicative: cats.CommutativeApplicative.type = cats.CommutativeApplicative

  final type Bimonad[F[_]] = cats.Bimonad[F]
  final val Bimonad: cats.Bimonad.type = cats.Bimonad

  final type Bifoldable[F[_, _]] = cats.Bifoldable[F]
  final val Bifoldable: cats.Bifoldable.type = cats.Bifoldable

  final type Alternative[F[_]] = cats.Alternative[F]
  final val Alternative: cats.Alternative.type = cats.Alternative

  final type Align[F[_]] = cats.Align[F]
  final val Align: cats.Align.type = cats.Align

  final type Show[T] = cats.Show[T]
  final val Show: cats.Show.type = cats.Show

  final type Endo[T] = cats.Endo[T]

  final type ⊥ = Nothing
  final type ⊤ = Any

  final type :<:[F[_], G[_]] = cats.:<:[F, G]
  final type :≺:[F[_], G[_]] = cats.:≺:[F, G]

  //---------- cats-data ----------------

  final type EitherT[F[_], L, R] = cats.data.EitherT[F, L, R]
  final val EitherT: cats.data.EitherT.type = cats.data.EitherT

  final type OptionT[F[_], A] = cats.data.OptionT[F, A]
  final val OptionT: cats.data.OptionT.type = cats.data.OptionT

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

  final type NEChain[+A] = cats.data.NonEmptyChain[A]
  final val NEChain: cats.data.NonEmptyChain.type = cats.data.NonEmptyChain

  final type NonEmptyChain[+A] = cats.data.NonEmptyChain[A]
  final val NonEmptyChain: cats.data.NonEmptyChain.type = cats.data.NonEmptyChain

  final type Kleisli[F[_], A, B] = cats.data.Kleisli[F, A, B]
  final val Kleisli: cats.data.Kleisli.type = cats.data.Kleisli

  final type ReaderT[F[_], A, B] = cats.data.ReaderT[F, A, B]
  final val ReaderT: cats.data.ReaderT.type = cats.data.ReaderT

  final type Reader[A, B] = cats.data.Reader[A, B]
  final val Reader: cats.data.Reader.type = cats.data.Reader

  final type AndThen[-T, +R] = cats.data.AndThen[T, R]
  final val AndThen: cats.data.AndThen.type = cats.data.AndThen

  final type AppFunc[F[_], A, B] = cats.data.AppFunc[F, A, B]
  final val AppFunc: cats.data.AppFunc.type = cats.data.AppFunc

  final type Binested[F[_, _], G[_], H[_], A, B] = cats.data.Binested[F, G, H, A, B]
  final val Binested: cats.data.Binested.type = cats.data.Binested

  final type BinestedBifoldable[F[_, _], G[_], H[_]] = cats.data.BinestedBifoldable[F, G, H]
  final type BinestedBitraverse[F[_, _], G[_], H[_]] = cats.data.BinestedBitraverse[F, G, H]

  final type Cokleisli[F[_], A, B] = cats.data.Cokleisli[F, A, B]
  final val Cokleisli: cats.data.Cokleisli.type = cats.data.Cokleisli

  final type Const[A, B] = cats.data.Const[A, B]
  final val Const: cats.data.Const.type = cats.data.Const

  final type ContT[M[_], A, +B] = cats.data.ContT[M, A, B]
  final val ContT: cats.data.ContT.type = cats.data.ContT

  final type EitherK[F[_], G[_], A] = cats.data.EitherK[F, G, A]
  final val EitherK: cats.data.EitherK.type = cats.data.EitherK

  final type Func[F[_], A, B] = cats.data.Func[F, A, B]
  final val Func: cats.data.Func.type = cats.data.Func

  final type IdT[F[_], A] = cats.data.IdT[F, A]
  final val IdT: cats.data.IdT.type = cats.data.IdT

  final type IndexedReaderWriterStateT[F[_], E, L, SA, SB, A] = cats.data.IndexedReaderWriterStateT[F, E, L, SA, SB, A]
  final val IndexedReaderWriterStateT: cats.data.IndexedReaderWriterStateT.type = cats.data.IndexedReaderWriterStateT

  final type IndexedStateT[F[_], SA, SB, A] = cats.data.IndexedStateT[F, SA, SB, A]
  final val IndexedStateT: cats.data.IndexedStateT.type = cats.data.IndexedStateT

  final type Ior[+A, +B] = cats.data.Ior[A, B]
  final val Ior: cats.data.Ior.type = cats.data.Ior

  final type Nested[F[_], G[_], A] = cats.data.Nested[F, G, A]
  final val Nested: cats.data.Nested.type = cats.data.Nested

  final type NonEmptyLazyList[+A] = cats.data.NonEmptyLazyList[A]
  final val NonEmptyLazyList: cats.data.NonEmptyLazyList.type = cats.data.NonEmptyLazyList

  final type NELazyList[+A] = cats.data.NonEmptyLazyList[A]
  final val NELazyList: cats.data.NonEmptyLazyList.type = cats.data.NonEmptyLazyList

  final type OneAnd[F[_], A] = cats.data.OneAnd[F, A]
  final val OneAnd: cats.data.OneAnd.type = cats.data.OneAnd

  final type Op[Arr[_, _], A, B] = cats.data.Op[Arr, A, B]
  final val Op: cats.data.Op.type = cats.data.Op

  final type RepresentableStore[F[_], S, A] = cats.data.RepresentableStore[F, S, A]
  final val RepresentableStore: cats.data.RepresentableStore.type = cats.data.RepresentableStore

  final type Tuple2K[F[_], G[_], A] = cats.data.Tuple2K[F, G, A]
  final val Tuple2K: cats.data.Tuple2K.type = cats.data.Tuple2K

  final type Validated[+E, +A] = cats.data.Validated[E, A]
  final val Validated: cats.data.Validated.type = cats.data.Validated

  final type WriterT[F[_], L, V] = cats.data.WriterT[F, L, V]
  final val WriterT: cats.data.WriterT.type = cats.data.WriterT

  final type ValidatedNel[+E, +A] = cats.data.Validated[NonEmptyList[E], A]
  final type IorNel[+B, +A]       = cats.data.Ior[NonEmptyList[B], A]
  final type IorNec[+B, +A]       = cats.data.Ior[NonEmptyChain[B], A]
  final type IorNes[B, +A]        = cats.data.Ior[NonEmptySet[B], A]
  final type EitherNel[+E, +A]    = Either[NonEmptyList[E], A]
  final type EitherNec[+E, +A]    = Either[NonEmptyChain[E], A]
  final type EitherNes[E, +A]     = Either[NonEmptySet[E], A]
  final type ValidatedNec[+E, +A] = cats.data.Validated[NonEmptyChain[E], A]

  final type Writer[L, V] = cats.data.Writer[L, V]
  final val Writer: cats.data.Writer.type = cats.data.Writer

  final type IndexedState[S1, S2, A] = cats.data.IndexedState[S1, S2, A]
  final val IndexedState: cats.data.IndexedState.type = cats.data.IndexedState

  final type StateT[F[_], S, A] = cats.data.StateT[F, S, A]
  final val StateT: cats.data.StateT.type = cats.data.StateT

  final type State[S, A] = cats.data.State[S, A]
  final val State: cats.data.State.type = cats.data.State

  final type IRWST[F[_], E, L, SA, SB, A] = cats.data.IRWST[F, E, L, SA, SB, A]
  final val IRWST = cats.data.IndexedReaderWriterStateT

  final type ReaderWriterStateT[F[_], E, L, S, A] = cats.data.ReaderWriterStateT[F, E, L, S, A]
  final val ReaderWriterStateT: cats.data.ReaderWriterStateT.type = cats.data.ReaderWriterStateT

  final type RWST[F[_], E, L, S, A] = cats.data.RWST[F, E, L, S, A]
  final val RWST: cats.data.ReaderWriterStateT.type = cats.data.ReaderWriterStateT

  final type RWS[E, L, S, A] = cats.data.RWS[E, L, S, A]
  final val RWS: cats.data.RWS.type = cats.data.RWS

  final type Store[S, A] = cats.data.Store[S, A]
  final val Store: cats.data.Store.type = cats.data.Store

  final type Cont[A, B] = cats.data.Cont[A, B]
  final val Cont: cats.data.Cont.type = cats.data.Cont

  // ------------- cats.arrow ---------------

  final type ~>[F[_], G[_]] = cats.arrow.FunctionK[F, G]

  final type Arrow[F[_, _]] = cats.arrow.Arrow[F]
  final val Arrow: cats.arrow.Arrow.type = cats.arrow.Arrow

  final type ArrowChoice[F[_, _]] = cats.arrow.ArrowChoice[F]
  final val ArrowChoice: cats.arrow.ArrowChoice.type = cats.arrow.ArrowChoice

  final type Category[F[_, _]] = cats.arrow.Category[F]
  final val Category: cats.arrow.Category.type = cats.arrow.Category

  final type Choice[F[_, _]] = cats.arrow.Choice[F]
  final val Choice: cats.arrow.Choice.type = cats.arrow.Choice

  final type CommutativeArrow[F[_, _]] = cats.arrow.CommutativeArrow[F]
  final val CommutativeArrow: cats.arrow.CommutativeArrow.type = cats.arrow.CommutativeArrow

  final type Compose[F[_, _]] = cats.arrow.Compose[F]
  final val Compose: cats.arrow.Compose.type = cats.arrow.Compose

  final type FunctionK[F[_], G[_]] = cats.arrow.FunctionK[F, G]
  final val FunctionK: cats.arrow.FunctionK.type = cats.arrow.FunctionK

  final type Profunctor[F[_, _]] = cats.arrow.Profunctor[F]
  final val Profunctor: cats.arrow.Profunctor.type = cats.arrow.Profunctor

  final type Strong[F[_, _]] = cats.arrow.Strong[F]
  final val Strong: cats.arrow.Strong.type = cats.arrow.Strong

  //---------- cats-data ----------------

}
