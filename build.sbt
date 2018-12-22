enablePlugins(ScalaJSPlugin)

name := "mood"

scalaVersion := "2.12.6"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "org.scala-js"          %%% "scalajs-dom" % "0.9.6",
  "org.specs2"            %%  "specs2-core" % "4.3.4" % "test"
)

scalacOptions in Test ++= Seq("-Yrangepos")
