package de.fhl.enca.gui.utility;

import java.util.ResourceBundle;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.User;
import de.fhl.enca.gui.model.CleaningAgentBean;
import de.fhl.enca.gui.view.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * Provide useful utilities for other interfaces.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class Utility {

	/**
	 * Store the reference of the MainController.
	 * @see MainController
	 */
	private static MainController mainController;

	public static void setMainController(MainController mainController) {
		Utility.mainController = mainController;
	}

	/**
	 * Get the ResourceBundle according to the interface language.
	 */
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("resource.message", User.getInterfaceLanguage().getLocale());
	}

	/**
	 * Return the list of all the languages supported
	 */
	public static ObservableList<String> getLanguageList() {
		ObservableList<String> languageList = FXCollections.observableArrayList();
		for (LanguageType type : LanguageType.values()) {
			languageList.add(type.toString());
		}
		return languageList;
	}

	/**
	 * Return a label that holds the tag
	 * @param tag the tag to be held
	 */
	public static Label getTagLabel(Tag tag, LanguageType type) {
		Label label = new Label(tag.getName().getString(type));
		label.setBorder(new Border(new BorderStroke(User.getTagColor(tag.getTagType()), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		label.setPadding(new Insets(0, 4, 0, 4));
		label.setTextFill(Color.WHITE);
		label.setBackground(new Background(new BackgroundFill(User.getTagColor(tag.getTagType()), CornerRadii.EMPTY, new Insets(0))));
		return label;
	}

	/**
	 * Show alert when memo is to be cleared.
	 * @return whether user confirm the execution
	 */
	public static boolean showClearAlert() {
		return showAlert("clearAlert");
	}

	/**
	 * Show alert when a cleaning agent is to be deleted.
	 * @return whether user confirm the execution
	 */
	public static boolean showDeleteCAAlert() {
		return showAlert("deleteCAAlert");
	}

	/**
	 * Show alert when a tag is to be deleted.
	 * @return whether user confirm the execution
	 */
	public static boolean showDeleteTagAlert() {
		return showAlert("deleteTagAlert");
	}

	/**
	 * Show alert when a duplicate tag is to be added.
	 * @return whether user confirm the execution
	 */
	public static boolean showAddTagAlert() {
		return showAlert("addTagAlert");
	}

	/**
	 * Generate the alert according to the key.
	 * @param key key representing the content of the alert
	 * @return whether user confirm the execution
	 */
	private static boolean showAlert(String key) {
		ResourceBundle resourceBundle = getResourceBundle();
		ButtonType ok = new ButtonType(resourceBundle.getString("ok"), ButtonData.YES);
		ButtonType cancel = new ButtonType(resourceBundle.getString("cancel"), ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING, resourceBundle.getString(key), ok, cancel);
		return alert.showAndWait().filter(e -> e == ok).isPresent();
	}

	/**
	 * Refresh the Main interface.</br>
	 * Called when execution of modifying, adding and deleting is done.
	 * @see MainController
	 */
	public static void refreshMain() {
		CleaningAgentBean.refreshCleaningAgentBeans();
		mainController.refreshMain();
	}
}