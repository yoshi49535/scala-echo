package echo

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}

trait ClientServiceImpl extends Service {
  // 
  def serverEndpoint : Service 

  implicit def actorTimeout:Timeout

  def echo(message:String)(implicit ec:ExecutionContext) : Future[String] = {
    (server ? Protocol.EchoRequest(message))
      .mapTo[Protocol.EchoResponse]
      .map(r => r.message)
  }
}

object ClientServiceActor {
  def props(server:ActorRef)(implicit ec:ExecutionContext, to:Timeout) : Props = Props(new ClientServiceActor(new ActorService(server)))
  def props(server:Service)(implicit ec:ExecutionContext, to:Timeout) : Props = Props(new ClientServiceActor(server))
}
class ClientServiceActor(override val serverEndpoint:Service)(implicit val ec:ExecutionContext, val actorTimeout:Timeout) extends Actor with ServiceActorLike with ClientServiceImpl {
  
}

