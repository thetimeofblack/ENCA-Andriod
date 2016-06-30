package de.fhl.enca.gui.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

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