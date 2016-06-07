package console;

import de.fhl.enca.bl.LanguagePreference;
import de.fhl.enca.bl.User;

public final class InitializeStatus {

	public static void main(String[] args) {
		User.initialize();
		LanguagePreference.initialize();
	}

}