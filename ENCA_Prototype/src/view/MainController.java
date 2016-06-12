package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Search;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.CleaningAgentBean;
import model.TagBean;

public final class MainController {

	@FXML
	private ListView<TagBean> roomTagListView;
	@FXML
	private ListView<TagBean> itemTagListView;
	@FXML
	private ListView<TagBean> otherTaglistView;
	@FXML
	private TableView<CleaningAgentBean> englishTableView;
	@FXML
	private TableView<CleaningAgentBean> germanTableView;
	@FXML
	private TableView<CleaningAgentBean> chineseTableView;
	@FXML
	private TableColumn<CleaningAgentBean, Set<Button>> englishTagsColumn;
	@FXML
	private TableColumn<CleaningAgentBean, Set<Button>> germanTagsColumn;
	@FXML
	private TableColumn<CleaningAgentBean, Set<Button>> chineseTagsColumn;
	@FXML
	private TextField textField;

	private Map<ListView<TagBean>, TagType> listViewMap = new HashMap<>();
	private Map<ListView<TagBean>, Integer> priorityMap = new HashMap<>();
	private int priority;

	private Map<TableView<CleaningAgentBean>, LanguageType> tableViewMap = new HashMap<>();
	private Set<TableColumn<CleaningAgentBean, Set<Button>>> columnList = new HashSet<>();
	private Set<CleaningAgent> result = new HashSet<>();

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
		tableViewMap.put(englishTableView, LanguageType.ENGLISH);
		tableViewMap.put(germanTableView, LanguageType.GERMAN);
		tableViewMap.put(chineseTableView, LanguageType.CHINESE);
		columnList.add(englishTagsColumn);
		columnList.add(germanTagsColumn);
		columnList.add(chineseTagsColumn);
		for (TableView<CleaningAgentBean> tableView : tableViewMap.keySet()) {
			tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
			tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("tags"));
		}
		for (TableColumn<CleaningAgentBean, Set<Button>> column : columnList) {
			column.setCellFactory(e -> new TableCell<CleaningAgentBean, Set<Button>>() {

				@Override
				protected void updateItem(Set<Button> item, boolean empty) {
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						HBox hBox = new HBox();
						for (Button button : item) {
							hBox.getChildren().add(button);
						}
						setGraphic(hBox);
					}
				}
			});
		}
		for (ListView<TagBean> listView : listViewMap.keySet()) {
			listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TagBean> e, TagBean oldValue, TagBean newValue) -> {
				if (newValue != null) {
					if (!priorityMap.containsKey(listView)) {
						priorityMap.put(listView, ++priority);
					}
					for (ListView<TagBean> listView2 : listViewMap.keySet()) {
						if (priorityMap.containsKey(listView2)) {
							if (priorityMap.get(listView2) > priorityMap.get(listView)) {
								listView2.getSelectionModel().clearSelection();
								initListView(listView2);
							}
						} else {
							initListView(listView2);
						}
					}
					initTableViews(CleaningAgentFetcher.fetchCleaningAgentsOfTypes(TagBean.convert(getChosenTags())));
				}
			});
		}

		initMain();
		initTableViews(CleaningAgentFetcher.fetchCleaningAgentsAll());
	}

	@FXML
	private void initMain() {
		priorityMap.clear();
		priority = 0;
		for (ListView<TagBean> listView : listViewMap.keySet()) {
			listView.getSelectionModel().clearSelection();
		}
		for (ListView<TagBean> listView : listViewMap.keySet()) {
			initListView(listView);
		}
		initTableViews(CleaningAgentFetcher.fetchCleaningAgentsAll());
	}

	@FXML
	private void search() {
		if (!textField.getText().equals("")) {
			if (getChosenTags().isEmpty()) {
				initTableViews(Search.search(CleaningAgentFetcher.fetchCleaningAgentsAll(), textField.getText()));
			} else {
				initTableViews(Search.search(result, textField.getText()));
			}
		}
	}

	private void initListView(ListView<TagBean> listView) {
		if (getChosenTags().isEmpty()) {
			listView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfType(listViewMap.get(listView))));
		} else {
			if (listView.getSelectionModel().isEmpty()) {
				listView.setItems(TagBean.generateList(TagFetcher.fetchTagOfTypeOfTags(TagBean.convert(getChosenTags()), listViewMap.get(listView))));
			}
		}
	}

	private void initTableViews(Set<CleaningAgent> source) {
		result = source;
		for (Map.Entry<TableView<CleaningAgentBean>, LanguageType> entry : tableViewMap.entrySet()) {
			entry.getKey().setItems(CleaningAgentBean.generateList(source, entry.getValue()));
		}
	}
}