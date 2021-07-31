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

//=============================================================================
//============================== build details ================================
//=============================================================================

Global / onChangedBuildSource := ReloadOnSourceChanges

// format: off
val Scala213    = "2.13.6"
val Scala3      = "3.0.1"
// format: on

//=============================================================================
//============================ publishing details =============================
//=============================================================================

//see: https://github.com/xerial/sbt-sonatype#buildsbt
ThisBuild / sonatypeCredentialHost := "s01.oss.sonatype.org"

ThisBuild / baseVersion      := "0.5"
ThisBuild / organization     := "com.busymachines"
ThisBuild / organizationName := "BusyMachines"
ThisBuild / homepage         := Option(url("https://github.com/busymachines/pureharm-effects-cats"))

ThisBuild / scmInfo := Option(
  ScmInfo(
    browseUrl  = url("https://github.com/busymachines/pureharm-effects-cats"),
    connection = "git@github.com:busymachines/pureharm-effects-cats.git",
  )
)

/** I want my email. So I put this here. To reduce a few lines of code, the sbt-spiewak plugin generates this (except
  * email) from these two settings:
  * {{{
  * ThisBuild / publishFullName   := "Loránd Szakács"
  * ThisBuild / publishGithubUser := "lorandszakacs"
  * }}}
  */
ThisBuild / developers := List(
  Developer(
    id    = "lorandszakacs",
    name  = "Loránd Szakács",
    email = "lorand.szakacs@protonmail.com",
    url   = new java.net.URL("https://github.com/lorandszakacs"),
  )
)

ThisBuild / startYear  := Some(2019)
ThisBuild / licenses   := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

//until we get to 1.0.0, we keep strictSemVer false
ThisBuild / strictSemVer              := false
ThisBuild / spiewakCiReleaseSnapshots := false
ThisBuild / spiewakMainBranches       := List("main")
ThisBuild / Test / publishArtifact    := false

ThisBuild / scalaVersion       := Scala213
ThisBuild / crossScalaVersions := List(Scala213, Scala3)

//required for binary compat checks
ThisBuild / versionIntroduced := Map(
  Scala213 -> "0.1.0",
  Scala3   -> "0.5.0",
)

//=============================================================================
//================================ Dependencies ===============================
//=============================================================================
ThisBuild / resolvers += Resolver.sonatypeRepo("releases")
ThisBuild / resolvers += Resolver.sonatypeRepo("snapshots")

// format: off
val catsV            = "2.6.1"       //https://github.com/typelevel/cats/releases
val catsEffectV      = "3.2.0"       //https://github.com/typelevel/cats-effect/releases
val catsEffect2V     = "2.5.2"       //https://github.com/typelevel/cats-effect/releases
val fs2V             = "3.0.6"       //https://github.com/typelevel/fs2/releases
val fs22V            = "2.5.9"       //https://github.com/typelevel/fs2/releases
val pureharmCoreV    = "0.3.0"       //https://github.com/busymachines/pureharm-core/releases
val munitCE3V        = "1.0.5"       //https://github.com/typelevel/munit-cats-effect/releases
val munitCE2V        = "1.0.5"       //https://github.com/typelevel/munit-cats-effect/releases
// format: on
//=============================================================================
//============================== Project details ==============================
//=============================================================================

lazy val root = project
  .in(file("."))
  .aggregate(
    `effects-catsJVM`,
    `effects-catsJS`,
    `effects-cats-2JVM`,
    `effects-cats-2JS`,
  )
  .enablePlugins(NoPublishPlugin)
  .enablePlugins(SonatypeCiReleasePlugin)
  .settings(
    scalacOptions ++= scalaCompilerOptions(scalaVersion.value)
  )

lazy val `effects-cats` = crossProject(JVMPlatform, JSPlatform)
  .settings(
    scalacOptions ++= scalaCompilerOptions(scalaVersion.value)
  )
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .settings(
    name := "pureharm-effects-cats",
    libraryDependencies ++= Seq(
      // format: off
      "org.typelevel"       %%% "cats-core"                   % catsV                    withSources(),
      "org.typelevel"       %%% "cats-effect"                 % catsEffectV              withSources(),
      "co.fs2"              %%% "fs2-core"                    % fs2V                     withSources(),
      "com.busymachines"    %%% "pureharm-core-anomaly"       % pureharmCoreV            withSources(),
      "com.busymachines"    %%% "pureharm-core-sprout"        % pureharmCoreV            withSources(),
      "org.typelevel"       %%% "munit-cats-effect-3"         % munitCE3V         % Test withSources(),
      // format: on
    ),
  )

lazy val `effects-catsJVM` = `effects-cats`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val `effects-catsJS` = `effects-cats`.js

lazy val `effects-cats-2` = crossProject(JVMPlatform, JSPlatform)
  .settings(
    scalacOptions ++= scalaCompilerOptions(scalaVersion.value),
    headerSources / excludeFilter := HiddenFileFilter || "*RandomImpl.scala" || "*Random.scala",
  )
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )
  .settings(
    name := "pureharm-effects-cats-2",
    libraryDependencies ++= Seq(
      // format: off
      "org.typelevel"       %%% "cats-core"                   % catsV                    withSources(),
      "org.typelevel"       %%% "cats-effect"                 % catsEffect2V             withSources(),
      "co.fs2"              %%% "fs2-core"                    % fs22V                    withSources(),
      "com.busymachines"    %%% "pureharm-core-anomaly"       % pureharmCoreV            withSources(),
      "com.busymachines"    %%% "pureharm-core-sprout"        % pureharmCoreV            withSources(),
      "org.typelevel"       %%% "munit-cats-effect-2"         % munitCE2V         % Test withSources(),
      // format: on
    ),
  )

lazy val `effects-cats-2JVM` = `effects-cats-2`.jvm.settings(
  javaOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val `effects-cats-2JS` = `effects-cats-2`.js

//=============================================================================
//================================= Settings ==================================
//=============================================================================

def scalaCompilerOptions(scalaVersion: String): Seq[String] =
  CrossVersion.partialVersion(scalaVersion) match {
    case Some((2, _)) =>
      Seq[String](
        //"-Xsource:3"
      )
    case _            => Seq.empty[String]
  }
