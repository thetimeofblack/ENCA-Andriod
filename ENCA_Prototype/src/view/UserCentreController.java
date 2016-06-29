package view;

import application.CleaningAgentDetail;
import application.Main;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public final class UserCentreController {

	@FXML
	private TextField usernameTextField;
	@FXML
	private Label regDateLabel;
	@FXML
	private ComboBox<String> interfaceComboBox;
	@FXML
	private ComboBox<String> contentComboBox;
	@FXML
	private FlowPane flowPane;
	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	private Stage mainStage;

	private Stage userCentreStage;

	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	public void setUserCentreStage(Stage userCentreStage) {
		this.userCentreStage = userCentreStage;
	}

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
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentsWithMemo()) {
			ImageView imageView = new ImageView(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgent));
			imageView.setFitWidth(100);
			imageView.setFitHeight(100);
			imageView.setOnMouseClicked(e -> new CleaningAgentDetail(cleaningAgent).start(new Stage()));
			flowPane.getChildren().add(imageView);
		}
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
				mainStage.hide();
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