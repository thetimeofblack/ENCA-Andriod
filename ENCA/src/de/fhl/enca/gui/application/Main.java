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
 * The main interface.</br>
 * Main interface of the software.</br>
 * Allow searching cleaning agents by tags or by keywords.</br>
 * Information about origin, name and tags of cleaning agents as results will be shown in the tables.</br>
 * When single tag is chosen, tags of other types will be filtered and only those related tags of the chosen tag will be shown.</br>
 * Search result will be displayed in real time when user types in the keywords.</br>
 * Multiple entries to other interfaces are implemented in buttons, context menus and menus in menu bar.</br>
 * Size of this interface can be adjusted.
 * @author Zhaowen.Gong
 * @version 30.06.2016
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