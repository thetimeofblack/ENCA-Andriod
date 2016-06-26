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
		FXMLLoader loader = new FXMLLoader(CleaningAgentDetail.class.getResource("/view/CleaningAgentDetail.fxml"), ResourceBundle.getBundle("resource.CleaningAgentDetail", User.getInterfaceLanguage().getLocale()));
		try {
			primaryStage.setScene(new Scene(loader.load()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		((CleaningAgentDetailController) loader.getController()).setCleaningAgent(cleaningAgent);
		primaryStage.setTitle("Cleaning Agent Detail");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
