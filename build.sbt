scalaVersion := "3.9.0"

lazy val root = project.in(file("."))
  .enablePlugins(NativeImagePlugin)
  .settings(
    name := "sharaf-todo-backend",
    Compile / mainClass := Some("ba.sake.todo.backend.main"),
    scalacOptions ++= Seq("-Yretain-trees", "-deprecation", "-Wunused:all"),
    libraryDependencies += "ba.sake" %% "sharaf-potateh" % "0.24.0",
    nativeImageCommand := Seq("native-image"),
    nativeImageOptions ++= Seq("--no-fallback", "-march=compatibility")
  )
