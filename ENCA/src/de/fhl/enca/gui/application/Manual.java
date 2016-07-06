package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.ManualController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Manual extends Application {

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/Manual.fxml"), Utility.getResourceBundle());
		try {
			primaryStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		((ManualController) loader.getController()).setStage(primaryStage);
		primaryStage.setTitle("Manual");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
