package view;

import java.util.Date;
import application.Main;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

	@FXML
	private void initialize() {
		ObservableList<String> languageList = FXCollections.observableArrayList();
		for (LanguageType lType : LanguageType.values()) {
			languageList.add(lType.toString());
		}
		interfaceComboBox.setItems(languageList);
		interfaceComboBox.setValue(User.getInterfaceLanguage().toString());
		contentComboBox.setItems(languageList);
		contentComboBox.setValue(User.getContentLanguage().toString());
	}

	@FXML
	private void login() {
		if (!usernameTextField.getText().equals("") && !interfaceComboBox.getSelectionModel().isEmpty() && !contentComboBox.getSelectionModel().isEmpty()) {
			User.setName(usernameTextField.getText());
			User.setRegDate(new Date());
			User.setFirstUse(false);
			User.setInterfaceLanguage(LanguageType.getLanguageType(interfaceComboBox.getSelectionModel().getSelectedIndex()));
			User.setContentLanguage(LanguageType.getLanguageType(contentComboBox.getSelectionModel().getSelectedIndex()));
			User.writeUser();
			new Main().start(new Stage());
			loginStage.hide();
		}
	}

	public void setStage(Stage stage) {
		this.loginStage = stage;
	}
}