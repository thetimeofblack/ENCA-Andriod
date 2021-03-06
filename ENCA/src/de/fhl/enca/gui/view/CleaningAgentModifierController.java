package de.fhl.enca.gui.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.CleaningAgentFetcher;
import de.fhl.enca.controller.CleaningAgentOperator;
import de.fhl.enca.controller.TagFetcher;
import de.fhl.enca.gui.application.CleaningAgentDetail;
import de.fhl.enca.gui.application.TagAdder;
import de.fhl.enca.gui.utility.Refreshable;
import de.fhl.enca.gui.utility.Utility;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Controller of CleaningAgentModifier interface.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class CleaningAgentModifierController implements Refreshable {

	/**
	 * Determine whether this interface is used for modifying or adding cleaning agent.
	 * @author Zhaowen.Gong
	 * @version 30.06.2016
	 */
	public static enum OperationType {
		MODIFY, ADD
	}

	/**
	 * Represent a group of controls showing content.
	 * @author Zhaowen.Gong
	 * @version 30.06.2016
	 */
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

		/**
		 * Show the content of the cleaning agent in controls.
		 */
		public void showContent() {
			if (operationType == OperationType.MODIFY) {
				name.setText(cleaningAgent.getName().getString(type));
				description.setText(cleaningAgent.getDescription().getString(type));
				instruction.setText(cleaningAgent.getInstruction().getString(type));
			}
		}

		/**
		 * Assembly the content of the controls to the cleaning agent.
		 */
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
	
	/**
	 * The cleaning agent to be modified.
	 */
	private CleaningAgent cleaningAgent;
	private Set<ContentGroup> contentGroups = new HashSet<>();
	private Map<ComboBox<Tag>, TagType> comboBoxes = new HashMap<>();
	
	/**
	 * Store the related tags shown in the interface.
	 */
	private Set<Tag> tags = new HashSet<>();
	
	/**
	 * The new image file chosen by user.
	 */
	private File imageFile = null;

	private Stage stage;

	@FXML
	private TabPane tabPane;
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
	private ComboBox<String> language;
	@FXML
	private Button delete;
	@FXML
	private Button save;

	/**
	 * Initialize the interface.</br>
	 * Will be called automatically during the construction of the Stage.
	 */
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
		refresh();
		for (ComboBox<Tag> comboBox : comboBoxes.keySet()) {
			comboBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tag> o, Tag oldValue, Tag newValue) -> {
				if (comboBox.getSelectionModel().getSelectedIndex() != -1) {
					Tag tag = Tag.getTag(comboBox.getSelectionModel().getSelectedItem().getTagID());
					if (!tags.contains(tag)) {
						addTagLabel(tag);
					}
				}
			});
		}
		language.setItems(FXCollections.observableArrayList("US", "DE", "CN"));
		tabPane.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
	}

	/**
	 * Clear memo.
	 */
	@FXML
	private void clear() {
		if (Utility.showClearAlert()) {
			memo.clear();
		}
	}

	/**
	 * Choose picture.
	 */
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

	/**
	 * Remove chosen picture.
	 */
	@FXML
	private void removePicture() {
		imageView.setImage(null);
		imageFile = null;
	}

	/**
	 * Jump to TagAdder interface.
	 */
	@FXML
	private void addTag() {
		new TagAdder(this).start(new Stage());
	}

	/**
	 * Delete the cleaning agent.
	 */
	@FXML
	private void delete() {
		if (Utility.showDeleteCAAlert()) {
			CleaningAgentOperator.removeCleaningAgent(cleaningAgent);
			Utility.refreshMain();
			stage.hide();
		}
	}

	/**
	 * Save the modification to the cleaning agent.</br>
	 * Boundary check will also be done.
	 */
	@FXML
	private void save() {
		if (validate() && validateBoundary()) {
			cleaningAgent = assembly();
			switch (operationType) {
				case MODIFY:
					CleaningAgentOperator.modifyCleaningAgent(cleaningAgent);
					break;
				case ADD:
					CleaningAgentOperator.createCleaningAgent(cleaningAgent);
					break;
			}
			delete.setDisable(false);
			CleaningAgentOperator.saveMemo(cleaningAgent, memo.getText());
			if (imageFile != null) {
				CleaningAgentOperator.saveImage(cleaningAgent, imageFile);
			}
			Utility.refreshMain();
			new CleaningAgentDetail(cleaningAgent).start(new Stage());
			stage.hide();
		}
	}

	/**
	 * Dispose the interface.
	 */
	@FXML
	private void cancel() {
		stage.hide();
	}

	/**
	 * Show the content of the cleaning agent in the interface.</br>
	 * Is called by class CleaningAgentModifier.
	 */
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
			language.getSelectionModel().clearAndSelect(cleaningAgent.getMainLanguage().getId());
		} else {
			rate.getSelectionModel().clearAndSelect(4);
			delete.setDisable(true);
			save.setDisable(true);
			language.getSelectionModel().clearAndSelect(User.getContentLanguage().getId());
		}
	}

	/**
	 * Add chosen related tag to the interface.
	 */
	private void addTagLabel(Tag tag) {
		tags.add(tag);
		Label label = Utility.getTagLabel(tag, User.getInterfaceLanguage());
		label.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1) {
				tags.remove(tag);
				tagBox.getChildren().remove(label);
				for (ComboBox<Tag> comboBox : comboBoxes.keySet()) {
					comboBox.getSelectionModel().clearSelection();
				}
			}
		});
		tagBox.getChildren().add(label);
	}

	/**
	 * Assembly the content on the interface and generate a new cleaning agent.
	 */
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
		newCleaningAgent.setBelongsToSystem(operationType == OperationType.ADD ? false : cleaningAgent.BelongsToSystem());
		newCleaningAgent.addTagsAll(tags);
		newCleaningAgent.setMainLanguage(LanguageType.getLanguageType(language.getSelectionModel().getSelectedIndex()));
		return newCleaningAgent;
	}

	/**
	 * Check if all the names are null.
	 */
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

	/**
	 * Boundary check for application time and frequency.</br>
	 * if exception occurs, error messages will be displayed.
	 */
	private boolean validateBoundary() {
		boolean appTimeValid = validateFormat(applicationTime.getText());
		boolean frequencyValid = validateFormat(frequency.getText());
		if (!appTimeValid && !frequencyValid) {
			Utility.showBothError();
			return false;
		}
		if (!appTimeValid) {
			Utility.showApplicationTimeError();
			return false;
		}
		if (!frequencyValid) {
			Utility.showFrequencyError();
			return false;
		}
		return true;
	}

	/**
	 * Boundary check for single number field.
	 */
	private boolean validateFormat(String source) {
		boolean valid;
		if (!source.equals("")) {
			try {
				valid = Long.valueOf(source) >= 0;
			} catch (NumberFormatException e) {
				valid = false;
			}
		} else {
			valid = true;
		}
		return valid;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Refresh the tag content in the three combo boxes.
	 */
	@Override
	public void refresh() {
		for (Entry<ComboBox<Tag>, TagType> entry : comboBoxes.entrySet()) {
			entry.getKey().setItems(FXCollections.observableArrayList(TagFetcher.fetchTagsAllOfCertainType(entry.getValue())));
		}
	}
}