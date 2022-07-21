import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public abstract class ChatController {
    @FXML
    protected TextField inputField;
    @FXML
    protected TextArea outputField;
    @FXML
    protected TextArea usersTab;
    @FXML
    protected Button exitButton;

    @FXML
    protected void onExitButton() {
    }
    @FXML
    protected void onSendButton() {
    }
}