package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Person;

public final class TableViewTestController {

	@FXML
	private TableView<Person> tableView;

	@FXML
	private TableColumn<Person, String> nameColumn;

	@FXML
	private TableColumn<Person, Integer> scoreColumn;

	@FXML
	private Button confirmButton;

	@FXML
	private Label label;

	@FXML
	private void initialize() {
		ObservableList<Person> list = FXCollections.observableArrayList(new Person("Bobby", 90), new Person("Mark", 100), new Person("Tony", 80));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
		tableView.setItems(list);
	}

	@FXML
	private void confirm() {
		label.setText(tableView.getSelectionModel().getSelectedItem().getName());
	}
}