package de.fhl.enca.dao;

public final class SQLAmender {

	public static void createTCRelation(int cleaningAgentID, int tagID) {
		Connector.executeNonSelect("insert into TC values (" + cleaningAgentID + "," + tagID + ")");
	}

	public static void removeTCRelation(int cleaningAgentID, int tagID) {
		Connector.executeNonSelect("delete from TC where cleaningAgentID=" + cleaningAgentID + " and tagID=" + tagID);
	}
}