package de.fhl.enca.controller;

import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class TagFetcher
 * This class contains methods of operating tags
 * which are stored in memory in map Tag.tagsAll.
 */
public final class TagFetcher {

	public static Set<Tag> fetchTagsAll() {
		Set<Tag> tempSet = new HashSet<Tag>();
		for (Tag tag : Tag.getTagsAll().values()) {
			tempSet.add(tag);
		}
		return tempSet;
	}

	public static Set<Tag> fetchTagsOfType(TagType type) {
		Set<Tag> tempSet = new HashSet<Tag>();
		for (Tag tag : Tag.getTagsAll().values()) {
			if (tag.getTagType() == type)
				tempSet.add(tag);
		}
		return tempSet;
	}
}
