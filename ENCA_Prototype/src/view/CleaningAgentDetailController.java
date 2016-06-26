package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.controller.CleaningAgentFetcher;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

public final class CleaningAgentDetailController {

	private final class ContentGroup {

		private LanguageType type;
		private Label name;
		private FlowPane tags;
		private Label description;
		private Label instruction;
		private ImageView imageView;
		private Label applicationTime;
		private Label frequency;
		private Label rate;

		public ContentGroup(LanguageType type, Label name, FlowPane tags, Label description, Label instruction, ImageView imageView, Label applicationTime, Label frequency, Label rate) {
			this.type = type;
			this.name = name;
			this.tags = tags;
			this.description = description;
			this.instruction = instruction;
			this.imageView = imageView;
			this.applicationTime = applicationTime;
			this.frequency = frequency;
			this.rate = rate;
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
			imageView.setImage(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgent.getCleaningAgentID()));
			applicationTime.setText(String.valueOf(cleaningAgent.getApplicationTime()));
			frequency.setText(String.valueOf(cleaningAgent.getFrequency()));
			rate.setText(rateMap.get(cleaningAgent.getRate()));
		}
	}

	private static Map<Integer, String> rateMap = new HashMap<>();
	static {
		rateMap.put(0, "☆☆☆☆☆");
		rateMap.put(1, "★☆☆☆☆");
		rateMap.put(2, "★★☆☆☆");
		rateMap.put(3, "★★★☆☆");
		rateMap.put(4, "★★★★☆");
		rateMap.put(5, "★★★★★");
	}

	private CleaningAgent cleaningAgent;
	private Set<ContentGroup> contentGroups = new HashSet<>();

	@FXML
	private Label name_en;
	@FXML
	private FlowPane tags_en;
	@FXML
	private Label description_en;
	@FXML
	private Label instruction_en;
	@FXML
	private ImageView imageView_en;
	@FXML
	private Label applicationTime_en;
	@FXML
	private Label frequency_en;
	@FXML
	private Label rate_en;

	@FXML
	private void initialize() {
		contentGroups.add(new ContentGroup(LanguageType.ENGLISH, name_en, tags_en, description_en, instruction_en, imageView_en, applicationTime_en, frequency_en, rate_en));
	}

	public void setCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgent = cleaningAgent;
		for (ContentGroup contentGroup : contentGroups) {
			contentGroup.showContent();
		}
	}
}