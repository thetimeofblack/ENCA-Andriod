package de.fhl.enca.dao;

import java.sql.ResultSet;

/**
 * Execute select SQL operations
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see Connector
 */
public final class SQLVisitor {

	public static ResultSet visitCleaningAgentsAll() {
		return Connector.executeSelect("select * from CleaningAgents");
	}

	public static ResultSet visitTagsAll() {
		return Connector.executeSelect("select * from Tags");
	}

	public static ResultSet visitRelations() {
		return Connector.executeSelect("select * from TC");
	}

	public static ResultSet visitMemos() {
		return Connector.executeSelect("select * from Memos");
	}

	public static ResultSet visitImage(int cleaningAgentID) {
		return Connector.executeSelect("select image from CleaningAgents where cleaningAgentID=" + cleaningAgentID);
	}
}