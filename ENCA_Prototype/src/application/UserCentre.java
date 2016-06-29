package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.UserCentreController;

public final class UserCentre extends Application {

	private Stage mainStage;

	public UserCentre(Stage mainStage) {
		this.mainStage = mainStage;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/UserCentre.fxml"), ResourceBundle.getBundle("resource.UserCentre", User.getInterfaceLanguage().getLocale()));
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((UserCentreController) loader.getController()).setMainStage(mainStage);
		((UserCentreController) loader.getController()).setUserCentreStage(primaryStage);
		primaryStage.setTitle("User Centre");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}