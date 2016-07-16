package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The main interface
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see de.fhl.enca.gui.view.MainController
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/Main.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/EncaStyle.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((MainController) loader.getController()).setMainStage(primaryStage);
		Utility.setMainController(((MainController) loader.getController()));
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		primaryStage.setTitle("ENCA");
		primaryStage.getIcons().add(new Image(this.getClass().getResource("/image/Logo.png").toString()));
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
}