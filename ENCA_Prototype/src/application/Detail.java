package application;

import java.io.IOException;
import java.util.ResourceBundle;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.DetailController;
import view.DetailController.DetailType;

public class Detail extends Application {

	private DetailType type;
	private CleaningAgent cleaningAgent;

	public Detail(DetailType type, CleaningAgent cleaningAgent) {
		this.type = type;
		this.cleaningAgent = cleaningAgent;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/Detail.fxml"), ResourceBundle.getBundle("resource.Detail", User.getInterfaceLanguage().getLocale()));
			primaryStage.setScene(new Scene(loader.load()));
			((DetailController) loader.getController()).initializeContent(type, cleaningAgent);
			primaryStage.setTitle("Detail");
			primaryStage.initStyle(StageStyle.UNIFIED);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}