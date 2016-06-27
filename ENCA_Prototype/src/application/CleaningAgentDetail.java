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
import view.CleaningAgentDetailController;

public class CleaningAgentDetail extends Application {

	private CleaningAgent cleaningAgent;

	public CleaningAgentDetail(CleaningAgent cleaningAgent) {
		super();
		this.cleaningAgent = cleaningAgent;
	}

	@Override
	public void start(Stage primaryStage) {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/view/CleaningAgentDetail.fxml"), ResourceBundle.getBundle("resource.CleaningAgentDetail", User.getInterfaceLanguage().getLocale()));
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(this.getClass().getResource("/css/TabPaneHeader.css").toString());
			primaryStage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
		((CleaningAgentDetailController) loader.getController()).initializeContent(cleaningAgent);
		primaryStage.setTitle("Cleaning Agent Detail");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.show();
	}
}