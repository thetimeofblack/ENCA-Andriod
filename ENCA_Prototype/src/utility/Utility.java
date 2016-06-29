package utility;

import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import view.MainController;

public final class Utility {

	private static MainController mainController;
	
	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("resource.message", User.getInterfaceLanguage().getLocale());
	}

	public static boolean showClearAlert() {
		return showAlert("clearAlert");
	}

	public static boolean showDeleteCAAlert() {
		return showAlert("deleteCAAlert");
	}

	public static boolean showDeleteTagAlert() {
		return showAlert("deleteTagAlert");
	}

	private static boolean showAlert(String key) {
		ResourceBundle resourceBundle = getResourceBundle();
		ButtonType yes = new ButtonType(resourceBundle.getString("yes"), ButtonData.YES);
		ButtonType no = new ButtonType(resourceBundle.getString("no"), ButtonData.NO);
		ButtonType cancel = new ButtonType(resourceBundle.getString("cancel"), ButtonData.CANCEL_CLOSE);
		Alert alert = new Alert(AlertType.WARNING, resourceBundle.getString(key), yes, no, cancel);
		return alert.showAndWait().filter(e -> e == yes).isPresent();
	}

	public static void setMainController(MainController mainController) {
		Utility.mainController = mainController;
	}

	public static void refreshMain() {
		mainController.refreshMain();
	}
}