package de.fhl.enca.gui.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.TagOperator;
import de.fhl.enca.gui.utility.Refreshable;
import de.fhl.enca.gui.utility.Utility;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller of TagAdder interface.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class TagAdderController {

	private Stage stage;
	private Refreshable refreshable;
	private Map<LanguageType, TextField> textFieldMap = new HashMap<>();

	@FXML
	private ComboBox<String> tagType;
	@FXML
	private TextField english;
	@FXML
	private TextField german;
	@FXML
	private TextField chinese;

	@FXML
	private Button save;
	@FXML
	private Button cancel;

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		ResourceBundle resourceBundle = Utility.getResourceBundle();
		ObservableList<String> list = FXCollections.observableArrayList();
		for (TagType type : TagType.values()) {
			list.add(resourceBundle.getString(type.toString().toLowerCase()));
		}
		tagType.setItems(list);
		tagType.getSelectionModel().clearAndSelect(2);
		textFieldMap.put(LanguageType.ENGLISH, english);
		textFieldMap.put(LanguageType.GERMAN, german);
		textFieldMap.put(LanguageType.CHINESE, chinese);
		for (TextField textField : textFieldMap.values()) {
			textField.textProperty().addListener((ObservableValue<? extends String> o, String x, String y) -> save.setDisable(!validate()));
		}
	}

	/**
	 * Do boundary check and save the tag.
	 */
	@FXML
	private void save() {
		if (validate()) {
			if (checkName()) {
				if (Utility.showAddTagAlert()) {
					reallySave();
				}
			} else {
				reallySave();
			}
		}
	}

	/**
	 * Save the tag.
	 */
	private void reallySave() {
		TagOperator.createTag(assembly());
		Utility.refreshMain();
		stage.hide();
		if (refreshable != null) {
			refreshable.refresh();
		}
	}

	/**
	 * Check if tag with then same name exists.
	 */
	private boolean checkName() {
		for (Tag tag : Tag.getTagsAll()) {
			for (Entry<LanguageType, TextField> entry : textFieldMap.entrySet()) {
				if (tag.getName().getString(entry.getKey()).equals(entry.getValue().getText())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Dispose the interface.
	 */
	@FXML
	private void cancel() {
		stage.hide();
	}

	/**
	 * Check if all the names are null.
	 */
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

	/**
	 * Assembly the content on the interface and generate a new tag.
	 */
	private Tag assembly() {
		int id = Tag.getMaxID() + 1;
		Tag.setMaxID(id);
		InternationalString name = new InternationalString();
		for (Entry<LanguageType, TextField> entry : textFieldMap.entrySet()) {
			name.setString(entry.getKey(), entry.getValue().getText());
		}
		return new Tag(id, name, TagType.getTagType(tagType.getSelectionModel().getSelectedIndex()), false);
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Set the interface which calls this tag adder interface.
	 */
	public void setRefreshable(Refreshable refreshable) {
		this.refreshable = refreshable;
	}
}