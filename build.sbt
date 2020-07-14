scalaVersion := "2.13.3"

// Monix Minitest:
libraryDependencies += "io.monix" %% "minitest" % "2.8.2" % "test"
testFrameworks += new TestFramework("minitest.runner.Framework")

// Hedgehog:
val hedgehogVersion = "bcee5de37940942d83975ee1c3adae784e3f26fc"
libraryDependencies ++= Seq(
  "qa.hedgehog" %% "hedgehog-core" % hedgehogVersion,
  "qa.hedgehog" %% "hedgehog-runner" % hedgehogVersion,
  "qa.hedgehog" %% "hedgehog-sbt" % hedgehogVersion,
  "org.apache.kafka" % "kafka-clients" % "2.5.0"
)
resolvers += "bintray-scala-hedgehog" at "https://dl.bintray.com/hedgehogqa/scala-hedgehog"
testFrameworks += TestFramework("hedgehog.sbt.Framework")