package de.fhl.enca.dao;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;

public final class SQLAmender {

	public static void writeMemo(int cleaningAgentID, String memo) {
		Connector.executeNonSelect("update CleaningAgents set memo='" + memo + "' where cleaningAgentID=" + cleaningAgentID);
	}

	public static void writeImage(int cleaningAgentID, byte[] content) {

	}

	public static void createTCRelation(int cleaningAgentID, int tagID) {
//		Connector.executeNonSelect("insert into TC values (" + cleaningAgentID + "," + tagID + ")");
	}

	public static void removeTCRelation(int cleaningAgentID, int tagID) {
//		Connector.executeNonSelect("delete from TC where cleaningAgentID=" + cleaningAgentID + " and tagID=" + tagID);
	}

	public static void modifyCleaningAgent(CleaningAgent cleaningAgent) {}

	public static void createCleaningAgent(CleaningAgent cleaningAgent) {}

	public static void removeCleaningAgent(CleaningAgent cleaningAgent) {}

	public static void modifyTag(Tag tag) {}

	public static void createTag(Tag tag) {}

	public static void removeTag(Tag tag) {}
}