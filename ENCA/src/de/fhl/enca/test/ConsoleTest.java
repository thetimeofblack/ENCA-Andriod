package de.fhl.enca.test;

import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;

public final class ConsoleTest {

	public static void main(String[] args) {
		User.initialize();
		Initialize.initialize();
		for(Tag tag:Tag.getTagsAll()) {
			System.out.println(tag.getName().getString(LanguageType.GERMAN));
		}
	}
}