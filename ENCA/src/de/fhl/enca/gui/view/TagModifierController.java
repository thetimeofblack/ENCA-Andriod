package de.fhl.enca.gui.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.TagFetcher;
import de.fhl.enca.controller.TagOperator;
import de.fhl.enca.gui.application.TagAdder;
import de.fhl.enca.gui.utility.Refreshable;
import de.fhl.enca.gui.utility.Utility;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controller of TagModifier interface.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class TagModifierController implements Refreshable {

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
	private Label tip;

	@FXML
	private Button save;
	@FXML
	private Button delete;
	@FXML
	private Button addNew;
	@FXML
	private Button cancel;

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		listViewMap.put(TagType.ROOM, room);
		listViewMap.put(TagType.ITEM, item);
		listViewMap.put(TagType.OTHERS, others);
		textFieldMap.put(LanguageType.ENGLISH, english);
		textFieldMap.put(LanguageType.GERMAN, german);
		textFieldMap.put(LanguageType.CHINESE, chinese);
		tabPane.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> o, Number x, Number y) -> refreshTextField());
		refresh();
		for (Entry<TagType, ListView<Tag>> entry : listViewMap.entrySet()) {
			entry.getValue().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag x, Tag y) -> refreshTextField());
		}
		for (TextField textField : textFieldMap.values()) {
			textField.textProperty().addListener((ObservableValue<? extends String> o, String x, String y) -> save.setDisable(textField.isDisable() || !validate()));
		}
		setDisable(true);
	}

	@FXML
	private void save() {
		if (tag != null && validate()) {
			TagOperator.modifyTag(assembly());
			refresh();
			Utility.refreshMain();
		}
	}

	@FXML
	private void delete() {
		if (tag != null) {
			if (Utility.showDeleteTagAlert()) {
				TagOperator.removeTag(tag);
				refresh();
				Utility.refreshMain();
			}
		}
	}

	@FXML
	private void addNew() {
		new TagAdder(this).start(new Stage());
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
				if (!User.getPriority()) {
					setDisable(tag.belongsToSystem());
					if (tag.belongsToSystem()) {
						tip.setText(Utility.getResourceBundle().getString("tagPriority"));
					} else {
						tip.setText(null);
					}
				} else {
					setDisable(false);
					if (tag.belongsToSystem()) {
						tip.setText(Utility.getResourceBundle().getString("tagGod"));
					} else {
						tip.setText(null);
					}
				}
			} else {
				setDisable(true);
				tip.setText(null);
			}
		} else {
			setDisable(true);
			tip.setText(null);
		}
		for (Entry<LanguageType, TextField> entry : textFieldMap.entrySet()) {
			if (tag != null) {
				entry.getValue().setText(tag.getName().getString(entry.getKey()));
			} else {
				entry.getValue().setText("");
			}
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

	public void initializeContent(Tag tag) {
		tabPane.getSelectionModel().clearAndSelect(tag.getTagType().getId());
		listViewMap.get(tag.getTagType()).getSelectionModel().select(tag);
	}

	@Override
	public void refresh() {
		for (Entry<TagType, ListView<Tag>> entry : listViewMap.entrySet()) {
			entry.getValue().setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(entry.getKey())));
		}
	}
}