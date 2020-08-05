package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public JFXTextField usernameTextField;
    public JFXPasswordField passwordTextField;
    public Button loginButton;

    // Need combine with enter key but don't know how yet...
    public void validateAccount(MouseEvent event) throws IOException {
	String username = usernameTextField.getText();
	String password = passwordTextField.getText();

	// Validate database eeee...
	if (Main.sm.login(username, password)) {
	    Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/FXML/mainMenu.fxml"));
	    Scene mainMenuScene = new Scene(mainMenuParent, 1366, 768);

	    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    window.setTitle("Main Menu - Huahee Library");
	    window.setScene(mainMenuScene);
	    window.centerOnScreen();
	}
    }
}