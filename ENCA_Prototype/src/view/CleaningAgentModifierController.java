package view;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.controller.CleaningAgentFetcher;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public final class CleaningAgentModifierController {

	public static enum OperationType {
		MODIFY, ADD
	}

	private final class ContentGroup {

		private LanguageType type;
		private TextField name;
		private TextArea description;
		private TextArea instruction;

		public ContentGroup(LanguageType type, TextField name, TextArea description, TextArea instruction) {
			this.type = type;
			this.name = name;
			this.description = description;
			this.instruction = instruction;
		}

		public void showContent() {
			if (operationType == OperationType.MODIFY) {
				name.setText(cleaningAgent.getName().getString(type));
				description.setText(cleaningAgent.getDescription().getString(type));
				instruction.setText(cleaningAgent.getInstruction().getString(type));
			}
		}
	}

	private OperationType operationType;
	private CleaningAgent cleaningAgent;
	private Set<ContentGroup> contentGroups = new HashSet<>();

	@FXML
	private TextField name_en;
	@FXML
	private TextArea description_en;
	@FXML
	private TextArea instruction_en;

	@FXML
	private TextArea memo;
	@FXML
	private ImageView imageView;

	@FXML
	private void initialize() {
		contentGroups.add(new ContentGroup(LanguageType.ENGLISH, name_en, description_en, instruction_en));
	}

	@FXML
	private void clearMemo() {
		memo.setText("");
	}

	public void initializeContent(OperationType operationType, CleaningAgent cleaningAgent) {
		this.operationType = operationType;
		this.cleaningAgent = cleaningAgent;
		for (ContentGroup contentGroup : contentGroups) {
			contentGroup.showContent();
		}
		memo.setText(cleaningAgent.getMemo());
		imageView.setImage(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgent));
	}
}