package de.fhl.enca.bl;

import java.awt.Image;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class CleaningAgent
 * Object representing cleaning agent
 */
public final class CleaningAgent {

	/* static member */
	private static Map<Integer, CleaningAgent> cleaningAgentAll = new HashMap<Integer, CleaningAgent>();
	private static int agentCount;

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
	private Image image;

	private Set<Integer> tags = new HashSet<Integer>();

	/* static method */
	public static CleaningAgent getCleaningAgent(int ID) {
		return cleaningAgentAll.get(ID);
	}

	/* non-static method */
	public void addTag(int tagID) {
		tags.add(tagID);
	}

	/**
	 * search the cleaning agent according to given keyword
	 * @param keyword
	 * @return integer representing the relevance
	 */
	public int search(String keyword) {
		return name.search(keyword) + description.search(keyword) + instruction.search(keyword);
	}

	/* getters and setters */
	public static Map<Integer, CleaningAgent> getCleaningAgentAll() {
		return cleaningAgentAll;
	}

	public static int getAgentCount() {
		return agentCount;
	}

	public static void setAgentCount(int agentCount) {
		CleaningAgent.agentCount = agentCount;
	}

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Set<Integer> getTags() {
		return tags;
	}
}