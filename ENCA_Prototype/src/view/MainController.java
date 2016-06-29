package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import application.CleaningAgentDetail;
import application.CleaningAgentModifier;
import application.TagAdder;
import application.TagModifier;
import application.UserCentre;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
import model.CleaningAgentBean;
import view.CleaningAgentModifierController.OperationType;

public final class MainController {

	@FXML
	private ListView<Tag> roomTagListView;
	@FXML
	private ListView<Tag> itemTagListView;
	@FXML
	private ListView<Tag> otherTaglistView;
	@FXML
	private TabPane tabPane;
	@FXML
	private TableView<CleaningAgentBean> englishTableView;
	@FXML
	private TableView<CleaningAgentBean> germanTableView;
	@FXML
	private TableView<CleaningAgentBean> chineseTableView;
	@FXML
	private TableColumn<CleaningAgentBean, FlowPane> englishTagsColumn;
	@FXML
	private TableColumn<CleaningAgentBean, FlowPane> germanTagsColumn;
	@FXML
	private TableColumn<CleaningAgentBean, FlowPane> chineseTagsColumn;
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

	private Stage mainStage;

	/**
	 * Store the three listView and their representing tagType
	 */
	private Map<ListView<Tag>, TagType> listViewMap = new HashMap<>();

	/**
	 * Store the priority of three listView
	 */
	private Map<ListView<Tag>, Integer> priorityMap = new HashMap<>();

	/**
	 * Current priority
	 */
	private int priority;

	/**
	 * Store the three tableView and their representing language
	 */
	private Map<LanguageType, TableView<CleaningAgentBean>> tableViewMap = new HashMap<>();

	/**
	 * Store the three tag columns
	 */
	private Set<TableColumn<CleaningAgentBean, FlowPane>> columnList = new HashSet<>();

	private Set<MenuItem> detailSet = new HashSet<>();
	private Set<MenuItem> modifyCASet = new HashSet<>();

	/**
	 * Store the current cleaning agent fetch result
	 */
	private Set<CleaningAgent> result = new HashSet<>();

	private Set<Tag> getChosenTags() {
		Set<Tag> set = new HashSet<>();
		for (ListView<Tag> listView : listViewMap.keySet()) {
			if (!listView.getSelectionModel().isEmpty()) {
				set.add(listView.getSelectionModel().getSelectedItem());
			}
		}
		return set;
	}

	private TableView<CleaningAgentBean> getCurrentTableView() {
		if (!tabPane.getSelectionModel().isEmpty()) {
			return tableViewMap.get(LanguageType.getLanguageType((tabPane.getSelectionModel().getSelectedIndex())));
		} else {
			return null;
		}
	}

	@FXML
	private void initialize() {
		listViewMap.put(roomTagListView, TagType.ROOM);
		listViewMap.put(itemTagListView, TagType.ITEM);
		listViewMap.put(otherTaglistView, TagType.OTHERS);
		tableViewMap.put(LanguageType.ENGLISH, englishTableView);
		tableViewMap.put(LanguageType.GERMAN, germanTableView);
		tableViewMap.put(LanguageType.CHINESE, chineseTableView);
		columnList.add(englishTagsColumn);
		columnList.add(germanTagsColumn);
		columnList.add(chineseTagsColumn);
		detailSet.add(detail);
		detailSet.add(detail_en);
		detailSet.add(detail_de);
		detailSet.add(detail_zh);
		modifyCASet.add(modifyCA);
		modifyCASet.add(modifyCA_en);
		modifyCASet.add(modifyCA_de);
		modifyCASet.add(modifyCA_zh);
		tabPane.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
		/* assign the two columns of the tableView */
		for (TableView<CleaningAgentBean> tableView : tableViewMap.values()) {
			tableView.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
			tableView.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("tags"));
			tableView.setOnMouseClicked(e -> {
				validate();
				if (e.getClickCount() > 1) {
					detail();
				}
			});
		}
		/* put those tag labels into the table cell */
		for (TableColumn<CleaningAgentBean, FlowPane> column : columnList) {
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
		/* assign action when the selection of the listView is changed */
		for (ListView<Tag> listView1 : listViewMap.keySet()) {
			listView1.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag oldValue, Tag newValue) -> {
				/* ensure the action is performed by user */
				if (newValue != null) {
					/* assign priority for each listView according to user's action */
					if (!priorityMap.containsKey(listView1)) {
						priorityMap.put(listView1, ++priority);
					}
					/* refresh each listView according to the priority */
					for (ListView<Tag> listView2 : listViewMap.keySet()) {
						if (priorityMap.containsKey(listView2)) {
							/* refresh those listViews whose priority is larger than that of current listView */
							if (priorityMap.get(listView2) > priorityMap.get(listView1)) {
								listView2.getSelectionModel().clearSelection();
								initListView(listView2);
							}
						} else {
							/* refresh those listViews who doesn't have a priority */
							initListView(listView2);
						}
					}
					/* refresh the tableView according to the change of listview */
					initTableViews(CleaningAgentFetcher.fetchCleaningAgentsOfTags(getChosenTags()));
				}
			});
		}
		/* add auto-search function for the textField */
		textField.textProperty().addListener((ObservableValue<? extends String> o, String oldValue, String newValue) -> {
			if (newValue.equals("")) {
				initTableViews(CleaningAgent.getCleaningAgentsAll());
			} else {
				search();
			}
		});
		tabPane.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> o, Number x, Number y) -> initTableViews(result));
		initMain();
	}

	/**
	 * Initialize all of the content
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
		textField.clear();
		validate();
	}

	/**
	 * Search cleaning agents according to current chosen tags and keywords
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
	 * Initialize or refresh a certain listView
	 */
	private void initListView(ListView<Tag> listView) {
		if (getChosenTags().isEmpty()) {
			/* If no tag has been chosen, fetch all tags of the tagType of the listView */
			listView.setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(listViewMap.get(listView))));
			if (listView == otherTaglistView) {
				if (!Tag.getTag(0).getCleaningAgents().isEmpty()) {
					listView.getItems().add(Tag.getTag(0));
				}
			}
		} else {
			/* If some tags have been chosen, fetch tags according to the chosen tags */
			listView.setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsOfCertainType(TagFetcher.fetchTagsRelated(getChosenTags()), listViewMap.get(listView))));
		}
	}

	/**
	 * initialize the tableView according to the source
	 */
	private void initTableViews(Set<CleaningAgent> source) {
		result = source;
		getCurrentTableView().setItems(CleaningAgentBean.generateList(source, LanguageType.getLanguageType((tabPane.getSelectionModel().getSelectedIndex()))));
	}

	private boolean validateDetail() {
		return getCurrentTableView() != null && !getCurrentTableView().getSelectionModel().isEmpty();
	}

	private boolean validateModify() {
		return validateDetail() && !CleaningAgent.getCleaningAgent(getCurrentTableView().getSelectionModel().getSelectedItem().getId()).BelongsToSystem();
	}

	@FXML
	private void validate() {
		for (MenuItem menuItem : detailSet) {
			menuItem.setDisable(!validateDetail());
		}
		for (MenuItem menuItem : modifyCASet) {
			menuItem.setDisable(!validateModify());
		}
	}

	@FXML
	private void userCentre() {
		new UserCentre(mainStage).start(new Stage());
	}

	@FXML
	private void detail() {
		if (validateDetail()) {
			new CleaningAgentDetail(CleaningAgent.getCleaningAgent(getCurrentTableView().getSelectionModel().getSelectedItem().getId())).start(new Stage());
		}
	}

	@FXML
	private void modifyCA() {
		if (validateModify()) {
			new CleaningAgentModifier(OperationType.MODIFY, CleaningAgent.getCleaningAgent(getCurrentTableView().getSelectionModel().getSelectedItem().getId())).start(new Stage());
		}
	}

	@FXML
	private void add() {
		add.show();
	}

	@FXML
	private void addCleaningAgent() {
		new CleaningAgentModifier(OperationType.ADD, null).start(new Stage());
	}

	@FXML
	private void addTag() {
		new TagAdder().start(new Stage());
	}

	@FXML
	private void modifyTag() {
		new TagModifier().start(new Stage());
	}

	@FXML
	private void exit() {
		System.exit(0);
	}

	public void setMainStage(Stage stage) {
		this.mainStage = stage;
	}
	
	public void refreshMain() {
		initMain();
	}
}