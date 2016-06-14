package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.Search;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
	private TableColumn<CleaningAgentBean, HBox> englishTagsColumn;
	@FXML
	private TableColumn<CleaningAgentBean, HBox> germanTagsColumn;
	@FXML
	private TableColumn<CleaningAgentBean, HBox> chineseTagsColumn;
	@FXML
	private TextField textField;

	/**
	 * Store the three listView and their representing tagType
	 */
	private Map<ListView<TagBean>, TagType> listViewMap = new HashMap<>();

	/**
	 * Store the priority of three listView
	 */
	private Map<ListView<TagBean>, Integer> priorityMap = new HashMap<>();

	/**
	 * Current priority
	 */
	private int priority;

	/**
	 * Store the three tableView and their representing language
	 */
	private Map<TableView<CleaningAgentBean>, LanguageType> tableViewMap = new HashMap<>();

	/**
	 * Store the three tag columns
	 */
	private Set<TableColumn<CleaningAgentBean, HBox>> columnList = new HashSet<>();

	/**
	 * Store the current cleaning agent fetch result
	 */
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
		/* Assign the two columns of the tableView */
		for (TableView<CleaningAgentBean> tableView : tableViewMap.keySet()) {
			tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
			tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("tags"));
		}
		/* Put those tag labels into the table cell */
		for (TableColumn<CleaningAgentBean, HBox> column : columnList) {
			column.setCellFactory(e -> new TableCell<CleaningAgentBean, HBox>() {

				@Override
				protected void updateItem(HBox item, boolean empty) {
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						setGraphic(item);
					}
				}
			});
		}
		/* Assign action when the selection of the listView is changed */
		for (ListView<TagBean> listView1 : listViewMap.keySet()) {
			listView1.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TagBean> e, TagBean oldValue, TagBean newValue) -> {
				/* Ensure the action is performed by user */
				if (newValue != null) {
					/* Assign priority for each listView according to user's action */
					if (!priorityMap.containsKey(listView1)) {
						priorityMap.put(listView1, ++priority);
					}
					/* Refresh each listView according to the priority */
					for (ListView<TagBean> listView2 : listViewMap.keySet()) {
						if (priorityMap.containsKey(listView2)) {
							/* Refresh those listViews whose priority is larger than that of current listView */
							if (priorityMap.get(listView2) > priorityMap.get(listView1)) {
								listView2.getSelectionModel().clearSelection();
								initListView(listView2);
							}
						} else {
							/* Refresh those listViews who doesn't have a priority */
							initListView(listView2);
						}
					}
					/* Refresh the tableView according to the change of listview */
					initTableViews(CleaningAgentFetcher.fetchCleaningAgentsOfTypes(TagBean.convert(getChosenTags())));
				}
			});
		}
		/* Add auto-search function for the textField */
		textField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
			if (newValue.equals("")) {
				initTableViews(CleaningAgent.getCleaningAgentsAll());
			} else {
				search();
			}
		});
		initMain();
	}

	/**
	 * Initialize all of the content
	 */
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
		initTableViews(CleaningAgent.getCleaningAgentsAll());
	}

	/**
	 * Search cleaning agents according to current chosen tags and keywords
	 */
	@FXML
	private void search() {
		if (!textField.getText().equals("")) {
			if (getChosenTags().isEmpty()) {
				initTableViews(Search.search(CleaningAgent.getCleaningAgentsAll(), textField.getText()));
			} else {
				initTableViews(Search.search(result, textField.getText()));
			}
		}
	}

	/**
	 * Initialize or refresh a certain listView
	 */
	private void initListView(ListView<TagBean> listView) {
		if (getChosenTags().isEmpty()) {
			/* If no tag has been chosen, fetch all tags of the tagType of the listView */
			listView.setItems(TagBean.generateList(TagFetcher.fetchTagsOfType(listViewMap.get(listView))));
		} else {
			/* If some tags have been chosen, fetch tags according to the chosen tags */
			listView.setItems(TagBean.generateList(TagFetcher.fetchTagOfTypeOfTags(TagBean.convert(getChosenTags()), listViewMap.get(listView))));
		}
	}

	/**
	 * initialize the tableView according to the source
	 */
	private void initTableViews(Set<CleaningAgent> source) {
		result = source;
		for (Map.Entry<TableView<CleaningAgentBean>, LanguageType> entry : tableViewMap.entrySet()) {
			entry.getKey().setItems(CleaningAgentBean.generateList(source, entry.getValue()));
		}
	}
}