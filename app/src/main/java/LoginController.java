import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class LoginController {
    @FXML
    protected TextField nameInput;
    @FXML
    protected TextField portInput;
    @FXML
    protected TextField ipInput;
    @FXML
    protected Button loginButton;

    @FXML
    protected void onLoginButton(ActionEvent event) {
    }
}
