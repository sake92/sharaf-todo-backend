import mill._
import mill.scalalib._

object todo extends ScalaModule {

  def scalaVersion = "3.3.1"

  def scalacOptions = super.scalacOptions() ++ Seq(
    "-Yretain-trees",
    "-deprecation",
    "-Wunused:all"
  )

  def ivyDeps = Agg(
    ivy"ba.sake::sharaf:0.0.11"
  )

  object test extends ScalaTests with TestModule.Munit {
    def ivyDeps = Agg(
      ivy"org.scalameta::munit::0.7.29"
    )
  }
}
