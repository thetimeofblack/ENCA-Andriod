package test;

import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;

public final class ConsoleTest {

	public static void main(String[] args) {
		User.initialize();
		Initialize.initialize();
		User.setInterfaceLanguage(LanguageType.ENGLISH);
		User.writeUser();
	}
}