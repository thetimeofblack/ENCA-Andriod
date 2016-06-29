package application;

import java.io.IOException;
import de.fhl.enca.bl.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.Utility;
import view.LoginController;
import view.LoginFirstController;

public final class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		User.initialize();
		try {
			if (User.isFirstUse()) {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/LoginFirst.fxml"));
				primaryStage.setScene(new Scene(loader.load()));
				((LoginFirstController) loader.getController()).setLoginFirstStage(primaryStage);
			} else {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Login.fxml"), Utility.getResourceBundle());
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