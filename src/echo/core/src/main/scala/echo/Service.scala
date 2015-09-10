package echo

import akka.actor.{Actor, ActorRef, ActorSelection, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}

trait Service {
  def echo(message:String)(implicit ec:ExecutionContext) : Future[String]
}

object ServiceActorLike {
  trait Protocol {
    trait RequestEvent
    trait ResponseModel

    case class EchoRequest(message:String) extends RequestEvent
    case class EchoResponse(message:String) extends ResponseModel
  }
  case object Protocol extends Protocol 
}

trait ServiceActorLike {
  this:Actor with Service => 

  val protocol : ServiceActorLike.Protocol = ServiceActorLike.Protocol
  import protocol._

  implicit def ec:ExecutionContext

  def receive:Receive = {
    case EchoRequest(message) => {
      echo(message)
        .map(r => EchoResponse(r))
        .pipeTo(sender)
    }
  }
}

trait ProxyService extends Service {
  def endpoint : Service

  def echo(message:String)(implicit ec:ExecutionContext) = {
    endpoint.echo(message)
  }
}

object ActorRefService {
  def apply(ref:ActorRef)(implicit ec:ExecutionContext, to:Timeout) = {
    new ActorRefService(ref)
  }
}

class ActorRefService(val endpoint:ActorRef)(implicit val ec:ExecutionContext, val actorTimeout:Timeout) extends Service {
 
  val protocol : ServiceActorLike.Protocol = ServiceActorLike.Protocol

  import protocol._

  def echo(message:String)(implicit ec:ExecutionContext) = {
    (endpoint ? protocol.EchoRequest(message))
      .mapTo[EchoResponse]
      .map(r => r.message)
  }
}

object ActorSelectionService {
  def apply(ref:ActorSelection)(implicit ec:ExecutionContext, to:Timeout) = {
    new ActorSelectionService(ref)
  }
}

class ActorSelectionService(val endpoint:ActorSelection)(implicit val ec:ExecutionContext, val actorTimeout:Timeout) extends Service {
 
  val protocol : ServiceActorLike.Protocol = ServiceActorLike.Protocol

  import protocol._

  def echo(message:String)(implicit ec:ExecutionContext) = {
    (endpoint ? protocol.EchoRequest(message))
      .mapTo[EchoResponse]
      .map(r => r.message)
  }
}

