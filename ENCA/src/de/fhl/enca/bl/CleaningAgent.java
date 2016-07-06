package de.fhl.enca.bl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.controller.TagFetcher;

/**
 * Object representing cleaning agent.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class CleaningAgent {

	/* static member */
	/**
	 * Store the references of all cleaning agents.
	 */
	private static Map<Integer, CleaningAgent> cleaningAgentAll = new LinkedHashMap<>();

	/**
	 * Store the references of those cleaning agents with memo.
	 */
	private static Set<CleaningAgent> cleaningAgentsWithMemo = new LinkedHashSet<>();

	/**
	 * Max id stored for creating new cleaning agent.
	 */
	private static int maxID = 0;

	/* non-static member */
	private int cleaningAgentID;
	private InternationalString name;
	private InternationalString description;
	private InternationalString instruction;
	private long applicationTime;
	private long frequency;
	private boolean belongsToSystem;
	private int rate = 5;
	private LanguageType mainLanguage;
	private String memo;

	/**
	 * Tags related to self.
	 */
	private Set<Tag> tags = new HashSet<>();

	/* static method */
	/**
	 * Get cleaning agent by id.
	 */
	public static CleaningAgent getCleaningAgent(int ID) {
		return cleaningAgentAll.get(ID);
	}

	/**
	 * Store new cleaning agent in the map.
	 * @param cleaningAgent the cleaning agent to be added
	 */
	public static void addCleaningAgent(CleaningAgent cleaningAgent) {
		cleaningAgentAll.put(cleaningAgent.getCleaningAgentID(), cleaningAgent);
		refreshCleaningAgentWithMemo(cleaningAgent);

	}

	/**
	 * Check the given cleaning agent, determine whether it should be included in set cleaningAgentsWithMemo.
	 * @param cleaningAgent the cleaning agent to be checked
	 */
	public static void refreshCleaningAgentWithMemo(CleaningAgent cleaningAgent) {
		if (cleaningAgent.getMemo() != null && !cleaningAgent.getMemo().equals("")) {
			if (!cleaningAgentsWithMemo.contains(cleaningAgent)) {
				cleaningAgentsWithMemo.add(cleaningAgent);
			}
		} else {
			if (cleaningAgentsWithMemo.contains(cleaningAgent)) {
				cleaningAgentsWithMemo.remove(cleaningAgent);
			}
		}
	}

	/**
	 * Remove the given cleaning agent from the map.
	 * @param cleaningAgent the cleaning agent to be removed
	 */
	public static void removeCleaningAgent(CleaningAgent cleaningAgent) {
		if (cleaningAgentAll.containsKey(cleaningAgent.getCleaningAgentID())) {
			cleaningAgentAll.remove(cleaningAgent.getCleaningAgentID());
		}
		refreshCleaningAgentWithMemo(cleaningAgent);
	}

	public static Set<CleaningAgent> getCleaningAgentsAll() {
		return new HashSet<CleaningAgent>(cleaningAgentAll.values());
	}

	public static Set<CleaningAgent> getCleaningAgentsWithMemo() {
		return new HashSet<CleaningAgent>(cleaningAgentsWithMemo);
	}

	public static int getMaxID() {
		return maxID;
	}

	public static void setMaxID(int maxID) {
		CleaningAgent.maxID = maxID;
	}

	/* non-static method */
	/**
	 * Constructor.</br>
	 * Only id is needed, other properties will be assigned using setters.
	 * @param id the id of the cleaning agent
	 */
	public CleaningAgent(int id) {
		this.cleaningAgentID = id;
	}

	/**
	 * Search the cleaning agent according to given keyword.
	 * @param keyword the given keyword
	 * @return integer representing the relevance
	 */
	public int search(String keyword) {
		int tagRelevance = 0;
		for (Tag tag : tags) {
			tagRelevance += tag.search(keyword);
		}
		return name.search(keyword) + description.search(keyword) + instruction.search(keyword) + 2 * tagRelevance;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public void addTagsAll(Set<Tag> tags) {
		this.tags.addAll(tags);
	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);
	}

	public void removeTagsAll(Set<Tag> tags) {
		this.tags.removeAll(tags);
	}

	/**
	 * Show the name in interface language.
	 */
	@Override
	public String toString() {
		return "CA[" + cleaningAgentID + " - " + name.getString(User.getInterfaceLanguage()) + "]\n";
	}

	/* getters and setters */
	public int getCleaningAgentID() {
		return cleaningAgentID;
	}

	public InternationalString getName() {
		return name;
	}

	public void setName(InternationalString name) {
		this.name = name;
	}

	public InternationalString getDescription() {
		return description;
	}

	public void setDescription(InternationalString description) {
		this.description = description;
	}

	public InternationalString getInstruction() {
		return instruction;
	}

	public void setInstruction(InternationalString instruction) {
		this.instruction = instruction;
	}

	public long getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(long applicationTime) {
		this.applicationTime = applicationTime;
	}

	public long getFrequency() {
		return frequency;
	}

	public boolean BelongsToSystem() {
		return belongsToSystem;
	}

	public void setBelongsToSystem(boolean belongsToSystem) {
		this.belongsToSystem = belongsToSystem;
	}

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public LanguageType getMainLanguage() {
		return mainLanguage;
	}

	public void setMainLanguage(LanguageType lang) {
		this.mainLanguage = lang;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * Order will be sorted before return.
	 */
	public Set<Tag> getTags() {
		return TagFetcher.fetchSortedTags(tags);
	}
}