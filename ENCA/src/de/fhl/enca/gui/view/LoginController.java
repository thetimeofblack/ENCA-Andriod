package de.fhl.enca.gui.view;

import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;
import de.fhl.enca.gui.application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller of Login interface when it is not user's first use.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class LoginController {

	@FXML
	private Button loginButton;
	@FXML
	private Label usernameLabel;

	private Stage loginStage;

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		Initialize.initialize();
		usernameLabel.setText(User.getName());
	}

	@FXML
	private void login() {
		new Main().start(new Stage());
		loginStage.hide();
	}

	public void setLoginStage(Stage stage) {
		this.loginStage = stage;
	}
}