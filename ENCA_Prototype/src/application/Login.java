package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;
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
		User.initialize();
		Initialize.initialize();
		FXMLLoader loader;
		try {
			if (User.isFirstUse()) {
				loader = new FXMLLoader(this.getClass().getResource("/view/LoginFirst.fxml"));
				primaryStage.setScene(new Scene(loader.load()));
				((LoginFirstController) loader.getController()).setLoginFirstStage(primaryStage);
			} else {
				loader = new FXMLLoader(this.getClass().getResource("/view/Login.fxml"), ResourceBundle.getBundle("resource.Login", User.getInterfaceLanguage().getLocale()));
				primaryStage.setScene(new Scene(loader.load()));
				((LoginController) loader.getController()).setLoginStage(primaryStage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.setResizable(false);
		primaryStage.setTitle("ENCA");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}