package view;

import application.Main;
import de.fhl.enca.bl.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public final class LoginController {

	@FXML
	private Button loginButton;
	@FXML
	private Label usernameLabel;

	private Stage loginStage;

	@FXML
	private void initialize() {
		usernameLabel.setText(User.getName());
	}

	@FXML
	private void login() {
		new Main().start(new Stage());
		loginStage.hide();
	}

	public void setStage(Stage stage) {
		this.loginStage = stage;
	}
}