ThisBuild / scalaVersion := "3.9.0"

lazy val root = project.in(file("."))
  .settings(
    name := "sharaf-todo-backend",
    Compile / scalaSource := baseDirectory.value / "app" / "src",
    Compile / resourceDirectory := baseDirectory.value / "app" / "resources",
    Compile / mainClass := Some("ba.sake.todo.backend.main"),
    scalacOptions ++= Seq("-Yretain-trees", "-deprecation", "-Wunused:all"),
    libraryDependencies ++= Seq(
      "ba.sake" %% "sharaf-potateh" % "0.21.0",
      "ba.sake" %% "tupson-config" % "0.31.0",
      "ch.qos.logback" % "logback-classic" % "1.4.6"
    ),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case path => (assembly / assemblyMergeStrategy).value(path)
    },
    assembly / assemblyOutputPath := Def.uncached(baseDirectory.value / "target" / "app.jar")
  )
