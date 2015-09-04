package echo

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}

object Protocol {
  trait Request
  trait Response 

  case class EchoRequest(message:String) extends Request
  case class EchoResponse(message:String) extends Response
}

trait Service {
  def echo(message:String)(implicit ec:ExecutionContext) : Future[String]
}

trait ServiceActorLike {
  this:Actor with Service => 

  implicit def ec:ExecutionContext

  def receive:Receive = {
    case Protocol.EchoRequest(message) => {
      echo(message)
        .map(r => Protocol.EchoResponse(r))
        .pipeTo(sender)
    }
  }
}

object ActorService {
  def apply(ref:ActorRef)(implicit ec:ExecutionContext, to:Timeout) = {
    new ActorService(ref)
  }
}

class ActorService(val endpoint:ActorRef)(implicit val ec:ExecutionContext, val actorTimeout:Timeout) extends Service {
  def echo(message:String)(implicit ec:ExecutionContext) = {
    (endpoint ? Protocol.EchoRequest(message))
      .mapTo[Protocol.EchoResponse]
      .map(r => r.message)
  }
}

