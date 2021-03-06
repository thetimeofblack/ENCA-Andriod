package de.fhl.enca.gui.application;

import java.io.IOException;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.gui.utility.Utility;
import de.fhl.enca.gui.view.TagModifierController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * The tag modifier interface.</br>
 * Interface allowing modifying and deleting tags.</br>
 * The tag should be chosen in three lists on the left before modifying and deleting,
 * then the names of the tags will be displayed on the right, allowing modifying.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class TagModifier extends Application {

	/**
	 * The tag which this interface should directly go to
	 */
	private Tag tag;

	public TagModifier(Tag tag) {
		this.tag = tag;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/de/fhl/enca/gui/view/TagModifier.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/EncaStyle.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((TagModifierController) loader.getController()).setStage(primaryStage);
		if (tag != null) {
			((TagModifierController) loader.getController()).initializeContent(tag);
		}
		primaryStage.setTitle("Tag Modifier");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}