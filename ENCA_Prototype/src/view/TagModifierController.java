package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import application.TagAdder;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.TagFetcher;
import de.fhl.enca.controller.TagOperator;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public final class TagModifierController {

	private Stage stage;
	private Map<TagType, ListView<Tag>> listViewMap = new HashMap<>();
	private Map<LanguageType, TextField> textFieldMap = new HashMap<>();
	private Tag tag = null;

	@FXML
	private TabPane tabPane;

	@FXML
	private ListView<Tag> room;
	@FXML
	private ListView<Tag> item;
	@FXML
	private ListView<Tag> others;

	@FXML
	private TextField english;
	@FXML
	private TextField german;
	@FXML
	private TextField chinese;

	@FXML
	private Button save;
	@FXML
	private Button delete;
	@FXML
	private Button addNew;
	@FXML
	private Button cancel;

	@FXML
	private void initialize() {
		listViewMap.put(TagType.ROOM, room);
		listViewMap.put(TagType.ITEM, item);
		listViewMap.put(TagType.OTHERS, others);
		textFieldMap.put(LanguageType.ENGLISH, english);
		textFieldMap.put(LanguageType.GERMAN, german);
		textFieldMap.put(LanguageType.CHINESE, chinese);
		tabPane.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> o, Number x, Number y) -> refreshTextField());
		refreshListView();
		for (Entry<TagType, ListView<Tag>> entry : listViewMap.entrySet()) {
			entry.getValue().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag x, Tag y) -> refreshTextField());
		}
		for (TextField textField : textFieldMap.values()) {
			textField.textProperty().addListener((ObservableValue<? extends String> o, String x, String y) -> save.setDisable(!validate()));
		}
		setDisable(true);
	}

	@FXML
	private void save() {
		if (tag != null && validate()) {
			TagOperator.modifyTag(assembly());
			refreshListView();
		}
	}

	@FXML
	private void delete() {
		if (tag != null) {
			TagOperator.removeTag(tag);
		}
	}

	@FXML
	private void addNew() {
		new TagAdder().start(new Stage());
		stage.hide();
	}

	@FXML
	private void cancel() {
		stage.hide();
	}

	private void refreshTextField() {
		tag = null;
		if (!tabPane.getSelectionModel().isEmpty()) {
			ListView<Tag> listView = listViewMap.get(TagType.getTagType(tabPane.getSelectionModel().getSelectedIndex()));
			if (!listView.getSelectionModel().isEmpty()) {
				tag = listView.getSelectionModel().getSelectedItem();
				if (!tag.belongsToSystem()) {
					setDisable(false);
				} else {
					setDisable(true);
				}
			} else {
				setDisable(true);
			}
		} else {
			setDisable(true);
		}
		for (Entry<LanguageType, TextField> entry : textFieldMap.entrySet()) {
			if (tag != null) {
				entry.getValue().setText(tag.getName().getString(entry.getKey()));
			} else {
				entry.getValue().setText("");
			}
		}
	}
	
	private void refreshListView() {
		for (Entry<TagType, ListView<Tag>> entry : listViewMap.entrySet()) {
			entry.getValue().setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(entry.getKey())));
		}
	}
	private boolean validate() {
		boolean valid = false;
		for (TextField textField : textFieldMap.values()) {
			if (!textField.getText().equals("")) {
				valid = true;
				break;
			}
		}
		return valid;
	}

	private void setDisable(boolean b) {
		for (TextField textField : textFieldMap.values()) {
			textField.setDisable(b);
		}
		save.setDisable(b);
		delete.setDisable(b);
	}

	private Tag assembly() {
		InternationalString name = new InternationalString();
		for (Entry<LanguageType, TextField> entry : textFieldMap.entrySet()) {
			name.setString(entry.getKey(), entry.getValue().getText());
		}
		return new Tag(tag.getTagID(), name, tag.getTagType(), tag.belongsToSystem());
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}