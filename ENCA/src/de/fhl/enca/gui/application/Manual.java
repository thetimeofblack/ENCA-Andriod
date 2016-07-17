package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.AboutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The manual interface.</br>
 * Interface showing instruction about how to use the software.</br>
 * Will be shown automatically when user first uses the software.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class Manual extends Application {

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/Manual.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/EncaStyle.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((AboutController) loader.getController()).setStage(primaryStage);
		primaryStage.setTitle("Manual");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
