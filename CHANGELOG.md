# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

# unreleased

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
