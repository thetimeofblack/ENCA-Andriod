package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	private Button clearButton;

	private Map<ListView<TagBean>, TagType> listViewMap = new HashMap<ListView<TagBean>, TagType>();

	private Map<ListView<TagBean>, Integer> priorityMap = new HashMap<>();

	private int priority;

	private Set<TagBean> getChosenTags() {
		Set<TagBean> set = new HashSet<>();
		for (ListView<TagBean> listView : listViewMap.keySet()) {
			if (!listView.getSelectionModel().isEmpty()) {
				set.add(listView.getSelectionModel().getSelectedItem());
			}
		}
		return set;
	}

	@FXML
	private void initialize() {
		listViewMap.put(roomTagListView, TagType.ROOM);
		listViewMap.put(itemTagListView, TagType.ITEM);
		listViewMap.put(otherTaglistView, TagType.OTHERS);
		for (ListView<TagBean> listView : listViewMap.keySet()) {
			listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TagBean>() {

				@Override
				public void changed(ObservableValue<? extends TagBean> observable, TagBean oldValue, TagBean newValue) {
					if (newValue != null) {
						if (!priorityMap.containsKey(listView)) {
							priorityMap.put(listView, ++priority);
						}
						for (ListView<TagBean> listView2 : listViewMap.keySet()) {
							if (priorityMap.containsKey(listView2)) {
								if (priorityMap.get(listView2) > priorityMap.get(listView)) {
									listView2.getSelectionModel().clearSelection();
									init(listView2);
								}
							} else {
								init(listView2);
							}
						}
					}
				}
			});
		}
		clear();
	}

	private void init(ListView<TagBean> listView) {
		if (getChosenTags().isEmpty()) {
			listView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfType(listViewMap.get(listView))));
		} else {
			if (listView.getSelectionModel().isEmpty()) {
				listView.setItems(TagBean.generateList(TagFetcher.fetchTagOfTypeOfTags(TagBean.convert(getChosenTags()), listViewMap.get(listView))));
			}
		}
	}

	@FXML
	private void clear() {
		for (ListView<TagBean> listView : listViewMap.keySet()) {
			listView.getSelectionModel().clearSelection();
		}
		priorityMap.clear();
		priority = 0;
		for (Map.Entry<ListView<TagBean>, TagType> entry : listViewMap.entrySet()) {
			init(entry.getKey());
		}
	}
}