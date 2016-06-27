package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TagModifier extends Application {

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/TagModifier.fxml"), ResourceBundle.getBundle("resource.TagModifier", User.getInterfaceLanguage().getLocale()));
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.setTitle("Tag Modifier");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}