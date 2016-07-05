package de.fhl.enca.gui.utility;

import java.util.ResourceBundle;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import de.fhl.enca.gui.view.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

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

	public static ObservableList<String> getLanguageList() {
		ObservableList<String> languageList = FXCollections.observableArrayList();
		for (LanguageType type : LanguageType.values()) {
			languageList.add(type.toString());
		}
		return languageList;
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
		mainController.refreshMain();
	}
}