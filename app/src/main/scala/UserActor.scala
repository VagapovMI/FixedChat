import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object UserActor {

  sealed trait AkkaCommand

  case object WelcomeUser extends AkkaCommand
  case class ExitUser(nickname: String) extends AkkaCommand
  case class LoginUser(nickname: String) extends AkkaCommand
  case class PrivateMessage(sender: String, to: String, text: String) extends AkkaCommand
  case class Message(sender: String, text: String) extends AkkaCommand

  def apply(chatActorSystemImpl: ChatControllerImpl): Behavior[AkkaCommand] = newActor(chatActorSystemImpl)

  private def newActor(chatActorSystemImpl: ChatControllerImpl): Behavior[AkkaCommand] = {
    Behaviors.setup(context => new UserActor(context, chatActorSystemImpl))
  }

  class UserActor(context: ActorContext[AkkaCommand], chatControllerImpl: ChatControllerImpl) extends AbstractBehavior[AkkaCommand](context) {
    override def onMessage(message: AkkaCommand): Behavior[AkkaCommand] = {
      val login = chatControllerImpl.getUserLogin

      message match {

        case WelcomeUser =>
          chatControllerImpl.getChatActorSystem.host ! LoginUser(login)
          this

        case Message(sender, text) =>
          applyMessage(sender, text)
          this

        case LoginUser(nick) =>
          if (!chatControllerImpl.usersTab.getText.contains(nick)) {
            chatControllerImpl.usersTab.appendText(s"$nick\n")
          }
          this

        case PrivateMessage(sender, to, text) =>
          if (login.equals(to) || login.equals(sender)) {
            applyPrivateMessage(text)
          }
          this
        case ExitUser(nickname) =>
          chatControllerImpl.usersTab.setText(chatControllerImpl.usersTab.getText.replace(s"$nickname\n", "").trim)
          this
      }
    }

    private def applyMessage(sender: String, msg: String): Unit = {
      chatControllerImpl.outputField.appendText(s"[$sender]: $msg\n")
    }
    private def applyPrivateMessage(msg: String): Unit = {
      chatControllerImpl.outputField.appendText(s"$msg\n")
    }
  }
}