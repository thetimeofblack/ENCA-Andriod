package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.MainController;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Initialize.initialize();
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/Main.fxml"), ResourceBundle.getBundle("resource.Main", User.getInterfaceLanguage().getLocale()));
		try {
			primaryStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		((MainController) loader.getController()).setMainStage(primaryStage);
		primaryStage.setTitle("ENCA");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
}