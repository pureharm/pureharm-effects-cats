# pureharm-effects-cats

See [changelog](./CHANGELOG.md).

## modules

### Scala versions
Scala `2.13`, `3.0.1`, for JVM and JS.

The available modules are:

- `"com.busymachines" %% "pureharm-effects-cats" % "0.5.0"`
    - in future versions this will be the only module. N.B. that this module is mutually exclusive with the one below, the latter existing for migration purposes towards cats-effect 3
    - [cats](https://github.com/typelevel/cats/releases) `2.6.1`
    - [cats-effect](https://github.com/typelevel/cats-effect/releases) `3.2.0`
    - [fs2-core](https://github.com/typelevel/fs2/releases) `3.0.6`
    - [pureharm-core-anomaly](https://github.com/busymachines/pureharm-core/releases) `0.3.0`
    - [pureharm-core-sprout](https://github.com/busymachines/pureharm-core/releases) `0.3.0`
- `"com.busymachines" %% "pureharm-effects-cats-2" % "0.5.0"` 
    - [cats](https://github.com/typelevel/cats/releases) `2.6.1`
    - [cats-effect](https://github.com/typelevel/cats-effect/releases) `2.5.2`
    - [fs2-core](https://github.com/typelevel/fs2/releases) `2.5.9`
    - [pureharm-core-anomaly](https://github.com/busymachines/pureharm-core/releases) `0.3.0`
    - [pureharm-core-sprout](https://github.com/busymachines/pureharm-core/releases) `0.3.0`

## usage

Under construction. See [release notes](https://github.com/busymachines/pureharm-effects-cats/releases) and tests for examples.

The recommended way of making use of this module is to create your own "effects" package giving you seemless pure-functional scala experience without import confusion. It's for developers who realized that you can't really write any production apps without treating `cats`, `cats-effect`, `fs2` as being standard library.

```scala
package myapp

import busymachines.pureharm

package object effects extends pureharm.effects.PureharmEffectsAliases with pureharm.effects.PureharmEffectsImplicits {
  //write your own custom opiniated things here!
}

//everywhere else in your code just do:
import myapp.effects._

//instead of:
import cats._
import cats.implicits._
import cats.effect._
import cats.effect.implicits._
import fs2._
```

## Copyright and License

All code is available to you under the Apache 2.0 license, available
at [http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0) and also in
the [LICENSE](./LICENSE) file.
