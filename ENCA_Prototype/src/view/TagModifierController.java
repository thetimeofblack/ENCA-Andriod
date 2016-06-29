package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public final class TagModifierController {

	private final class ContentGroup {

		private TagType type;
		private ListView<Tag> listView;
		private Map<LanguageType, TextField> textFieldMap = new HashMap<>();
		private Button save;
		private Button delete;

		public ContentGroup(TagType type, ListView<Tag> listView, TextField english, TextField german, TextField chinese, Button save, Button delete) {
			this.type = type;
			this.listView = listView;
			this.textFieldMap.put(LanguageType.ENGLISH, english);
			this.textFieldMap.put(LanguageType.GERMAN, german);
			this.textFieldMap.put(LanguageType.CHINESE, chinese);
			this.save = save;
			this.delete = delete;
		}

		public void showContent() {
			listView.setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(type)));
			listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag oldValue, Tag newValue) -> {
				if (newValue != null) {
					Tag tag = Tag.getTag(newValue.getTagID());
					for (Map.Entry<LanguageType, TextField> entry : textFieldMap.entrySet()) {
						entry.getValue().setText(tag.getName().getString(entry.getKey()));
						entry.getValue().setDisable(tag.belongsToSystem());
						entry.getValue().setEditable(!tag.belongsToSystem());
						delete.setDisable(tag.belongsToSystem());
						save.setDisable(tag.belongsToSystem());
					}
				}
			});
		}
	}

	private static Set<ContentGroup> contentGroups = new HashSet<>();

	@FXML
	private ListView<Tag> room;
	@FXML
	private TextField english_room;
	@FXML
	private TextField german_room;
	@FXML
	private TextField chinese_room;
	@FXML
	private Button save_room;
	@FXML
	private Button delete_room;
	@FXML
	private ListView<Tag> item;
	@FXML
	private TextField english_item;
	@FXML
	private TextField german_item;
	@FXML
	private TextField chinese_item;
	@FXML
	private Button save_item;
	@FXML
	private Button delete_item;
	@FXML
	private ListView<Tag> others;
	@FXML
	private TextField english_others;
	@FXML
	private TextField german_others;
	@FXML
	private TextField chinese_others;
	@FXML
	private Button save_others;
	@FXML
	private Button delete_others;

	@FXML
	private Button addNew;
	@FXML
	private Button cancel;

	@FXML
	private void initialize() {
		contentGroups.add(new ContentGroup(TagType.ROOM, room, english_room, german_room, chinese_room, save_room, delete_room));
		contentGroups.add(new ContentGroup(TagType.ITEM, item, english_item, german_item, chinese_item, save_item, delete_item));
		contentGroups.add(new ContentGroup(TagType.OTHERS, others, english_others, german_others, chinese_others, save_others, delete_others));
		for (ContentGroup contentGroup : contentGroups) {
			contentGroup.showContent();
		}
	}
}