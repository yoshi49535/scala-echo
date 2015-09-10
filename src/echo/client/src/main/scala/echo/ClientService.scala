package echo

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}

object ClientServiceImpl {
  def apply(e:Service) = new ClientServiceImpl {
    override val serverEndpoint = e
  }
}

trait ClientServiceImpl extends Service {
  // 
  def serverEndpoint : Service 

  def echo(message:String)(implicit ec:ExecutionContext) : Future[String] = {
    serverEndpoint.echo(message)
  }
}

object ClientServiceActor {
  def props(server:ActorRef)(implicit ec:ExecutionContext, to:Timeout) : Props = Props(new ClientServiceActor(ActorRefService(server)))
  def props(server:Service)(implicit ec:ExecutionContext, to:Timeout) : Props = Props(new ClientServiceActor(server))
}

class ClientServiceActor(override val serverEndpoint:Service)(implicit val ec:ExecutionContext) extends Actor with ServiceActorLike with ClientServiceImpl {
  
}

