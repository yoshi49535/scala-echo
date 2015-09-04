package echo.remote.server

import akka.util.Timeout
import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import scala.Application
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Boot extends App {
  val config = ConfigFactory.load()
    .withFallback(ConfigFactory.load("reference.conf"))

  implicit val system = ActorSystem("remote-server")
  implicit val timeout:Timeout = 3.seconds
  
  try {
    val serverActor = system.actorOf(echo.ServerServiceActor.props, "echo-server")
  } catch {
    case e:Throwable => system.shutdown
  }
}

