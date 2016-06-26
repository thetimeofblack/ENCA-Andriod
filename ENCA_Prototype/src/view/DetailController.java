package view;

import java.io.File;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public final class DetailController {

	public static enum DetailType {
		DETAIL(false), MODIFY(true), ADD(true);

		private boolean editable;

		private DetailType(boolean editable) {
			this.editable = editable;
		}
	}

	private final static class TextGroup {

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

	private Stage detailStage = null;
	private ResourceBundle resourceBundle = null;
	private CleaningAgent cleaningAgent = null;

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
	@FXML
	private ImageView imageView;
	@FXML
	private FlowPane flowPane;

	private Set<TextGroup> textGroupSet = new HashSet<>();

	@FXML
	private void initialize() {
		textGroupSet.add(new TextGroup(LanguageType.ENGLISH, name_en, descrition_en, instruction_en));
		textGroupSet.add(new TextGroup(LanguageType.GERMAN, name_de, descrition_de, instruction_de));
		textGroupSet.add(new TextGroup(LanguageType.CHINESE, name_zh, descrition_zh, instruction_zh));
		tabPane.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
	}

	@FXML
	private void delete() {
		ButtonType yes = new ButtonType(resourceBundle.getString("yes"), ButtonData.YES);
		ButtonType no = new ButtonType(resourceBundle.getString("no"), ButtonData.NO);
		Alert alert = new Alert(AlertType.CONFIRMATION, resourceBundle.getString("delete?"), yes, no);
		alert.showAndWait().filter(e -> e == yes).ifPresent(e -> detailStage.hide());
	}

	@FXML
	private void choosePicture() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new ExtensionFilter(resourceBundle.getString("imageFiles"), "*.png", "*.jpg"));
		File file = fileChooser.showOpenDialog(detailStage);
		if (file != null && file.isFile() && file.exists()) {
			System.out.println(file.getName());
		}
	}

	public void initializeContent(DetailType type, CleaningAgent cleaningAgent) {
		this.cleaningAgent = cleaningAgent;
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
		imageView.setImage(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgent.getCleaningAgentID()));
		if (!type.editable) {
			flowPane.getChildren().clear();
			for (Tag tag : cleaningAgent.getTags()) {
				Label label = new Label(tag.getName().getString(User.getInterfaceLanguage()));
				label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
				label.setPadding(new Insets(0, 4, 0, 4));
				flowPane.getChildren().add(label);
			}
		} else {
			flowPane.getChildren().clear();
			for (Tag tag : cleaningAgent.getTags()) {
				flowPane.getChildren().add(new Button(tag.getName().getString(User.getInterfaceLanguage())));
			}
		}
	}

	public void setDetailStage(Stage stage) {
		this.detailStage = stage;
	}

	public void setResourceBundle(ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
	}
}