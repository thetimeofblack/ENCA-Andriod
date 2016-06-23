package com.enca.controller;


import com.enca.bl.CleaningAgent;
import com.enca.bl.CleaningAgentType;
import com.enca.bl.InternationalString;
import com.enca.bl.LanguageType;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class CleaningAgentBuilder
 * The class helps building a cleaning agent,
 * applying Builder design pattern
 */
public final class CleaningAgentBuilder {

	private CleaningAgent cleaningAgent = new CleaningAgent();

	public void setID(int id) {
		cleaningAgent.setCleaningAgentID(id);
	}

	public void setName(InternationalString name) {
		cleaningAgent.setName(name);
	}

	public void setDescription(InternationalString description) {
		cleaningAgent.setDescription(description);
	}

	public void setInstruction(InternationalString instruction) {
		cleaningAgent.setInstruction(instruction);
	}

	public void setApplicationTime(long time) {
		cleaningAgent.setApplicationTime(time);
	}

	public void setFrequency(long f) {
		cleaningAgent.setFrequency(f);
	}

	public void setType(String type) {
		cleaningAgent.setAgentType(CleaningAgentType.getCleaningAgentType(type));
	}

	public void setRate(int rate) {
		cleaningAgent.setRate(rate);
	}

	public void setMainLanguage(int index) {
		cleaningAgent.setMainLanguage(LanguageType.getLanguageType(index));
	}

	/**
	 * Return the cleaning agent just built,
	 * will also store this result.
	 */
	public CleaningAgent getResult() {
		CleaningAgent.addCleaningAgent(cleaningAgent);
		return cleaningAgent;
	}
}