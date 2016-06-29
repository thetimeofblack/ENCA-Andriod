package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utility.Utility;
import view.MainController;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Main.fxml"), Utility.getResourceBundle());
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((MainController) loader.getController()).setMainStage(primaryStage);
		Utility.setMainController(((MainController) loader.getController()));
		primaryStage.setTitle("ENCA");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
}