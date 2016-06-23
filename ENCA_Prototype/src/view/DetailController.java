package view;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public final class DetailController {

	public static enum DetailType {
		DETAIL, MODIFY, ADD;
	}

	private static class TextGroup {

		private LanguageType languageType;
		private TextField name;
		private TextArea description;
		private TextArea instruction;

		public TextGroup(LanguageType languageType, TextField name, TextArea description, TextArea instruction) {
			this.languageType = languageType;
			this.name = name;
			this.description = description;
			this.instruction = instruction;
		}

		public void setContent(CleaningAgent cleaningAgent) {
			name.setText(cleaningAgent.getName().getString(languageType));
			description.setText(cleaningAgent.getDescription().getString(languageType));
			instruction.setText(cleaningAgent.getInstruction().getString(languageType));
		}
	}

	@FXML
	private TextField name_en;
	@FXML
	private TextArea descrition_en;
	@FXML
	private TextArea instruction_en;
	@FXML
	private TextField name_de;
	@FXML
	private TextArea descrition_de;
	@FXML
	private TextArea instruction_de;
	@FXML
	private TextField name_zh;
	@FXML
	private TextArea descrition_zh;
	@FXML
	private TextArea instruction_zh;

	private Set<TextGroup> textGroupSet = new HashSet<>();

	@FXML
	private void initialize() {
		textGroupSet.add(new TextGroup(LanguageType.ENGLISH, name_en, descrition_en, instruction_en));
		textGroupSet.add(new TextGroup(LanguageType.GERMAN, name_de, descrition_de, instruction_de));
		textGroupSet.add(new TextGroup(LanguageType.CHINESE, name_zh, descrition_zh, instruction_zh));
	}

	public void initializeContent(DetailType type, CleaningAgent cleaningAgent) {
		for (TextGroup textGroup : textGroupSet) {
			textGroup.setContent(cleaningAgent);
		}
	}
}