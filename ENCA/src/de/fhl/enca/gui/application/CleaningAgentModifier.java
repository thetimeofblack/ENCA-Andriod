package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.CleaningAgentModifierController;
import de.fhl.enca.gui.view.CleaningAgentModifierController.OperationType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The cleaning agent modifier interface
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class CleaningAgentModifier extends Application {

	private OperationType operationType;
	private CleaningAgent cleaningAgent;

	public CleaningAgentModifier(OperationType operationType, CleaningAgent cleaningAgent) {
		super();
		this.operationType = operationType;
		this.cleaningAgent = cleaningAgent;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/CleaningAgentModifier.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((CleaningAgentModifierController) loader.getController()).setStage(primaryStage);
		((CleaningAgentModifierController) loader.getController()).initializeContent(operationType, cleaningAgent);
		primaryStage.setTitle("Cleaning Agent Modifier");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
}