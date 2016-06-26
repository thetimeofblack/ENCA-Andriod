package de.fhl.enca.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
	public static Set<Tag> fetchTagsAllOfCertainType(TagType type) {
		return fetchTagsOfCertainType(Tag.getTagsAll(), type);
	}

	/**
	 * Fetch from those tags related to all of tags given according to the given tagType
	 */
	public static Set<Tag> fetchTagsRelated(Set<Tag> tags) {
		Set<Tag> result = null;
		for (Tag tag : tags) {
			if (result == null) {
				/* Put all of the related tags of the first tag into the result */
				result = tag.getTagsRelated();
			} else {
				Set<Tag> tempSet = new LinkedHashSet<>();
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
	public static Set<Tag> fetchTagsOfCertainType(Set<Tag> source, TagType type) {
		Set<Tag> result = new LinkedHashSet<>();
		for (Tag tag : source) {
			if (tag.getTagType() == type)
				result.add(tag);
		}
		return result;
	}

	/**
	 * Sort Set of Tags before provided to other classes.
	 */
	public static Set<Tag> fetchSortedTags(Set<Tag> source) {
		List<Tag> tempTagList = new ArrayList<>(source);
		tempTagList.sort((Tag o1, Tag o2) -> o1.getTagType().getId() - o2.getTagType().getId());
		return new LinkedHashSet<Tag>(tempTagList);
	}
}