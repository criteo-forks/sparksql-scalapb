import ReleaseTransformations._

ThisBuild / organization := "com.thesamet.scalapb"

ThisBuild / scalacOptions ++= Seq("-deprecation", "-target:jvm-1.8")

ThisBuild / javacOptions ++= List("-target", "8", "-source", "8")

val Scala211 = "2.11.12"

val Scala212 = "2.12.15"

// Latest version published for Scala 2.111
val ScalapbVersion = "0.10.0-M4"

ThisBuild / scalaVersion := Scala211

lazy val sparkSqlScalaPB = project
  .in(file("sparksql-scalapb"))
  .settings(
    name := "sparksql-scalapb",
    crossScalaVersions := Seq(Scala211, Scala212),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "frameless-dataset" % "0.8.0",
      "com.thesamet.scalapb" %% "scalapb-runtime" % ScalapbVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime" % ScalapbVersion % "protobuf",
      "org.apache.spark" %% "spark-sql" % "2.4.6" % "provided",
      "org.apache.spark" %% "spark-sql" % "2.4.6" % "test",
      "org.scalatest" %% "scalatest" % "3.2.0" % "test",
      "org.scalatestplus" %% "scalacheck-1-14" % "3.2.0.0" % "test",
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.5" % "test"
    ),
    inConfig(Test)(
      sbtprotoc.ProtocPlugin.protobufConfigSettings
    ),
    Test / PB.targets := Seq(
      scalapb.gen(grpc = false) -> (Test / sourceManaged).value
    )
  )

ThisBuild / publishTo := sonatypePublishToBundle.value

releaseCrossBuild := true

releasePublishArtifactsAction := PgpKeys.publishSigned.value

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

lazy val root =
  project
    .in(file("."))
    .settings(
      publishArtifact := false,
      publish := {},
      publishLocal := {}
    )
    .aggregate(
      sparkSqlScalaPB
    )
