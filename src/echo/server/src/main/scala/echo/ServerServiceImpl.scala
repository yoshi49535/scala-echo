package echo

import akka.actor.{Actor, ActorRef, Props}
import scala.concurrent.{Future, ExecutionContext}

trait ServerServiceImpl extends Service {
  def echo(message:String)(implicit ec:ExecutionContext) = Future {
    "Echo : " + message
  }
}

object ServerServiceActor {
  def props(implicit ec:ExecutionContext) : Props = Props(new ServerServiceActor)
}

class ServerServiceActor(implicit val ec:ExecutionContext) extends Actor with ServiceActorLike with ServerServiceImpl {
}

