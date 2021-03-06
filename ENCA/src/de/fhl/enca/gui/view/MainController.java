package de.fhl.enca.gui.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.TagFetcher;
import de.fhl.enca.gui.application.About;
import de.fhl.enca.gui.application.CleaningAgentDetail;
import de.fhl.enca.gui.application.CleaningAgentModifier;
import de.fhl.enca.gui.application.Manual;
import de.fhl.enca.gui.application.TagAdder;
import de.fhl.enca.gui.application.TagModifier;
import de.fhl.enca.gui.application.UserCentre;
import de.fhl.enca.gui.model.CleaningAgentBean;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.CleaningAgentModifierController.OperationType;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Controller of Main interface.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see de.fhl.enca.gui.application.Main
 */
public final class MainController {

	@FXML
	private Label room;
	@FXML
	private Label items;
	@FXML
	private Label others;

	@FXML
	private ListView<Tag> list_room;
	@FXML
	private ListView<Tag> list_items;
	@FXML
	private ListView<Tag> list_others;
	@FXML
	private Label count;
	@FXML
	private TabPane tabPane;
	@FXML
	private TableView<CleaningAgentBean> table_en;
	@FXML
	private TableView<CleaningAgentBean> table_de;
	@FXML
	private TableView<CleaningAgentBean> table_zh;
	@FXML
	private TableColumn<CleaningAgentBean, FlowPane> column_en;
	@FXML
	private TableColumn<CleaningAgentBean, FlowPane> column_de;
	@FXML
	private TableColumn<CleaningAgentBean, FlowPane> column_zh;
	@FXML
	private TextField textField;
	@FXML
	private SplitMenuButton add;

	@FXML
	private MenuItem detail;
	@FXML
	private MenuItem modifyCA;
	@FXML
	private MenuItem detail_en;
	@FXML
	private MenuItem modifyCA_en;
	@FXML
	private MenuItem detail_de;
	@FXML
	private MenuItem modifyCA_de;
	@FXML
	private MenuItem detail_zh;
	@FXML
	private MenuItem modifyCA_zh;

	private Stage stage;

	private Map<Label, TagType> labelMap = new HashMap<>();
	/**
	 * Store the three listView and their representing tagType.
	 */
	private Map<ListView<Tag>, TagType> listViewMap = new HashMap<>();

	/**
	 * Store the priority of three listView.
	 */
	private Map<ListView<Tag>, Integer> priorityMap = new HashMap<>();

	/**
	 * Current priority.
	 */
	private int priority;

	/**
	 * Store the three tableView and their representing language.
	 */
	private Map<LanguageType, TableView<CleaningAgentBean>> tableViewMap = new HashMap<>();

	/**
	 * Store the three tag columns.
	 */
	private Set<TableColumn<CleaningAgentBean, FlowPane>> columns = new HashSet<>();

	/**
	 * Store MenuItem representing showing detail action.
	 */
	private Set<MenuItem> detailSet = new HashSet<>();

	/**
	 * Store MenuItem representing modifying action.
	 */
	private Set<MenuItem> modifyCASet = new HashSet<>();

	/**
	 * Store the current cleaning agent fetch result.
	 */
	private Set<CleaningAgent> result = new HashSet<>();

	/**
	 * Return chosen tags from the three ListView.
	 * @return tags chosen
	 */
	private Set<Tag> getChosenTags() {
		Set<Tag> set = new HashSet<>();
		for (ListView<Tag> listView : listViewMap.keySet()) {
			if (!listView.getSelectionModel().isEmpty()) {
				set.add(listView.getSelectionModel().getSelectedItem());
			}
		}
		return set;
	}

	/**
	 * Return current active TableView.
	 * @return the current active TableView
	 */
	private TableView<CleaningAgentBean> getCurrentTableView() {
		if (!tabPane.getSelectionModel().isEmpty()) {
			return tableViewMap.get(LanguageType.getLanguageType((tabPane.getSelectionModel().getSelectedIndex())));
		} else {
			return null;
		}
	}

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		labelMap.put(room, TagType.ROOM);
		labelMap.put(items, TagType.ITEM);
		labelMap.put(others, TagType.OTHERS);
		listViewMap.put(list_room, TagType.ROOM);
		listViewMap.put(list_items, TagType.ITEM);
		listViewMap.put(list_others, TagType.OTHERS);
		tableViewMap.put(LanguageType.ENGLISH, table_en);
		tableViewMap.put(LanguageType.GERMAN, table_de);
		tableViewMap.put(LanguageType.CHINESE, table_zh);
		columns.add(column_en);
		columns.add(column_de);
		columns.add(column_zh);
		detailSet.add(detail);
		detailSet.add(detail_en);
		detailSet.add(detail_de);
		detailSet.add(detail_zh);
		modifyCASet.add(modifyCA);
		modifyCASet.add(modifyCA_en);
		modifyCASet.add(modifyCA_de);
		modifyCASet.add(modifyCA_zh);
		for (Entry<Label, TagType> entry : labelMap.entrySet()) {
			entry.getKey().setTextFill(User.getTagColor(entry.getValue()));
		}
		tabPane.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
		for (TableView<CleaningAgentBean> tableView : tableViewMap.values()) {
			tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("nation"));
			tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
			tableView.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("tags"));
			tableView.setOnMouseClicked(e -> {
				validate();
				if (e.getClickCount() > 1) {
					detail();
				}
			});
			tableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CleaningAgentBean> o, CleaningAgentBean x, CleaningAgentBean y) -> validate());
			tableView.itemsProperty().addListener((ObservableValue<? extends ObservableList<CleaningAgentBean>> o, ObservableList<CleaningAgentBean> x, ObservableList<CleaningAgentBean> y) -> validate());
		}
		for (TableColumn<CleaningAgentBean, FlowPane> column : columns) {
			column.setCellFactory(e -> new TableCell<CleaningAgentBean, FlowPane>() {

				@Override
				protected void updateItem(FlowPane item, boolean empty) {
					if (empty) {
						setText(null);
						setGraphic(null);
					} else {
						setGraphic(item);
					}
				}
			});
		}
		for (ListView<Tag> listView1 : listViewMap.keySet()) {
			listView1.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag oldValue, Tag newValue) -> {
				if (newValue != null) {
					if (!priorityMap.containsKey(listView1)) {
						priorityMap.put(listView1, ++priority);
					}
					for (ListView<Tag> listView2 : listViewMap.keySet()) {
						if (priorityMap.containsKey(listView2)) {
							if (priorityMap.get(listView2) > priorityMap.get(listView1)) {
								listView2.getSelectionModel().clearSelection();
								initListView(listView2);
							}
						} else {
							initListView(listView2);
						}
					}
					initTableViews(CleaningAgentFetcher.fetchCleaningAgentsOfTags(getChosenTags()));
				}
			});
		}
		textField.textProperty().addListener((ObservableValue<? extends String> o, String oldValue, String newValue) -> {
			if (newValue.equals("")) {
				initTableViews(CleaningAgent.getCleaningAgentsAll());
			} else {
				search();
			}
		});
		initMain();
	}

	/**
	 * Initialize all of the content.
	 */
	@FXML
	private void initMain() {
		priorityMap.clear();
		priority = 0;
		for (ListView<Tag> listView : listViewMap.keySet()) {
			listView.getSelectionModel().clearSelection();
		}
		for (ListView<Tag> listView : listViewMap.keySet()) {
			initListView(listView);
		}
		initTableViews(CleaningAgent.getCleaningAgentsAll());
		for (TableView<CleaningAgentBean> tableView : tableViewMap.values()) {
			tableView.getSelectionModel().clearSelection();
		}
		textField.clear();
	}

	/**
	 * Search cleaning agents according to current chosen tags and keywords.
	 */
	@FXML
	private void search() {
		if (!textField.getText().equals("")) {
			if (getChosenTags().isEmpty()) {
				initTableViews(CleaningAgentFetcher.fetchResult(CleaningAgent.getCleaningAgentsAll(), textField.getText()));
			} else {
				initTableViews(CleaningAgentFetcher.fetchResult(result, textField.getText()));
			}
		}
	}

	/**
	 * Initialize or refresh a certain listView.
	 */
	private void initListView(ListView<Tag> listView) {
		if (getChosenTags().isEmpty()) {
			listView.setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(listViewMap.get(listView))));
			if (listView == list_others) {
				if (!Tag.getTag(0).getCleaningAgents().isEmpty()) {
					listView.getItems().add(Tag.getTag(0));
				}
			}
		} else {
			listView.setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsOfCertainType(TagFetcher.fetchTagsRelated(getChosenTags()), listViewMap.get(listView))));
		}
	}

	/**
	 * initialize the tableView according to the source.
	 */
	private void initTableViews(Set<CleaningAgent> source) {
		result = source;
		count.setText(result.size() + " " + Utility.getResourceBundle().getString("results"));
		for (Entry<LanguageType, TableView<CleaningAgentBean>> entry : tableViewMap.entrySet()) {
			entry.getValue().setItems(CleaningAgentBean.generateList(source, entry.getKey()));
		}
	}

	/**
	 * Check if a cleaning agent is chosen in the tables.
	 */
	private boolean validateDetail() {
		return getCurrentTableView() != null && !getCurrentTableView().getSelectionModel().isEmpty();
	}

	/**
	 * Check if the selected cleaning agent can be modified.
	 */
	private boolean validateModify() {
		if (validateDetail()) {
			if (!User.getPriority()) {
				try {
					return !CleaningAgent.getCleaningAgent(getCurrentTableView().getSelectionModel().getSelectedItem().getId()).BelongsToSystem();
				} catch (NullPointerException e) {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	/**
	 * Controls whether menu items are disable.
	 */
	@FXML
	private void validate() {
		for (MenuItem menuItem : detailSet) {
			menuItem.setDisable(!validateDetail());
		}
		for (MenuItem menuItem : modifyCASet) {
			menuItem.setDisable(!validateModify());
		}
	}

	/**
	 * Jump to the UserCentre interface.
	 */
	@FXML
	private void userCentre() {
		new UserCentre(stage, false).start(new Stage());
	}

	/**
	 * Jump to the UserCentre interface and go to memo.
	 */
	@FXML
	private void userCentreToMemo() {
		new UserCentre(stage, true).start(new Stage());
	}

	/**
	 * Show the detail of the chosen cleaning agent.
	 */
	@FXML
	private void detail() {
		if (validateDetail()) {
			new CleaningAgentDetail(CleaningAgent.getCleaningAgent(getCurrentTableView().getSelectionModel().getSelectedItem().getId())).start(new Stage());
		}
	}

	/**
	 * Modify the chosen cleaning agent.
	 */
	@FXML
	private void modifyCA() {
		if (validateModify()) {
			new CleaningAgentModifier(OperationType.MODIFY, CleaningAgent.getCleaningAgent(getCurrentTableView().getSelectionModel().getSelectedItem().getId())).start(new Stage());
		}
	}

	/**
	 * Show the add menu.
	 */
	@FXML
	private void add() {
		add.show();
	}

	/**
	 * Add a cleaning agent.
	 */
	@FXML
	private void addCleaningAgent() {
		new CleaningAgentModifier(OperationType.ADD, null).start(new Stage());
	}

	/**
	 * Add a tag.
	 */
	@FXML
	private void addTag() {
		new TagAdder(null).start(new Stage());
	}

	/**
	 * Jump to TagModifier interface.
	 */
	@FXML
	private void modifyTag() {
		new TagModifier(null).start(new Stage());
	}

	/**
	 * Jump to TagModifier interface and choose a specific tag.
	 */
	@FXML
	private void modifySpecificTag() {
		for (ListView<Tag> listView : listViewMap.keySet()) {
			if (listView.isFocused()) {
				if (!listView.getSelectionModel().isEmpty()) {
					Tag currentTag = listView.getSelectionModel().getSelectedItem();
					if (currentTag.getTagID() != 0) {
						new TagModifier(currentTag).start(new Stage());
						return;
					}
				}
			}
		}
		modifyTag();
	}

	/**
	 * Exit the process.
	 */
	@FXML
	private void exit() {
		System.exit(0);
	}

	/**
	 * Show about.
	 */
	@FXML
	private void about() {
		new About().start(new Stage());
	}

	/**
	 * Show manual.
	 */
	@FXML
	private void manual() {
		new Manual().start(new Stage());
	}

	public void setMainStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Refresh the content on the Main interface.
	 */
	public void refreshMain() {
		initMain();
	}
}