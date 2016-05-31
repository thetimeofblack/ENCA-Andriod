package de.fhl.enca.dao;

import java.sql.ResultSet;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class SQLVisitor
 * Class generates select sql query and sends them
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
}