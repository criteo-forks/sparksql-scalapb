addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.0")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.10")

addSbtPlugin("com.thesamet" % "sbt-protoc" % "0.99.25")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.8")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.1"

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value