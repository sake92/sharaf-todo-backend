import mill._
import mill.scalalib._

object app extends ScalaModule {

  def mainClass = T(Some("ba.sake.todo.backend.main"))

  def scalaVersion = "3.3.1"

  def scalacOptions = super.scalacOptions() ++ Seq(
    "-Yretain-trees",
    "-deprecation",
    "-Wunused:all"
  )

  def ivyDeps = Agg(
    ivy"ba.sake::sharaf:0.0.11",
    ivy"ch.qos.logback:logback-classic:1.4.6"
  )

  object test extends ScalaTests with TestModule.Munit {
    def ivyDeps = Agg(
      ivy"org.scalameta::munit::0.7.29"
    )
  }
}
