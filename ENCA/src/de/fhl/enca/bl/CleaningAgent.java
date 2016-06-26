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
 * Class CleaningAgent
 * Object representing cleaning agent
 */
public final class CleaningAgent {

	/* static member */
	/**
	 * Store the references of all cleaning agents
	 */
	private static Map<Integer, CleaningAgent> cleaningAgentAll = new HashMap<>();
	private static int maxID = 0;

	/* non-static member */
	private int cleaningAgentID;
	private InternationalString name;
	private InternationalString description;
	private InternationalString instruction;
	private long applicationTime;
	private long frequency;
	private CleaningAgentType agentType;
	private int rate;
	private LanguageType mainLanguage;

	private Set<Tag> tags = new HashSet<>();

	/* static method */
	public static CleaningAgent getCleaningAgent(int ID) {
		return cleaningAgentAll.get(ID);
	}

	public static void addCleaningAgent(CleaningAgent cleaningAgent) {
		cleaningAgentAll.put(cleaningAgent.getCleaningAgentID(), cleaningAgent);
	}

	public static Set<CleaningAgent> getCleaningAgentsAll() {
		return new HashSet<CleaningAgent>(cleaningAgentAll.values());
	}

	public static int getMaxID() {
		return maxID;
	}

	public static void setMaxID(int maxID) {
		CleaningAgent.maxID = maxID;
	}

	/* non-static method */
	/**
	 * search the cleaning agent according to given keyword
	 * @param keyword
	 * @return integer representing the relevance
	 */
	public int search(String keyword) {
		return name.search(keyword) + description.search(keyword) + instruction.search(keyword);
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);
	}

	public void addTagsAll(Tag tag) {
		this.tags.addAll(tags);
	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);
	}

	public void removeTagsAll(Set<Tag> tags) {
		this.tags.removeAll(tags);
	}

	@Override
	public String toString() {
		return "CA[" + cleaningAgentID + " - " + name.getString(User.getInterfaceLanguage()) + "]\n";
	}

	/* getters and setters */
	public int getCleaningAgentID() {
		return cleaningAgentID;
	}

	public void setCleaningAgentID(int cleaningAgentID) {
		this.cleaningAgentID = cleaningAgentID;
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

	public void setFrequency(long frequency) {
		this.frequency = frequency;
	}

	public CleaningAgentType getAgentType() {
		return agentType;
	}

	public void setAgentType(CleaningAgentType agentType) {
		this.agentType = agentType;
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

	public Set<Tag> getTags() {
		return TagFetcher.fetchSortedTags(tags);
	}
}