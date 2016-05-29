package de.fhl.enca.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.CleaningAgentBuilder;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.dao.SQLVisitor;

public final class Initialize {

	public static void initCleaningAgents() {
		ResultSet r = SQLVisitor.visitCleaningAgentsAll();
		try {
			while (r.next()) {
				CleaningAgentBuilder builder = new CleaningAgentBuilder();
				builder.setID(r.getInt(1));
				builder.setName(new String[] { r.getString(2), r.getString(3), r.getString(4) });
				builder.setDescription(new String[] { r.getString(5), r.getString(6), r.getString(7) });
				builder.setinstruction(new String[] { r.getString(8), r.getString(9), r.getString(10) });
				builder.setApplicationTime(r.getLong(11));
				builder.setFrequency(r.getLong(12));
				builder.setType(r.getString(13));
				builder.setRate(r.getInt(14));
				builder.setMainLanguage(r.getInt(15));
				builder.setImage(r.getBytes(16));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initTags() {
		ResultSet r = SQLVisitor.visitTagsAll();
		try {
			while (r.next()) {
				new Tag(r.getInt(1), new String[] { r.getString(2), r.getString(3), r.getString(4) }, r.getString(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void initRelations() {
		Map<Integer, Set<Integer>> tcMap = new HashMap<Integer, Set<Integer>>();
		ResultSet r = SQLVisitor.visitRelations();
		try {
			while (r.next()) {
				int cleaningAgentID = r.getInt(1);
				int tagID = r.getInt(2);
				if (!tcMap.containsKey(cleaningAgentID)) {
					tcMap.put(cleaningAgentID, new HashSet<Integer>());
				}
				tcMap.get(cleaningAgentID).add(tagID);
				CleaningAgent.getCleaningAgent(cleaningAgentID).addTag(tagID);
				Tag.getTag(tagID).addCleaningAgent(cleaningAgentID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Set<Integer> group : tcMap.values()) {
			for (int id1 : group) {
				for (int id2 : group) {
					if (id1 != id2 && Tag.getTag(id1).getTagType() != Tag.getTag(id2).getTagType()) {
						Tag.getTag(id1).addRelatedTag(id2);
					}
				}
			}
		}
	}
}