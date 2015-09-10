package echo

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}

object ClientServiceImpl {
  def apply(server:Service) = new ClientServiceImpl {
    override val endpoint = server
  }
}

trait ClientServiceImpl extends ProxyService {
}

object ClientServiceActor {
  def props(server:ActorRef)(implicit ec:ExecutionContext, to:Timeout) : Props = Props(new ClientServiceActor(ActorRefService(server)))
  def props(server:Service)(implicit ec:ExecutionContext, to:Timeout) : Props = Props(new ClientServiceActor(server))
}

class ClientServiceActor(override val endpoint:Service)(implicit val ec:ExecutionContext) extends Actor 
  with ServiceActorLike 
  with ClientServiceImpl 
{
  
}

