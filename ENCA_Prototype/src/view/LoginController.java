package view;

import de.fhl.enca.bl.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public final class LoginController {

	@FXML
	private Button loginButton;

	@FXML
	private Label usernameLabel;

	@FXML
	private void initialize() {
		usernameLabel.setText(User.getName());
	}

	@FXML
	private void login() {}
}