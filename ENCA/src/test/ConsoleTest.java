package test;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.LanguagePreference;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.bl.User;
import de.fhl.enca.controller.Initialize;
import de.fhl.enca.controller.TagFetcher;

public final class ConsoleTest {

	public static void main(String[] args) {
		User.initialize();
		LanguagePreference.initialize();
		Initialize.initialize();
		Set<Tag> set=new HashSet<>();
		set.add(Tag.getTag(1));
		set.add(Tag.getTag(7));
		System.out.println(TagFetcher.fetchTagOfTypeOfTags(set, TagType.OTHERS));
	}
}