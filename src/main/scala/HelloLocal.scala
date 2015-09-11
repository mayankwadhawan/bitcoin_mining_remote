/**
 * Created by mayank on 9/11/15.
 */
import akka.actor._

object Local extends App {

  implicit val system = ActorSystem("LocalSystem")
  val localActor = system.actorOf(Props[LocalActor], name = "LocalActor")  // the local actor
  localActor ! "START"                                                     // start the action

}

class LocalActor extends Actor {

  // create the remote actor (Akka 2.1 syntax)
  val remote = context.actorFor("akka.tcp://Bitcoin-server@127.0.0.1:2552/user/masterActor")
  var counter = 0

  def receive = {
    case "START" =>
      remote ! "MORE WORK"
    case msg: String =>
      println(s"LocalActor received message: '$msg'")
      if (counter < 5) {
        sender ! "Hello back to you"
        counter += 1
      }
  }
}