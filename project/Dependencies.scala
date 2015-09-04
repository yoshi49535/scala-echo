import sbt._

object Dependencies {
  import BuildSettings._

  val resolutionRepos = Seq(
    "Typesafe Releases" at "http://repo.typesafe.com/typesafe/releases/"
  )

  def compile   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")
  def provided  (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")
  def test      (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")
  def runtime   (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")
  def container (deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")
  
  val libAkkaActor       = "com.typesafe.akka"   %% "akka-actor"      % "2.3.12"
  val libAkkaRemote      = "com.typesafe.akka"   %% "akka-remote"     % "2.3.12"
  val libAkkaTestKit     = "com.typesafe.akka"   %% "akka-testkit"    % "2.3.12"
  val libTypesafeConfig  = "com.typesafe"        %  "config"          % "1.2.1"
}
