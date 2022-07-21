import javafx.application.Application
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.{Stage, WindowEvent}

object AppMain {
 def main(args: Array[String]): Unit = {
  Application.launch(classOf[AppMain], args: _*)
 }
}

class AppMain extends Application {
  override def start(primaryStage: Stage): Unit = {
    val root: Parent = FXMLLoader.load(getClass.getResource("Login.fxml"))
    val scene: Scene = new Scene(root, 900, 600)
    primaryStage.setTitle("Messenger")
    primaryStage.setScene(scene)
    primaryStage.show()
    primaryStage.setResizable(false)
    primaryStage.setOnCloseRequest(new EventHandler[WindowEvent]() {
      override def handle(t: WindowEvent): Unit = {
        System.exit(0)
      }
    })
  }
}