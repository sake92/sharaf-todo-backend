import scala.scalanative.build.Mode

ThisBuild / scalaVersion := "3.9.0"

lazy val root = project.in(file("."))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "sharaf-todo-backend",
    Compile / scalaSource := baseDirectory.value / "app" / "src",
    Compile / mainClass := Some("ba.sake.todo.backend.main"),
    Compile / crossTarget := baseDirectory.value / "target" / "native",
    scalacOptions ++= Seq("-Yretain-trees", "-deprecation", "-Wunused:all"),
    libraryDependencies ++= Seq(
      "ba.sake" %% "sharaf-potateh" % "0.21.0",
      "com.github.lolgab" %% "scala-native-crypto" % "0.4.0"
    ),
    nativeConfig ~= { _.withMode(Mode.releaseFast).withMultithreading(true) }
  )
