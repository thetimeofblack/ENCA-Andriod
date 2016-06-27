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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.TagBean;

public final class TagModifierController {

	private final class ContentGroup {

		private TagType type;
		private ListView<TagBean> listView;
		private Map<LanguageType, TextField> textFieldMap = new HashMap<>();
		private Button addNew;
		private Button delete;

		public ContentGroup(TagType type, ListView<TagBean> listView, TextField english, TextField german, TextField chinese, Button addNew, Button delete) {
			this.type = type;
			this.listView = listView;
			this.textFieldMap.put(LanguageType.ENGLISH, english);
			this.textFieldMap.put(LanguageType.GERMAN, german);
			this.textFieldMap.put(LanguageType.CHINESE, chinese);
			this.addNew = addNew;
			this.delete = delete;
		}

		public void showContent() {
			listView.setItems(TagBean.generateList(TagFetcher.fetchTagsAllOfCertainType(type)));
			listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TagBean> o, TagBean oldValue, TagBean newValue) -> {
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
	private ListView<TagBean> room;
	@FXML
	private TextField english_room;
	@FXML
	private TextField german_room;
	@FXML
	private TextField chinese_room;
	@FXML
	private Button addNew_room;
	@FXML
	private Button delete_rom;

	@FXML
	private Button save;
	@FXML
	private Button cancel;

	@FXML
	private void initialize() {
		contentGroups.add(new ContentGroup(TagType.ROOM, room, english_room, german_room, chinese_room, addNew_room, delete_rom));
		for (ContentGroup contentGroup : contentGroups) {
			contentGroup.showContent();
		}
	}
}