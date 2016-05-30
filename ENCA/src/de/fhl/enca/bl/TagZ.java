package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author David
 * @version 2016-05-30
 * Class Tag
 * Tag, an element that marks the property of a certain cleaning agent.
 * Smallest fragment in a tagging structure. Tags are classified as three
 * categories, however they are not in hierarchical manner.
 */
public class TagZ {
	/*
	 * Static tagsAll, contains all available tags during run time, created 
	 * during initialization.
	 */
	private static Map<Integer, TagZ> tagsAll = new HashMap<Integer, TagZ>();
	
	private int tagID;
	private InternationalString name;
	private TagType tagType;
	private Map<Integer, CleaningAgent> cleaningAgents;
	private Map<Integer, TagZ>tagsRelated;
	
	/*
	 * Getters
	 */
	static void setTagsAll(Map<Integer, TagZ> tagsAll) {
		TagZ.tagsAll = tagsAll;
	}
	public static Map<Integer, TagZ> getTagsAll() {
		return tagsAll;
	}
	public int getTagID() {
		return tagID;
	}
	public InternationalString getName() {
		return name;
	}
	public TagType getTagType() {
		return tagType;
	}
	public Map<Integer, CleaningAgent> getCleaningAgents() {
		return cleaningAgents;
	}
	public Map<Integer, TagZ> getTagsRelated() {
		return tagsRelated;
	}
	
	/*
	 * Setters
	 */
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	public void setName(InternationalString name) {
		this.name = name;
	}

	public void setTagType(TagType tagType) {
		this.tagType = tagType;
	}

	public void setCleaningAgents(Map<Integer, CleaningAgent> cleaningAgents) {
		this.cleaningAgents = cleaningAgents;
	}

	public void setTagsRelated(Map<Integer, TagZ> tagsRelated) {
		this.tagsRelated = tagsRelated;
	}
}