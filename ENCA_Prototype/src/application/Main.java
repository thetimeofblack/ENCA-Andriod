package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Scene scene = new Scene(new FXMLLoader(Main.class.getResource("/view/Main.fxml")).load());
			primaryStage.setScene(scene);
			primaryStage.setTitle("ENCA");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}