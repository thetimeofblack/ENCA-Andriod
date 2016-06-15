package view;

import application.Login;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	private Label restartLabel;
	@FXML
	private Button saveButton;

	private BooleanProperty interfaceChanged = new SimpleBooleanProperty(false);
	private BooleanProperty contentChanged = new SimpleBooleanProperty(false);
	private BooleanProperty needrestart = new SimpleBooleanProperty(false);

	@FXML
	private void initialize() {
		usernameTextField.setText(User.getName());
		regDateLabel.setText(User.getDateString());
		ChangeListener<Boolean> listener = (ObservableValue<? extends Boolean> a, Boolean b, Boolean c) -> needrestart.setValue(interfaceChanged.getValue() || contentChanged.getValue());
		interfaceChanged.addListener(listener);
		contentChanged.addListener(listener);
		needrestart.addListener((ObservableValue<? extends Boolean> a, Boolean b, Boolean c) -> restartLabel.setVisible(needrestart.getValue()));
		ObservableList<String> languageList = FXCollections.observableArrayList();
		for (LanguageType lType : LanguageType.values()) {
			languageList.add(lType.toString());
		}
		interfaceComboBox.setItems(languageList);
		interfaceComboBox.setValue(User.getInterfaceLanguage().toString());
		interfaceComboBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> a, Number b, Number c) -> interfaceChanged.setValue(User.getInterfaceLanguage() != LanguageType.getLanguageType(interfaceComboBox.getSelectionModel().getSelectedIndex())));
		contentComboBox.setItems(languageList);
		contentComboBox.setValue(User.getContentLanguage().toString());
		contentComboBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> a, Number b, Number c) -> contentChanged.setValue(User.getContentLanguage() != LanguageType.getLanguageType(contentComboBox.getSelectionModel().getSelectedIndex())));
		restartLabel.setVisible(false);
	}

	@FXML
	private void save() {
		if (!usernameTextField.getText().equals("")) {
			User.setName(usernameTextField.getText());
			User.setInterfaceLanguage(LanguageType.getLanguageType(interfaceComboBox.getSelectionModel().getSelectedIndex()));
			User.setContentLanguage(LanguageType.getLanguageType(contentComboBox.getSelectionModel().getSelectedIndex()));
			User.writeUser();
			userCentreStage.hide();
			if (needrestart.getValue()) {
				MainController.hideStage();
				new Login().start(new Stage());
			}
		}
	}

}