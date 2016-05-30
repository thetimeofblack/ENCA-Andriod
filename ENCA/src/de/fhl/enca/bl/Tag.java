package de.fhl.enca.bl;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Tag {

	public enum tagType_t {
		room, item, other
	}

	/* const member */
	private static final Map<tagType_t, Color> ColorMap = new HashMap<tagType_t, Color>() {

		private static final long serialVersionUID = 5237368492012644125L;
		{
			/* Remains to be decided */
		}
	};

	private static final Map<tagType_t, String> DescriptionMap = new HashMap<tagType_t, String>() {

		private static final long serialVersionUID = -6613820012277115380L;
		{
			/* Remains to be decided */
		}
	};

	/* static member */
	private static Map<Integer, Tag> tagsAll = new HashMap<Integer, Tag>();
	private static int tagCount;

	/* non-static member */
	private int tagID;
	private InternationalString name;
	private tagType_t tagType;

	private Set<Integer> cleaningAgents = new HashSet<Integer>();
	private Set<Integer> tagsRelated = new HashSet<Integer>();

	/* static method */
	public static Tag getTag(int ID) {
		return tagsAll.get(ID);
	}

	/* non-static method */
	public Tag(int tagID, String[] strings, String tagType) {
		this.tagID = tagID;
		this.name = new InternationalString(strings);
		this.tagType = tagType_t.valueOf(tagType);
		tagsAll.put(tagID, this);
	}

	public void addCleaningAgent(int id) {
		cleaningAgents.add(id);
	}

	public void addRelatedTag(int id) {
		tagsRelated.add(id);
	}

	/* getters and setters */
	public static Map<Integer, Tag> getTagsAll() {
		return tagsAll;
	}

	public static int getTagCount() {
		return tagCount;
	}

	public static void setTagCount(int tagCount) {
		Tag.tagCount = tagCount;
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

	public tagType_t getTagType() {
		return tagType;
	}

	public void setTagType(tagType_t tagType) {
		this.tagType = tagType;
	}
}