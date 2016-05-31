package de.fhl.enca.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.CleaningAgentBuilder;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.dao.SQLVisitor;

public final class Initialize {

	private static InternationalString iStringGenerator(ResultSet r, int i) {
		InternationalString iString = new InternationalString();
		try {
			iString.setString(LanguageType.ENGLISH, r.getString(i));
			iString.setString(LanguageType.GERMAN, r.getString(i + 1));
			iString.setString(LanguageType.CHINESE, r.getString(i + 2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return iString;
	}

	public static void initCleaningAgents() {
		ResultSet r = SQLVisitor.visitCleaningAgentsAll();
		try {
			while (r.next()) {
				CleaningAgentBuilder builder = new CleaningAgentBuilder();
				builder.setID(r.getInt(1));
				builder.setName(iStringGenerator(r, 2));
				builder.setName(iStringGenerator(r, 5));
				builder.setName(iStringGenerator(r, 8));
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
				new Tag(r.getInt(1), iStringGenerator(r, 2), TagType.getTagType(r.getString(5)));
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