package echo.remote.client

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

  implicit val system = ActorSystem("remote-client")
  implicit val timeout:Timeout = 3.seconds
  
  try {
    val serverSelection = system.actorSelection(config.getString("server.remote"))
    //val serverActor = Await.result(serverSelection.resolveOne(), 10.seconds)

    //val client = echo.ClientServiceImpl(echo.ActorRefService(serverActor))
    val client = echo.ClientServiceImpl(echo.ActorSelectionService(serverSelection))

    val res = Await.result(client.echo("test"), 10.seconds)
    println(res)
  } finally {
    system.shutdown
  }
}
