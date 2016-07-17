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
 * The cleaning agent modifier interface.</br>
 * Interface allowing modifying, creating or deleting cleaning agent.</br>
 * Content of all three languages can be modified simultaneously, as well as the image.</br>
 * Relations between tags and cleaning agents can also be created and removed here.</br>
 * Choose tag from three combo boxes to stick it to the current cleaning agent. Double click the tag to remove it.</br>
 * Entry to tag adder is also provided allowing user to create new tag for the cleaning agent.</br>
 * Before saving, boundary check including name, application time and frequency will be executed.
 * Saving execution will be dropped if exception occurs during boundary check along with error message being shown.</br>
 * After saving, this interface will be disposed and detail of this cleaning agent will be shown.</br>
 * Size of this interface can be adjusted.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class CleaningAgentModifier extends Application {

	/**
	 * Indicates whether this applcation is used for modifying or adding.
	 */
	private OperationType operationType;

	/**
	 * The cleaning agent to be modified.
	 */
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
			scene.getStylesheets().add(this.getClass().getResource("/css/EncaStyle.css").toString());
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