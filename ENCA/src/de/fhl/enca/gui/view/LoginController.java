package de.fhl.enca.gui.view;

import de.fhl.enca.bl.User;
import de.fhl.enca.gui.utility.Utility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller of Login interface when it is not user's first use.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class LoginController {

	@FXML
	private Label usernameLabel;

	/**
	 * Initialize the interface.</br>
	 * Show username in the interface./<br>
	 * Will be called automatically during the construction of the Stage.
	 */
	@FXML
	private void initialize() {
		usernameLabel.setText(Utility.getResourceBundle().getString("hey") + "  " + User.getName());
	}
}