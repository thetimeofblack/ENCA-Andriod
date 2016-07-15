package de.fhl.enca.dao;

import java.sql.ResultSet;

/**
 * Execute select SQL operations
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see Connector
 */
public final class SQLVisitor {

	/**
	 * Get all of the cleaning agents from table CleaningAgents
	 */
	public static ResultSet visitCleaningAgentsAll() {
		return Connector.executeSelect("select * from CleaningAgents");
	}

	/**
	 * Get all of the tags from table Tags
	 */
	public static ResultSet visitTagsAll() {
		return Connector.executeSelect("select * from Tags");
	}

	/**
	 * Get all of the tag-cleaning agent relations from table TC
	 */
	public static ResultSet visitRelations() {
		return Connector.executeSelect("select * from TC");
	}

	/**
	 * Get the image of the specific cleaning agent
	 */
	public static ResultSet visitImage(int cleaningAgentID) {
		return Connector.executeSelect("select image from CleaningAgents where cleaningAgentID=" + cleaningAgentID);
	}
}