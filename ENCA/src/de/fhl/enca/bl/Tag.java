package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.HashSet;
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
	static {
		InternationalString string = new InternationalString();
		string.setString(LanguageType.ENGLISH, "(No Tag)");
		string.setString(LanguageType.GERMAN, "(Keine Markierung)");
		string.setString(LanguageType.CHINESE, "(无标签)");
		tagsAll.put(0, new Tag(0, string, TagType.OTHERS, true));
	}

	private static int maxID = 0;

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
		Set<Tag> tags = new HashSet<>(tagsAll.values());
		tags.remove(Tag.getTag(0));
		return TagFetcher.fetchSortedTags(tags);
	}

	public static int getMaxID() {
		return maxID;
	}

	public static void setMaxID(int maxID) {
		Tag.maxID = maxID;
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
		return name.getString(User.getInterfaceLanguage());
	}

	public void addCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgents.add(cleaningAgent);
	}

	public void addCleaningAgentsAll(Set<CleaningAgent> cleaningAgents) {
		this.cleaningAgents.addAll(cleaningAgents);
	}

	public void removeCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgents.remove(cleaningAgent);
	}

	public void removeCleaningAgentsAll(Set<CleaningAgent> cleaningAgents) {
		this.cleaningAgents.removeAll(cleaningAgents);
	}

	public void addTagRelated(Tag tag) {
		this.tagsRelated.add(tag);
	}

	public void addTagsRelatedAll(Set<Tag> tags) {
		this.tagsRelated.addAll(tags);
	}

	public void removeTagRelated(Tag tag) {
		this.tagsRelated.remove(tag);
	}

	public void removeTagsRelatedAll(Set<Tag> tags) {
		this.tagsRelated.removeAll(tags);
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