# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# unreleased

This is the first release for a stable Scala 3 version!

### :warning: breaking changes :warning
- the module from version 0.4.0 is now named `pureharm-effects-cats-2`, so if you did not yet upgrade your entire stack to cats-effect 3 then use this module. `pureharm-effects-cats` now refers to the module version based on cats-effect 3.

### :warning: removed deprecations
- removed `busymachines.pureharm.effects.PureharmEffectsImplicits`
- removed Applicative/Monad/Bracket Attempt type aliases

### new features

#### pureharm-effects-cats-2
- inline [cats-retry](https://github.com/cb372/cats-retry/releases/tag/v3.0.0)! and provide infix syntax support. We inlined because cats-retry does not have Scala 3 support yet.
- deprecate all previous .reattempt, and .timedReattempt syntax in favor of things inlined from cats-retry
- add `.timed : F[FiniteDuration]` (backport from cats-effect 3), and `.timedIn(t: TimeUnit): F[FiniteDuration]` syntax
- add `busymachines.pureharm.capabilities.Random[F]` capability trait. Use this for your randomness needs, including UUID generation. It is source compatible w/ the version provided in the `pureharm-effects-cats`, and the cats-effect 3 version.

### pureharm-effects-cats
- first compatible module w/ cats-effect `3.2.1`!
    - removed PureharmIOApp, you can safely use IOApp, ResourceApp, and kin from cats-effect.
    - removed `BlockingShifter`, sinice ContextShift, and Blocker are both gone from cats-effect
    - removed `ExecutionContextMT`, since it existed solely for the purpose of instantiating a good pool from main. With cats-effect 3 it's almost impossible to give a good replacement, so just rely on the default compute pool of cats-effect 3, it's really good!
- inline [cats-retry](https://github.com/cb372/cats-retry/releases/tag/v3.0.0)! and provide infix syntax support. We inlined because cats-retry does not have Scala 3 support yet.
- add `.timedIn(t: TimeUnit): F[FiniteDuration]` syntax
- deprecate all previous .reattempt, and .timedReattempt syntax in favor of things inlined from cats-retry


### new Scala versions:
- `2.13.6`
- `3.0.1` for JVM + JS platforms
- drop `3.0.0-RC2`, `3.0.0-RC3`

### Version upgrades:
- [cats](https://github.com/typelevel/cats) `2.6.1`
- [cats-effect](https://github.com/typelevel/cats-effect) `2.5.2`
- [fs2-core](https://github.com/typelevel/fs2) `2.5.9`
- [pureharm-core-anomaly](https://github.com/busymachines/pureharm-core/releases) `0.3.0`
- [pureharm-core-sprout](https://github.com/busymachines/pureharm-core/releases) `0.3.0`

### internals
- bump [munit-cats-effect](https://github.com/typelevel/munit-cats-effect/releases) to `1.0.5`
- bump scalafmt to `3.0.0-RC6` — from `2.7.5`
- bump sbt to `1.5.5`
- bump sbt-spiewak to `0.21.0`
- bump sbt-scalafmt to `2.4.3`
- bump sbt-scalajs-crossproject to `1.1.0`
- bump sbt-scalajs to `1.6.0`

# 0.4.0

- add a bunch of missing aliases from cats

### deprecations:

- deprecate `aliases.CatsImplicits` in favor of `aliases.CatsSyntax`, the latter does not bring in no longer needed `cats.instances.all._` import
- deprecate `PureharmImplicits` in favor of `PureharmSyntax`, the latter does not bring in no longer needed `cats.instances.all._` import

# 0.3.0

:warning: botched release :warning:

use `0.4.0`

# 0.2.0

### Features:

- loosen constraint on all `*attempt*` like methods from `Sync[F]` to `MonadThrow[F]`. There was no reason for it to be `Sync`.
- add `.reattempt` syntax for `Stream[F, A]`

### Removed deprecations:

- remove (Applicative/Monad/Bracket)Attempt types. Use The `*Throw` aliases.

### Version upgrades:

- [cats](https://github.com/typelevel/cats) `2.5.0`
- [cats-effect](https://github.com/typelevel/cats-effect) `2.4.1`
- [fs2-core](https://github.com/typelevel/fs2) `2.5.4`
- [pureharm-core-anomaly](https://github.com/busymachines/pureharm-core/releases) `0.2.0`
- [pureharm-core-sprout](https://github.com/busymachines/pureharm-core/releases) `0.2.0`

### New Scala versions:

- 3.0.0-RC2

### Internals:

- upgrade [munit-cats-effect](https://github.com/typelevel/munit-cats-effect/releases) to `1.0.1`

# 0.1.0

Split out from [pureharm](https://github.com/busymachines/pureharm) as of version `0.0.7`.

Newly cross published for both Scala 2.13, and 3.0.0-RC1 on JVM and JS runtimes.

### Changes

- add dependency and aliases for `fs2-core`

:warning: Source incompatible changes :warning::

- remove `Show[Throwable]` instances together with `PureharmShowInstances`. Use the ones from `pureharm-core`.
- remove `.onErrorF` syntax for `F[_]: Sync`
- remove `Attempt` and `IO` companion object ops extensions. Use postfix equivalent cats syntax instead. e.g. `(??? : Throwable).raiseError[F, A]`

### Dependencies:

- [cats](https://github.com/typelevel/cats) `2.4.2`
- [cats-effect](https://github.com/typelevel/cats-effect) `2.3.3`
- [fs2-core](https://github.com/typelevel/fs2) `2.5.3`

### Internals:

- replace scalatest w/ [munit-cats-effect](https://github.com/typelevel/munit-cats-effect/releases)
