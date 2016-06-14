package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.LanguagePreference;
import de.fhl.enca.bl.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.LoginController;
import view.LoginFirstController;

public final class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader;
			if (User.isFirstUse()) {
				loader = new FXMLLoader(Login.class.getResource("/view/LoginFirst.fxml"));
				LoginFirstController.setLoginStage(primaryStage);
			} else {
				loader = new FXMLLoader(Login.class.getResource("/view/Login.fxml"), ResourceBundle.getBundle("res.Login", LanguagePreference.getInterfaceLanguage().getLocale()));
				LoginController.setLoginStage(primaryStage);
			}
			primaryStage.setScene(new Scene(loader.load()));
			primaryStage.setResizable(false);
			primaryStage.setTitle("ENCA");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}