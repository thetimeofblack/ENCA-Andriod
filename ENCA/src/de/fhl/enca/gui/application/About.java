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
 * The about interface.</br>
 * Interface showing basic and legal information of the software.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class About extends Application {

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/About.fxml"), Utility.getResourceBundle());
		try {
			primaryStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		((AboutController) loader.getController()).setStage(primaryStage);
		primaryStage.setTitle("About");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
