package view;

import application.Main;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class UserCentreController {

	private static Stage userCentreStage;

	public static void setStage(Stage stage) {
		userCentreStage = stage;
	}

	@FXML
	private TextField usernameTextField;
	@FXML
	private Label regDateLabel;
	@FXML
	private ComboBox<String> interfaceComboBox;
	@FXML
	private ComboBox<String> contentComboBox;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	@FXML
	private void initialize() {
		usernameTextField.setText(User.getName());
		regDateLabel.setText(User.getDateString());
		ObservableList<String> languageList = FXCollections.observableArrayList();
		for (LanguageType lType : LanguageType.values()) {
			languageList.add(lType.toString());
		}
		interfaceComboBox.setItems(languageList);
		contentComboBox.setItems(languageList);
		interfaceComboBox.setValue(User.getInterfaceLanguage().toString());
		contentComboBox.setValue(User.getContentLanguage().toString());
	}

	@FXML
	private void save() {
		if (!usernameTextField.getText().equals("")) {
			User.setName(usernameTextField.getText());
			userCentreStage.hide();
			if (User.getInterfaceLanguage() != LanguageType.getLanguageType(interfaceComboBox.getSelectionModel().getSelectedIndex()) || User.getContentLanguage() != LanguageType.getLanguageType(contentComboBox.getSelectionModel().getSelectedIndex())) {
				User.setInterfaceLanguage(LanguageType.getLanguageType(interfaceComboBox.getSelectionModel().getSelectedIndex()));
				User.setContentLanguage(LanguageType.getLanguageType(contentComboBox.getSelectionModel().getSelectedIndex()));
				User.writeUser();
				userCentreStage.hide();
				MainController.hideStage();
				new Main().start(new Stage());
				return;
			}
			User.writeUser();
		}
	}

	@FXML
	private void cancel() {
		userCentreStage.hide();
	}
}