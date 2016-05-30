package de.fhl.enca.controller;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.Tag;

public final class TagFetcher {

	public static Set<Tag> fetchTagsAll() {
		Set<Tag> tempSet = new HashSet<Tag>();
		for (Tag tag : Tag.getTagsAll().values()) {
			tempSet.add(tag);
		}
		return tempSet;
	}

	public static Set<Tag> fetchTagsOfType(Tag.tagType_t type) {
		Set<Tag> tempSet = new HashSet<Tag>();
		for (Tag tag : Tag.getTagsAll().values()) {
			if (tag.getTagType() == type)
				tempSet.add(tag);
		}
		return tempSet;
	}
}
