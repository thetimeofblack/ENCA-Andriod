package de.fhl.enca.controller;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;

/**
 * Contain methods of fetching tags.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class TagFetcher {

	/**
	 * Fetch from all tags according to the given tagType.
	 * @param type the certain TagType
	 * @return tags which are with the given type
	 */
	public static Set<Tag> fetchTagsAllOfCertainType(TagType type) {
		return fetchTagsOfCertainType(Tag.getTagsAll(), type);
	}

	/**
	 * Fetch from those tags related to all of tags given.
	 * @param source the given tags
	 * @return tags which are related to all of tags given
	 */
	public static Set<Tag> fetchTagsRelated(Set<Tag> source) {
		Set<Tag> result = null;
		for (Tag tag : source) {
			if (result == null) {
				result = tag.getTagsRelated();
			} else {
				Set<Tag> tempSet = new LinkedHashSet<>();
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
	 * Fetch from given tags according to the given tagType.
	 * @param source the given tags
	 * @param type the certain tagType
	 * @return tags from source which are with the given type
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
	 * @param source set of tags to be sorted
	 * @return tags which have been sorted
	 */
	public static Set<Tag> fetchSortedTags(Set<Tag> source) {
		List<Tag> tempTagList = new ArrayList<>(source);
		tempTagList.sort((Tag o1, Tag o2) -> o1.getTagType().getId() - o2.getTagType().getId());
		return new LinkedHashSet<Tag>(tempTagList);
	}
}