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
		try {
			primaryStage.setScene(new Scene(new FXMLLoader(Main.class.getResource("/view/Main.fxml"), ResourceBundle.getBundle("res.Main", User.getInterfaceLanguage().getLocale())).load()));
			primaryStage.setTitle("ENCA");
			primaryStage.initStyle(StageStyle.UNIFIED);
			MainController.setStage(primaryStage);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}