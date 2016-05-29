package de.fhl.enca.bl;

import java.awt.Toolkit;
import javax.swing.JFrame;
import de.fhl.enca.bl.CleaningAgent.cleaningAgent_t;

public final class CleaningAgentBuilder {

	private static final Toolkit TOOLKIT = new JFrame().getToolkit();

	private CleaningAgent cleaningAgent = new CleaningAgent();

	public void setID(int id) {
		cleaningAgent.setCleaningAgentID(id);
	}

	public void setName(String[] strings) {
		cleaningAgent.setName(new InternationalString(strings));
	}

	public void setDescription(String[] strings) {
		cleaningAgent.setDescription(new InternationalString(strings));
	}

	public void setinstruction(String[] strings) {
		cleaningAgent.setInstruction(new InternationalString(strings));
	}

	public void setApplicationTime(long time) {
		cleaningAgent.setApplicationTime(time);
	}

	public void setFrequency(long f) {
		cleaningAgent.setFrequency(f);
	}

	public void setType(String type) {
		cleaningAgent.setAgentType(cleaningAgent_t.valueOf(type));
	}

	public void setRate(int rate) {
		cleaningAgent.setRate(rate);
	}

	public void setMainLanguage(int index) {
		cleaningAgent.setMainLanguage(index);
	}

	public void setImage(byte[] source) {
		cleaningAgent.setImage(TOOLKIT.createImage(source));
	}

	public CleaningAgent getResult() {
		CleaningAgent.addCleaningAgent(cleaningAgent);
		return cleaningAgent;
	}
}