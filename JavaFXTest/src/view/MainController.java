package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public final class MainController {

	private ObservableList<String> languageList = FXCollections.observableArrayList("English", "中文");

	@FXML
	private Button confirmButton;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private void initialize() {
		comboBox.setItems(languageList);
	}

	@FXML
	private void changeLabel() {
		label2.setText("ENCA");
	}
}