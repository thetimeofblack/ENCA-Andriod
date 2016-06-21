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
	 * Fetch from all tags according to the given tagType
	 */
	public static Set<Tag> fetchTagsAllOfType(TagType type) {
		return fetchTagsOfType(Tag.getTagsAll(), type);
	}

	/**
	 * Fetch from those tags related to all of tags given according to the given tagType
	 */
	public static Set<Tag> fetchTagOfTags(Set<Tag> tags) {
		Set<Tag> result = null;
		for (Tag tag : tags) {
			if (result == null) {
				/* Put all of the related tags of the first tag into the result */
				result = tag.getTagsRelated();
			} else {
				Set<Tag> tempSet = new HashSet<>();
				/* Go through the result, only save those tags which are related to current tag */
				for (Tag relatedTag : result) {
					if (tag.getTagsRelated().contains(relatedTag)) {
						tempSet.add(relatedTag);
					}
				}
				result = tempSet;
			}
		}
		return result;
	}

	/**
	 * Fetch from given tags according to the given tagType
	 */
	public static Set<Tag> fetchTagsOfType(Set<Tag> source, TagType type) {
		Set<Tag> result = new HashSet<>();
		for (Tag tag : source) {
			if (tag.getTagType() == type)
				result.add(tag);
		}
		return result;
	}
}
