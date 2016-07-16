package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.gui.utility.Refreshable;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.TagAdderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The tag adder interface
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class TagAdder extends Application {

	/**
	 * The interface which calls this TagAdder interface.
	 * Should be refreshed after a tag is added.
	 */
	private Refreshable refreshable;

	public TagAdder(Refreshable refreshable) {
		this.refreshable = refreshable;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/TagAdder.fxml"), Utility.getResourceBundle());
		try {
			primaryStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		((TagAdderController) loader.getController()).setStage(primaryStage);
		((TagAdderController) loader.getController()).setRefreshable(refreshable);
		primaryStage.setTitle("Tag Adder");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}