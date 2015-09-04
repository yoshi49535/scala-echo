import sbt._
import Keys._

object Build extends Build {
  import BuildSettings._
  import Dependencies._

  lazy val root = (project in file(""))
    .settings(basicSetting: _*) 
    .aggregate(
      echoServer
    )
    .settings(
      aggregate in update := false
    )

  lazy val echoCore = Project("echo-core", file("src/echo/core"))
    .settings(basicSetting: _*)
    .settings(
      libraryDependencies ++= 
        compile(libAkkaActor)
    )
      
  lazy val echoClient = Project("echo-client", file("src/echo/client"))
    .settings(basicSetting: _*)
    .settings(
      libraryDependencies ++= 
        compile(libAkkaActor)
    )
    .dependsOn(echoCore)
      
      
  lazy val echoServer = Project("echo-server", file("src/echo/server"))
    .settings(basicSetting: _*)
    .settings(
      libraryDependencies ++= 
        compile(libAkkaActor)
    )
    .dependsOn(echoCore)
}
