import akka.actor.typed.ActorSystem
import com.typesafe.config.ConfigFactory
import UserActor.{AkkaCommand, Message, PrivateMessage, WelcomeUser}
import javafx.application.Platform

class ChatActorSystem extends ChatControllerImpl {
  var host: ActorSystem[AkkaCommand] = _
  private val privateCommand = "/pm "

  def publishMessage(login: String, msg: String): Unit = {
    if (msg.contains(privateCommand)) {
      val to = msg.split(" ")(1)
      val m =  msg.replace(privateCommand + msg.split(" ")(1), s"Приватное сообщение от $login для $to:")
      host ! PrivateMessage(login, to, m)
    } else {
      host ! Message(login, msg)
    }
  }

     def publishMyLogin(): Unit = this.synchronized {
      Thread.sleep(3000)
      host ! WelcomeUser
    }

  def createActorSystem(port: String, ip: String, chatUIController: ChatControllerImpl): Unit = this.synchronized {
    if (port.nonEmpty) {
      val conf = ConfigFactory.parseString(s"akka.remote.artery.canonical.port=$port," +
        s"""akka.remote.artery.canonical.hostname = "$ip"""").withFallback(ConfigFactory.load())
      this.host = ActorSystem(
        MainActor(chatUIController),
        "ClusterSystem",
        conf
      )
    }
  }

  def terminateActorSystem(): Unit = {
    Platform.exit()
    this.host.terminate()
    System.exit(0)
  }
}