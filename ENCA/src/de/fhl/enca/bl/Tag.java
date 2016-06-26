package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.controller.TagFetcher;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class Tag
 * Object representing tag
 */
public class Tag {

	/* static member */
	/**
	 * Store the references of all tags
	 */
	private static Map<Integer, Tag> tagsAll = new HashMap<>();

	/* non-static member */
	private int tagID;
	private InternationalString name;
	private TagType tagType;
	private boolean belongsToSystem;

	private Set<CleaningAgent> cleaningAgents = new HashSet<>();
	private Set<Tag> tagsRelated = new HashSet<>();

	/* static method */
	public static Tag getTag(int ID) {
		return tagsAll.get(ID);
	}

	public static void addTag(Tag tag) {
		tagsAll.put(tag.getTagID(), tag);
	}

	public static Set<Tag> getTagsAll() {
		return new LinkedHashSet<Tag>(tagsAll.values());
	}

	/* non-static method */
	public Tag(int tagID, InternationalString name, TagType tagType, boolean belongsToSystem) {
		this.tagID = tagID;
		this.name = name;
		this.tagType = tagType;
		this.belongsToSystem = belongsToSystem;
		/* directly put this tag into tagsAll */
		tagsAll.put(tagID, this);
	}

	@Override
	public String toString() {
		return "Tag[" + tagID + " - " + name.getString(User.getInterfaceLanguage()) + "]\n";
	}

	public void addCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgents.add(cleaningAgent);
	}

	public void addTagRelated(Tag tag) {
		this.tagsRelated.add(tag);
	}

	/* getters and setters */
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

	public boolean belongsToSystem() {
		return belongsToSystem;
	}

	public Set<CleaningAgent> getCleaningAgents() {
		return cleaningAgents;
	}

	public Set<Tag> getTagsRelated() {
		return TagFetcher.fetchSortedTags(tagsRelated);
	}
}