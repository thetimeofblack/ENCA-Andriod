package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.UserCentreController;

public final class UserCentre extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(new Scene(new FXMLLoader(Main.class.getResource("/view/UserCentre.fxml"), ResourceBundle.getBundle("res.UserCentre", User.getInterfaceLanguage().getLocale())).load()));
			primaryStage.setTitle("User Centre");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.setResizable(false);
			UserCentreController.setStage(primaryStage);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
