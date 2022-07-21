import javafx.event.ActionEvent
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage

class LoginControllerImpl extends LoginController{

  override def onLoginButton(event: ActionEvent): Unit = this.synchronized {
    if (nameInput.getText.nonEmpty) {
      val fxmlLoader = new FXMLLoader(getClass.getResource("Chat.fxml"))
      val root = fxmlLoader.load.asInstanceOf[Parent]
      val chatController = fxmlLoader.getController[ChatControllerImpl]
      val port = portInput.getText.trim
      val stage = new Stage()
      val ip = ipInput.getText.trim
      val loginFromLoginFrame = nameInput.getText.trim
      loginButton.getScene.setRoot(root)
      chatController.createChatRoom(loginFromLoginFrame, ip, port)
      stage.setResizable(false)
    }
  }
}