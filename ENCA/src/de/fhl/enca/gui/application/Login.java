package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.LoginFirstController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The login interface.</br>
 * Interface for user to login.</br>
 * Allow entering user name, choosing interface language and content language to meet user’s requirement.
 * Meanwhile, manual and about interface are also provided through help menu,
 * allowing user to grant basic information of the software before using it.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class Login extends Application {

	@Override
	public void start(Stage primaryStage) {
		User.initialize();
		Initialize.initialize();
		try {
			if (User.isFirstUse()) {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/LoginFirst.fxml"));
				primaryStage.setScene(new Scene(loader.load()));
				((LoginFirstController) loader.getController()).setLoginFirstStage(primaryStage);
			} else {
				FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/Login.fxml"), Utility.getResourceBundle());
				primaryStage.setScene(new Scene(loader.load()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.setResizable(false);
		primaryStage.setTitle("ENCA");
		primaryStage.getIcons().add(new Image(this.getClass().getResource("/image/Logo.png").toString()));
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
		if (!User.isFirstUse()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Main().start(new Stage());
			primaryStage.hide();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}