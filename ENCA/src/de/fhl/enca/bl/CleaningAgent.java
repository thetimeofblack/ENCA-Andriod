package de.fhl.enca.bl;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

public final class CleaningAgent {

	public enum cleaningAgent_t {
		System, User
	};

	private static Map<Integer, CleaningAgent> cleaningAgentAll = new HashMap<Integer, CleaningAgent>();

	private int cleaningAgentID;
	private InternationalString name;
	private InternationalString description;
	private InternationalString instruction;
	private long applicationTime;
	private long frequency;
	private int rate;
	private Image image;
	private cleaningAgent_t agentType;

	private Map<Integer, Tag> tags = new HashMap<Integer, Tag>();

	private static CleaningAgent getCleaningAgent(int ID) {
		return cleaningAgentAll.get(ID);
	}

	public CleaningAgent(int cleaningAgentID, InternationalString name, InternationalString description, InternationalString instruction, long applicationTime, long frequency, int rate, Image image, cleaningAgent_t agentType) {
		this.cleaningAgentID = cleaningAgentID;
		this.name = name;
		this.description = description;
		this.instruction = instruction;
		this.applicationTime = applicationTime;
		this.frequency = frequency;
		this.rate = rate;
		this.image = image;
		this.agentType = agentType;
		cleaningAgentAll.put(cleaningAgentID, this);
	}
}