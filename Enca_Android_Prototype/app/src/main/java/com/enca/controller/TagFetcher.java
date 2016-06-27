package com.enca.controller;

import com.enca.bl.Tag;
import com.enca.bl.TagType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 * @author Bobby
 * @version 26.06.2016
 *
 * Class TagFetcher
 * This class contains methods of operating tags
 * which are stored in memory in map Tag.tagsAll.
 */
public final class TagFetcher {

	/**
	 * Fetch from all tags according to the given tagType
	 * @param type the certain TagType
	 */
	public static Set<Tag> fetchTagsAllOfCertainType(TagType type) {
		return fetchTagsOfCertainType(Tag.getTagsAll(), type);
	}

	/**
	 * Fetch from those tags related to all of tags given
	 * @param source the given tags
	 * @return set of tags which are related to all of tags given
	 */
	public static Set<Tag> fetchTagsRelated(Set<Tag> source) {
		Set<Tag> result = null;
		for (Tag tag : source) {
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
	 * @param source the given tags
	 * @param type the certain tagType
	 * @return set of tags from source which are with the given type
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
	 * @return set of tags which have been sorted
	 */
	public static Set<Tag> fetchSortedTags(Set<Tag> source) {
		List<Tag> tempTagList = new ArrayList<>(source);
		Collections.sort(tempTagList, new Comparator<Tag>() {
			@Override
			public int compare(Tag o1, Tag o2) {
				return o1.getTagType().getId() - o2.getTagType().getId();
			}
		});
//		tempTagList.sort((Tag o1, Tag o2) -> o1.getTagType().getId() - o2.getTagType().getId());
		return new LinkedHashSet<Tag>(tempTagList);
	}
}
