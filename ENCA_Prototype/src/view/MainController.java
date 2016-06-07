package view;

import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.TagBean;

public final class MainController {

	@FXML
	private ListView<TagBean> roomTagListView;

	@FXML
	private ListView<TagBean> itemTagListView;

	@FXML
	private ListView<TagBean> otherTaglistView;

	@FXML
	private Button roomClearButton;

	@FXML
	private Button itemClearButton;

	@FXML
	private Button otherClearButton;

	@FXML
	private void initialize() {
		roomTagListView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfType(TagType.ROOM)));
		itemTagListView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfType(TagType.ITEM)));
		ObservableList<TagBean> list = TagBean.generateList(TagFetcher.fetchTagsOfType(TagType.OTHERS));
		if (!list.isEmpty()) {
			otherTaglistView.setItems(list);
		}
		roomTagListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TagBean>() {

			@Override
			public void changed(ObservableValue<? extends TagBean> observable, TagBean oldValue, TagBean newValue) {
				if (!roomTagListView.getSelectionModel().isEmpty()) {
					itemTagListView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfTypeOfTag(Tag.getTag(newValue.getTagID()), TagType.ITEM)));
				} else {
					itemTagListView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfType(TagType.ITEM)));
				}
			}
		});
	}

	@FXML
	private void clearRoom() {
		roomTagListView.getSelectionModel().clearSelection();
	}

	@FXML
	private void clearItem() {
		itemTagListView.getSelectionModel().clearSelection();
	}
}