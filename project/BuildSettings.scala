import sbt._
import Keys._

object BuildSettings {
  val VERSION      = "0.1"
  val ScalaVersion = "2.10.5"

  import Dependencies._

  lazy val basicSetting = Seq(
      homepage      := Some(new URL("http://1o1.co.jp")),
      organization  := "jp.com.1o1",
      organizationHomepage := Some(new URL("http://1o1.co.jp")),
      scalaVersion  := ScalaVersion,
      version       := VERSION,
      scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8"),
      resolvers     ++= Dependencies.resolutionRepos,
      sbtPlugin     := true,
      parallelExecution in Test := false
    )
}
