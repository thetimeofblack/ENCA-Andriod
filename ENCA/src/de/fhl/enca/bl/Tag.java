package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.controller.TagFetcher;

/**
 * Object representing tag.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public class Tag {

	/* static member */
	/**
	 * Store the references of all tags.
	 */
	private static Map<Integer, Tag> tagsAll = new HashMap<>();

	/**
	 * 'no tag' tag will be stored.
	 */
	static {
		InternationalString string = new InternationalString();
		string.setString(LanguageType.ENGLISH, "(No Tag)");
		string.setString(LanguageType.GERMAN, "(Keine Markierung)");
		string.setString(LanguageType.CHINESE, "(无标签)");
		tagsAll.put(0, new Tag(0, string, TagType.OTHERS, true));
	}

	/**
	 * Max id stored for creating new tag.
	 */
	private static int maxID = 0;

	/* non-static member */
	private int tagID;
	private InternationalString name;
	private TagType tagType;
	private boolean belongsToSystem;

	/**
	 * Cleaning agents related to self.
	 */
	private Set<CleaningAgent> cleaningAgents = new HashSet<>();

	/**
	 * Tags related to self.
	 */
	private Set<Tag> tagsRelated = new HashSet<>();

	/* static method */
	/**
	 * Get tag by id.
	 */
	public static Tag getTag(int ID) {
		return tagsAll.get(ID);
	}

	/**
	 * Store new tag in the map.
	 * @param tag the tag to be stored
	 */
	public static void addTag(Tag tag) {
		tagsAll.put(tag.getTagID(), tag);
	}

	/**
	 * Remove the given tag from the map.
	 */
	public static void removeTag(Tag tag) {
		tagsAll.remove(tag.getTagID());
	}

	/**
	 * Tag representing having no tag will not be presented.
	 */
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
	}

	/**
	 * Search the tag according to given keyword.
	 * @param keyword
	 * @return integer representing the relevance
	 */
	public int search(String keyword) {
		return name.search(keyword);
	}

	@Override
	public String toString() {
		return name.getString(User.getInterfaceLanguage());
	}

	public void addCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgents.add(cleaningAgent);
	}

	public void removeCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgents.remove(cleaningAgent);
	}

	public void addTagRelated(Tag tag) {
		this.tagsRelated.add(tag);
	}

	public void removeTagRelated(Tag tag) {
		this.tagsRelated.remove(tag);
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

	/**
	 * Order will be sorted before return.
	 */
	public Set<Tag> getTagsRelated() {
		return TagFetcher.fetchSortedTags(tagsRelated);
	}
}