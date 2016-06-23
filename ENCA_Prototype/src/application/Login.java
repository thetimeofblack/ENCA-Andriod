package application;

import java.io.IOException;
import java.util.ResourceBundle;
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
				primaryStage.setScene(new Scene(loader.load()));
				((LoginFirstController) loader.getController()).setStage(primaryStage);
			} else {
				loader = new FXMLLoader(Login.class.getResource("/view/Login.fxml"), ResourceBundle.getBundle("resource.Login", User.getInterfaceLanguage().getLocale()));
				primaryStage.setScene(new Scene(loader.load()));
				((LoginController) loader.getController()).setStage(primaryStage);
			}
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