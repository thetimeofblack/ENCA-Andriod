package de.fhl.enca.gui.view;

import java.util.Date;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import de.fhl.enca.gui.application.About;
import de.fhl.enca.gui.application.Main;
import de.fhl.enca.gui.application.Manual;
import de.fhl.enca.gui.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller of Login interface when it is user's first use.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class LoginFirstController {

	@FXML
	private AnchorPane pane;
	@FXML
	private ComboBox<String> interfaceComboBox;
	@FXML
	private ComboBox<String> contentComboBox;
	@FXML
	private Button loginButton;
	@FXML
	private TextField usernameTextField;

	private Stage loginStage;

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		interfaceComboBox.setItems(Utility.getLanguageList());
		interfaceComboBox.setValue(User.getInterfaceLanguage().toString());
		contentComboBox.setItems(Utility.getLanguageList());
		contentComboBox.setValue(User.getContentLanguage().toString());
	}

	/**
	 * Save user's preference and and call the main interface.
	 */
	@FXML
	private void login() {
		if (!usernameTextField.getText().equals("") && !interfaceComboBox.getSelectionModel().isEmpty() && !contentComboBox.getSelectionModel().isEmpty()) {
			User.setName(usernameTextField.getText());
			User.setRegDate(new Date());
			User.setInterfaceLanguage(LanguageType.getLanguageType(interfaceComboBox.getSelectionModel().getSelectedIndex()));
			User.setContentLanguage(LanguageType.getLanguageType(contentComboBox.getSelectionModel().getSelectedIndex()));
			User.writeUser();
			new Main().start(new Stage());
			new Manual().start(new Stage());
			loginStage.hide();
		}
	}

	/**
	 * Show manual.
	 */
	@FXML
	private void manual() {
		new Manual().start(new Stage());
	}

	/**
	 * Show about.
	 */
	@FXML
	private void about() {
		new About().start(new Stage());
	}

	public void setLoginFirstStage(Stage stage) {
		this.loginStage = stage;
	}
}