package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class Tag
 * Object representing tag
 */
public class Tag implements Searchable{

	/* static member */
	private static Map<Integer, Tag> tagsAll = new HashMap<Integer, Tag>();
	private static int tagCount = 0;

	/* non-static member */
	private int tagID;
	private InternationalString name;
	private TagType tagType;

	private Set<Integer> cleaningAgents = new HashSet<Integer>();
	private Set<Integer> tagsRelated = new HashSet<Integer>();

	/* static method */
	public static Tag getTag(int ID) {
		return tagsAll.get(ID);
	}

	/* non-static method */
	public Tag(int tagID, InternationalString name, TagType tagType) {
		this.tagID = tagID;
		this.name = name;
		this.tagType = tagType;

		/* directly put this tag into tagsAll */
		tagsAll.put(tagID, this);

		/* increase tagCount */
		tagCount++;
	}

	public void addCleaningAgent(int id) {
		cleaningAgents.add(id);
	}

	public void addRelatedTag(int id) {
		tagsRelated.add(id);
	}
	
	@Override
	public int search(String keyword) {
		return name.search(keyword);
	}

	/* getters and setters */
	public static Map<Integer, Tag> getTagsAll() {
		return tagsAll;
	}

	public static int getTagCount() {
		return tagCount;
	}

	public int getTagID() {
		return tagID;
	}

	public void setTagID(int tagID) {
		this.tagID = tagID;
	}

	public InternationalString getName() {
		return name;
	}

	public void setName(InternationalString name) {
		this.name = name;
	}

	public TagType getTagType() {
		return tagType;
	}

	public void setTagType(TagType tagType) {
		this.tagType = tagType;
	}
}