package utility;

import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;

public final class Utility {

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("resource.Utility", User.getInterfaceLanguage().getLocale());

	private static final ButtonType YES = new ButtonType(RESOURCE_BUNDLE.getString("yes"), ButtonData.YES);
	private static final ButtonType NO = new ButtonType(RESOURCE_BUNDLE.getString("no"), ButtonData.NO);
	private static final ButtonType CANCEL = new ButtonType(RESOURCE_BUNDLE.getString("cancel"), ButtonData.CANCEL_CLOSE);

	public static boolean showClearAlert() {
		return showAlert("clear");
	}

	public static boolean showDeleteCAAlert() {
		return showAlert("deleteCA");
	}

	private static boolean showAlert(String key) {
		Alert alert = new Alert(AlertType.WARNING, RESOURCE_BUNDLE.getString(key), YES, NO, CANCEL);
		return alert.showAndWait().filter(e -> e == YES).isPresent();
	}
}