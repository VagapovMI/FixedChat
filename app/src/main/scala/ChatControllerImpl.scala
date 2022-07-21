import UserActor.ExitUser
import javafx.application.Platform

class ChatControllerImpl extends ChatController {

  private var chatRoom: ChatActorSystem = _
  private var login: String = _
  def createChatRoom(login: String, ip: String, port: String): Unit = this.synchronized {
    val cr = new ChatActorSystem
    this.login = login
    this.chatRoom = cr
    cr.createActorSystem(port, ip, this)
    cr.publishMyLogin()
  }

  def getChatActorSystem: ChatActorSystem = this.chatRoom

  def getUserLogin: String = this.login

  override def onExitButton(): Unit = {
    getChatActorSystem.host ! ExitUser(login)
    this.chatRoom.terminateActorSystem()
    Platform.exit()
    System.exit(0)
  }

  override def onSendButton(): Unit = {
    val msg = inputField.getText.replaceAll(" {2}", " ")
    this.chatRoom.publishMessage(this.login, msg)
    inputField.setText("")
  }
}