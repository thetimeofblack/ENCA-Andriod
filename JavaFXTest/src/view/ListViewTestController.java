package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public final class ListViewTestController {

	@FXML
	private ListView<String> listView;

	@FXML
	private Button confirmButton;

	@FXML
	private Label label;
	@FXML
	private Label label2;

	@FXML
	private void initialize() {
		ObservableList<String> list = FXCollections.observableArrayList("Bobby", "Tony", "Mark");
		listView.setItems(list);
		listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		label2.textProperty().bind(listView.getSelectionModel().selectedItemProperty());
	}

	@FXML
	private void confirm() {
		if (!listView.getSelectionModel().isEmpty()) {
			label.setText(listView.getSelectionModel().getSelectedItem());
		}
	}
}