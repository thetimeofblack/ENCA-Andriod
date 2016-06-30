package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.UserCentreController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The user centre interface
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class UserCentre extends Application {

	private Stage mainStage;

	public UserCentre(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/UserCentre.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((UserCentreController) loader.getController()).setMainStage(mainStage);
		((UserCentreController) loader.getController()).setUserCentreStage(primaryStage);
		primaryStage.setTitle("User Centre");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}