package echo
package decorate

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.{ask, pipe}
import akka.util.Timeout
import scala.concurrent.{Future, ExecutionContext}

trait DecoratedService extends Service {
  var prefix:String
  var postfix:String
}

object DecoratedServiceActorLike {
  trait Protocol extends ServiceActorLike.Protocol {
    case class SetDecorateEvent(prefix:String, postfix:String) extends RequestEvent
    case class SetDeorateResponse() extends ResponseModel
  }
  object Protocol extends Protocol 
}

trait DecoratedServiceActorLike extends ServiceActorLike {
  this:Actor with DecoratedService => 

  override val protocol = DecoratedServiceActorLike.Protocol
  import protocol._

  override def receive:Receive = super.receive.orElse({
    case SetDecorateEvent(prefix, postfix) => {
      this.prefix  = prefix
      this.postfix = postfix
    }
  })
}


