package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import application.CleaningAgentDetail;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.CleaningAgentOperator;
import de.fhl.enca.controller.TagFetcher;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.CleaningAgentBean;
import utility.Utility;

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
			this.name.textProperty().addListener((ObservableValue<? extends String> o, String x, String y) -> save.setDisable(!validate()));
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

		public void assemblyContent(CleaningAgent cleaningAgent) {
			cleaningAgent.getName().setString(type, name.getText());
			cleaningAgent.getDescription().setString(type, description.getText());
			cleaningAgent.getInstruction().setString(type, instruction.getText());
		}

		public String getName() {
			return name.getText();
		}
	}

	private OperationType operationType;
	private CleaningAgent cleaningAgent;
	private Set<ContentGroup> contentGroups = new HashSet<>();
	private Map<ComboBox<Tag>, TagType> comboBoxes = new HashMap<>();
	private Set<Tag> tags = new HashSet<>();
	private File imageFile = null;

	private Stage stage;

	@FXML
	private TextField name_en;
	@FXML
	private TextArea description_en;
	@FXML
	private TextArea instruction_en;
	@FXML
	private TextField name_de;
	@FXML
	private TextArea description_de;
	@FXML
	private TextArea instruction_de;
	@FXML
	private TextField name_zh;
	@FXML
	private TextArea description_zh;
	@FXML
	private TextArea instruction_zh;

	@FXML
	private TextArea memo;
	@FXML
	private ImageView imageView;
	@FXML
	private TextField applicationTime;
	@FXML
	private TextField frequency;
	@FXML
	private ComboBox<String> rate;
	@FXML
	private VBox tagBox;
	@FXML
	private ComboBox<Tag> addRoom;
	@FXML
	private ComboBox<Tag> addItem;
	@FXML
	private ComboBox<Tag> addOthers;
	@FXML
	private Button detail;
	@FXML
	private Button delete;
	@FXML
	private Button save;

	@FXML
	private void initialize() {
		contentGroups.add(new ContentGroup(LanguageType.ENGLISH, name_en, description_en, instruction_en));
		contentGroups.add(new ContentGroup(LanguageType.GERMAN, name_de, description_de, instruction_de));
		contentGroups.add(new ContentGroup(LanguageType.CHINESE, name_zh, description_zh, instruction_zh));
		comboBoxes.put(addRoom, TagType.ROOM);
		comboBoxes.put(addItem, TagType.ITEM);
		comboBoxes.put(addOthers, TagType.OTHERS);
		ObservableList<String> rateList = FXCollections.observableArrayList("☆", "★", "★☆", "★★", "★★☆", "★★★", "★★★☆", "★★★★", "★★★★☆", "★★★★★");
		rate.setItems(rateList);
		for (Entry<ComboBox<Tag>, TagType> entry : comboBoxes.entrySet()) {
			entry.getKey().setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(entry.getValue())));
			entry.getKey().getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag oldValue, Tag newValue) -> {
				if (newValue != null) {
					Tag tag = Tag.getTag(newValue.getTagID());
					if (!tags.contains(tag)) {
						addTagLabel(tag);
					}
				}
			});
		}
	}

	@FXML
	private void clear() {
		if (Utility.showClearAlert()) {
			memo.clear();
		}
	}

	@FXML
	private void choosePicture() {
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().add(new ExtensionFilter("Image files", "*.png", "*.jpg"));
		File file = chooser.showOpenDialog(stage);
		if (file != null && file.isFile() && file.exists()) {
			try {
				Image image = new Image(new FileInputStream(file));
				if (image != null && !image.isError()) {
					imageView.setImage(image);
					imageFile = file;
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void detail() {
		new CleaningAgentDetail(cleaningAgent).start(new Stage());
		stage.hide();
	}

	@FXML
	private void delete() {
		if (Utility.showDeleteCAAlert()) {
			CleaningAgentOperator.removeCleaningAgent(cleaningAgent);
			CleaningAgentBean.removeCleaningAgentBean(cleaningAgent);
			Utility.refreshMain();
			stage.hide();
		}
	}

	@FXML
	private void save() {
		if (validate()) {
			cleaningAgent = assembly();
			switch (operationType) {
				case MODIFY:
					CleaningAgentOperator.modifyCleaningAgent(cleaningAgent);
					break;
				case ADD:
					CleaningAgentOperator.createCleaningAgent(cleaningAgent);
					break;
			}
			CleaningAgentBean.addCleaningAgentBean(cleaningAgent);
			detail.setDisable(false);
			delete.setDisable(false);
			CleaningAgentOperator.saveMemo(cleaningAgent, memo.getText());
			if (imageFile != null) {
				CleaningAgentOperator.saveImage(cleaningAgent, imageFile);
			}
			Utility.refreshMain();
		}
	}

	@FXML
	private void cancel() {
		stage.hide();
	}

	public void initializeContent(OperationType operationType, CleaningAgent cleaningAgent) {
		this.operationType = operationType;
		this.cleaningAgent = cleaningAgent;
		if (operationType == OperationType.MODIFY) {
			for (ContentGroup contentGroup : contentGroups) {
				contentGroup.showContent();
			}
			memo.setText(cleaningAgent.getMemo());
			imageView.setImage(CleaningAgentFetcher.fetchImageOfCleaningAgent(cleaningAgent));
			applicationTime.setText(String.valueOf(cleaningAgent.getApplicationTime()));
			frequency.setText(String.valueOf(cleaningAgent.getFrequency()));
			rate.getSelectionModel().clearAndSelect(cleaningAgent.getRate() - 1);
			tags.addAll(cleaningAgent.getTags());
			for (Tag tag : tags) {
				addTagLabel(tag);
			}
		} else {
			rate.getSelectionModel().clearAndSelect(0);
			detail.setDisable(true);
			delete.setDisable(true);
			save.setDisable(true);
		}
		System.out.println(applicationTime.getText() == null);
		System.out.println(applicationTime.getText().equals(""));
	}

	private void addTagLabel(Tag tag) {
		tags.add(tag);
		Label label = new Label(tag.getName().getString(User.getInterfaceLanguage()));
		label.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		label.setPadding(new Insets(0, 4, 0, 4));
		label.setOnMouseClicked(e -> {
			tags.remove(tag);
			tagBox.getChildren().remove(label);
		});
		tagBox.getChildren().add(label);
	}

	private CleaningAgent assembly() {
		int id = 0;
		switch (operationType) {
			case MODIFY:
				id = cleaningAgent.getCleaningAgentID();
				break;
			case ADD:
				id = CleaningAgent.getMaxID() + 1;
				CleaningAgent.setMaxID(id);
				break;
		}
		CleaningAgent newCleaningAgent = new CleaningAgent(id);
		newCleaningAgent.setName(new InternationalString());
		newCleaningAgent.setDescription(new InternationalString());
		newCleaningAgent.setInstruction(new InternationalString());
		for (ContentGroup contentGroup : contentGroups) {
			contentGroup.assemblyContent(newCleaningAgent);
		}
		newCleaningAgent.setApplicationTime(applicationTime.getText().equals("") ? 0 : Long.valueOf(applicationTime.getText()));
		newCleaningAgent.setFrequency(frequency.getText().equals("") ? 0 : Long.valueOf(frequency.getText()));
		newCleaningAgent.setRate(rate.getSelectionModel().getSelectedIndex() + 1);
		newCleaningAgent.setBelongsToSystem(false);
		newCleaningAgent.addTagsAll(tags);
		return newCleaningAgent;
	}

	private boolean validate() {
		boolean valid = false;
		for (ContentGroup contentGroup : contentGroups) {
			if (contentGroup.getName() != null && !contentGroup.getName().equals("")) {
				valid = true;
				break;
			}
		}
		return valid;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}