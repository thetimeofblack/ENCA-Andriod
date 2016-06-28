package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import application.CleaningAgentModifier;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.CleaningAgentOperator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utility.Utility;
import view.CleaningAgentModifierController.OperationType;

public final class CleaningAgentDetailController {

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
				Label label = new Label(tag.getName().getString(type));
				label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				label.setPadding(new Insets(0, 4, 0, 4));
				tags.getChildren().add(label);
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
	private void initialize() {
		contentGroups.add(new ContentGroup(LanguageType.ENGLISH, name_en, tags_en, description_en, instruction_en));
		contentGroups.add(new ContentGroup(LanguageType.GERMAN, name_de, tags_de, description_de, instruction_de));
		contentGroups.add(new ContentGroup(LanguageType.CHINESE, name_zh, tags_zh, description_zh, instruction_zh));
	}

	@FXML
	private void clear() {
		if(Utility.showClearAlert()) {
			memo.clear();
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
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}