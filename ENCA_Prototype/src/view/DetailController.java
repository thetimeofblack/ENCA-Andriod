package view;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public final class DetailController {

	public static enum DetailType {
		DETAIL(false), MODIFY(true), ADD(true);

		private boolean editable;

		private DetailType(boolean editable) {
			this.editable = editable;
		}
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

		public void setContent(CleaningAgent cleaningAgent, boolean editable) {
			name.setText(cleaningAgent.getName().getString(languageType));
			name.setEditable(editable);
			description.setText(cleaningAgent.getDescription().getString(languageType));
			description.setEditable(editable);
			instruction.setText(cleaningAgent.getInstruction().getString(languageType));
			instruction.setEditable(editable);
		}
	}

	@FXML
	private TabPane tabPane;
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
	@FXML
	private TextField applicationTime;
	@FXML
	private TextField frequency;
	@FXML
	private AnchorPane rateDetailPane;
	@FXML
	private TextField rateDetail;
	@FXML
	private AnchorPane rateModifyPane;
	@FXML
	private ComboBox<String> rateModify;

	private Set<TextGroup> textGroupSet = new HashSet<>();

	@FXML
	private void initialize() {
		textGroupSet.add(new TextGroup(LanguageType.ENGLISH, name_en, descrition_en, instruction_en));
		textGroupSet.add(new TextGroup(LanguageType.GERMAN, name_de, descrition_de, instruction_de));
		textGroupSet.add(new TextGroup(LanguageType.CHINESE, name_zh, descrition_zh, instruction_zh));
		tabPane.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
	}

	public void initializeContent(DetailType type, CleaningAgent cleaningAgent) {
		for (TextGroup textGroup : textGroupSet) {
			textGroup.setContent(cleaningAgent, type.editable);
		}
		applicationTime.setText(String.valueOf(cleaningAgent.getApplicationTime()));
		applicationTime.setEditable(type.editable);
		frequency.setText(String.valueOf(cleaningAgent.getFrequency()));
		frequency.setEditable(type.editable);
		rateDetailPane.setVisible(!type.editable);
		rateDetail.setText(String.valueOf(cleaningAgent.getRate()));
		rateDetail.setEditable(false);
		rateModifyPane.setVisible(type.editable);
	}
}