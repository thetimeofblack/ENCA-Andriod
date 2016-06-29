package de.fhl.enca.gui.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * Controller of About interface.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class AboutController {

	private Stage stage;

	@FXML
	private void ok() {
		stage.hide();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}