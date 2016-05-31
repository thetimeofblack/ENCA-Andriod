package de.fhl.enca.controller;

import java.awt.Toolkit;
import javax.swing.JFrame;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.CleaningAgentType;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class CleaningAgentBuilder
 * The class helps building a cleaning agent,
 * applying Builder design pattern
 */
public final class CleaningAgentBuilder {

	private static final Toolkit TOOLKIT = new JFrame().getToolkit();

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

	public void setImage(byte[] source) {
		cleaningAgent.setImage(TOOLKIT.createImage(source));
	}

	/**
	 * Return the cleaning agent just built,
	 * will also store this result.
	 */
	public CleaningAgent getResult() {
		CleaningAgent.getCleaningAgentAll().put(cleaningAgent.getCleaningAgentID(), cleaningAgent);
		return cleaningAgent;
	}
}