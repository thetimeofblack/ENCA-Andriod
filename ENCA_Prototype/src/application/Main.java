package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.LanguagePreference;
import de.fhl.enca.controller.Initialize;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Initialize.initialize();
		try {
			Scene scene = new Scene(new FXMLLoader(Main.class.getResource("/view/Main.fxml"), ResourceBundle.getBundle("res.Main", LanguagePreference.getInterfaceLanguage().getLocale())).load());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ENCA");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}