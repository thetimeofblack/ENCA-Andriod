package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.CleaningAgentDetailController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The cleaning agent detail interface
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class CleaningAgentDetail extends Application {

	/**
	 * The cleaning agent to be shown.
	 */
	private CleaningAgent cleaningAgent;

	public CleaningAgentDetail(CleaningAgent cleaningAgent) {
		super();
		this.cleaningAgent = cleaningAgent;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/CleaningAgentDetail.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((CleaningAgentDetailController) loader.getController()).setStage(primaryStage);
		((CleaningAgentDetailController) loader.getController()).initializeContent(cleaningAgent);
		primaryStage.setTitle("Cleaning Agent Detail");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
}