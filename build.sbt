enablePlugins(ScalaJSPlugin)

name := "mood"

scalaVersion := "2.12.6"

scalaJSUseMainModuleInitializer := true

libraryDependencies ++= Seq(
  "com.github.julien-truffaut"  %%% "monocle-core"  % "1.5.0",
  "com.github.julien-truffaut"  %%% "monocle-macro" % "1.5.0",
  "io.circe"                    %%% "circe-core"    % "0.11.1",
  "io.circe"                    %%% "circe-generic" % "0.11.1",
  "io.circe"                    %%% "circe-parser"  % "0.11.1",
  "org.typelevel"               %%% "cats-core"     % "1.5.0",
  "org.scalatest"               %%% "scalatest"     % "3.0.0" % "test"
)

scalacOptions ++= Seq(
  "-P:scalajs:sjsDefinedByDefault",
  "-Ypartial-unification"
)

scalacOptions in Test ++= Seq("-Yrangepos")
