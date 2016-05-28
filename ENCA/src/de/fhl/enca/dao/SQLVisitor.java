package de.fhl.enca.dao;

import java.sql.ResultSet;

public final class SQLVisitor {
	public static ResultSet visitCleaningAgentsAll(){
		return Connector.executeSelect("select * from CleaningAgents");
	}
}