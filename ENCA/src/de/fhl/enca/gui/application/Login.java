package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.bl.User;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.LoginController;
import de.fhl.enca.gui.view.LoginFirstController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The login interface
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		User.initialize();
		try {
			if (User.isFirstUse()) {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/LoginFirst.fxml"));
				primaryStage.setScene(new Scene(loader.load()));
				((LoginFirstController) loader.getController()).setLoginFirstStage(primaryStage);
			} else {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/Login.fxml"), Utility.getResourceBundle());
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