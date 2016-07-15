package de.fhl.enca.gui.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.CleaningAgentOperator;
import de.fhl.enca.gui.application.CleaningAgentModifier;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.CleaningAgentModifierController.OperationType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Controller of CleaningAgentDetail interface.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class CleaningAgentDetailController {

	/**
	 * Representing a group of controls showing content.
	 * @author Zhaowen.Gong
	 * @version 30.06.2016
	 */
	private final class ContentGroup {

		private LanguageType type;
		private Label name;
		private FlowPane tags;
		private Label description;
		private Label instruction;

		public ContentGroup(LanguageType type, Label name, FlowPane tags, Label description, Label instruction) {
			this.type = type;
			this.name = name;
			this.tags = tags;
			this.description = description;
			this.instruction = instruction;
		}

		public void showContent() {
			name.setText(cleaningAgent.getName().getString(type));
			for (Tag tag : cleaningAgent.getTags()) {
				tags.getChildren().add(Utility.getTagLabel(tag, type));
			}
			description.setText(cleaningAgent.getDescription().getString(type));
			instruction.setText(cleaningAgent.getInstruction().getString(type));

		}
	}

	private static Map<Integer, String> rateMap = new HashMap<>();
	static {
		rateMap.put(1, "☆");
		rateMap.put(2, "★");
		rateMap.put(3, "★☆");
		rateMap.put(4, "★★");
		rateMap.put(5, "★★☆");
		rateMap.put(6, "★★★");
		rateMap.put(7, "★★★☆");
		rateMap.put(8, "★★★★");
		rateMap.put(9, "★★★★☆");
		rateMap.put(10, "★★★★★");
	}

	private CleaningAgent cleaningAgent;
	private Set<ContentGroup> contentGroups = new HashSet<>();

	private Stage stage;

	@FXML
	private TabPane tabPane;
	@FXML
	private Label name_en;
	@FXML
	private FlowPane tags_en;
	@FXML
	private Label description_en;
	@FXML
	private Label instruction_en;
	@FXML
	private Label name_de;
	@FXML
	private FlowPane tags_de;
	@FXML
	private Label description_de;
	@FXML
	private Label instruction_de;
	@FXML
	private Label name_zh;
	@FXML
	private FlowPane tags_zh;
	@FXML
	private Label description_zh;
	@FXML
	private Label instruction_zh;

	@FXML
	private ImageView imageView;
	@FXML
	private Label applicationTime;
	@FXML
	private Label frequency;
	@FXML
	private Label rate;
	@FXML
	private TextArea memo;
	@FXML
	private Label systemCA;
	@FXML
	private Button modify;

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		contentGroups.add(new ContentGroup(LanguageType.ENGLISH, name_en, tags_en, description_en, instruction_en));
		contentGroups.add(new ContentGroup(LanguageType.GERMAN, name_de, tags_de, description_de, instruction_de));
		contentGroups.add(new ContentGroup(LanguageType.CHINESE, name_zh, tags_zh, description_zh, instruction_zh));
		tabPane.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
	}

	@FXML
	private void clear() {
		if (Utility.showClearAlert()) {
			memo.clear();
			save();
		}
	}

	@FXML
	private void save() {
		CleaningAgentOperator.saveMemo(cleaningAgent, memo.getText());
	}

	@FXML
	private void modify() {
		new CleaningAgentModifier(OperationType.MODIFY, cleaningAgent).start(new Stage());
		stage.hide();
	}

	@FXML
	private void cancel() {
		stage.hide();
	}

	public void initializeContent(CleaningAgent cleaningAgent) {
		this.cleaningAgent = cleaningAgent;
		for (ContentGroup contentGroup : contentGroups) {
			contentGroup.showContent();
		}
		imageView.setImage(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgent));
		applicationTime.setText(String.valueOf(cleaningAgent.getApplicationTime()));
		frequency.setText(String.valueOf(cleaningAgent.getFrequency()));
		rate.setText(rateMap.get(cleaningAgent.getRate()));
		memo.setText(cleaningAgent.getMemo());
		if (!User.getPriority()) {
			modify.setDisable(cleaningAgent.BelongsToSystem());
			systemCA.setVisible(cleaningAgent.BelongsToSystem());
		} else {
			modify.setDisable(false);
			systemCA.setVisible(false);
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}