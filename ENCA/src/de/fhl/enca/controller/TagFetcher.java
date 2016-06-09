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

	/**
	 * Get all tags
	 */
	public static Set<Tag> fetchTagsAll() {
		Set<Tag> tempSet = new HashSet<Tag>();
		for (Tag tag : Tag.getTagsAll().values()) {
			tempSet.add(tag);
		}
		return tempSet;
	}

	/**
	 * Get tags of specific type
	 * @param type
	 */
	public static Set<Tag> fetchTagsOfType(TagType type) {
		return fetchTagsOfTypeFrom(fetchTagsAll(), type);
	}

	public static Set<Tag> fetchTagsOfTypeOfTag(Tag tag, TagType type) {
		return fetchTagsOfTypeFrom(tag.getTags(), type);
	}

	public static Set<Tag> fetchTagOfTypeOfTags(Set<Tag> tags, TagType type) {
		Set<Tag> result = new HashSet<Tag>();
		for (Tag tag : tags) {
			for (int tagID : tag.getTagsRelated()) {
				boolean exists = true;
				for (Tag tag2 : tags) {
					if (tag != tag2) {
						if (!tag2.getTagsRelated().contains(tagID)) {
							exists = false;
						}
					}
				}
				if (exists) {
					result.add(Tag.getTag(tagID));
				}
			}
			break;
		}
		return fetchTagsOfTypeFrom(result, type);
	}

	private static Set<Tag> fetchTagsOfTypeFrom(Set<Tag> source, TagType type) {
		Set<Tag> tempSet = new HashSet<Tag>();
		for (Tag tag : source) {
			if (tag.getTagType() == type)
				tempSet.add(tag);
		}
		return tempSet;
	}
}
