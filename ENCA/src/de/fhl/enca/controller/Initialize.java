package de.fhl.enca.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Memo;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.dao.SQLVisitor;

/**
 * @author Bobby
 * @version 01.06.2016
 * 
 * Class Initialize
 * This class contains the operations of initialization.
 * During the initialization, all of tags and cleaning agents
 * will be read into memory. The relations between cleaning agents
 * and tags and the relations between tags will be realize.
 */
public final class Initialize {

	/**
	 * Initialize all cleaning agents and store them into the memory
	 */
	public static void initCleaningAgents() {
		ResultSet r = SQLVisitor.visitCleaningAgentsAll();
		try {
			while (r.next()) {
				CleaningAgentBuilder builder = new CleaningAgentBuilder();
				builder.setID(r.getInt(1));
				builder.setName(iStringGenerator(r, 2));
				builder.setDescription(iStringGenerator(r, 5));
				builder.setInstruction(iStringGenerator(r, 8));
				builder.setApplicationTime(r.getLong(11));
				builder.setFrequency(r.getLong(12));
				builder.setType(r.getString(13));
				builder.setRate(r.getInt(14));
				builder.setMainLanguage(r.getInt(15));
				builder.setImage(r.getBytes(16));
				builder.getResult();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize all tags and store them into the memory
	 */
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

	/**
	 * Initialize the relations between cleaning agents and tags
	 * and the relations between tags
	 */
	public static void initRelations() {
		// <cleaningAgentID, Set<tagID>> CleaningAgent:Tag = 1:*
		Map<Integer, Set<Integer>> ctMap = new HashMap<Integer, Set<Integer>>();
		// <tagID, Set<cleaningAgentID>> Tag:CleaningAgent = 1:*
		Map<Integer, Set<Integer>> tcMap = new HashMap<Integer, Set<Integer>>();
		initTCRelations(ctMap, tcMap);
		initTTRelations(ctMap, tcMap);
	}

	/**
	 * Initialize the relations between cleaning agents and tags
	 */
	private static void initTCRelations(Map<Integer, Set<Integer>> ctMap, Map<Integer, Set<Integer>> tcMap) {
		ResultSet r = SQLVisitor.visitRelations(); // get TC relation records
		try {
			while (r.next()) { // check every line of TC relation
				int cleaningAgentID = r.getInt(1);
				int tagID = r.getInt(2);
				if (!ctMap.containsKey(cleaningAgentID)) { // add CA if never met
					// <cleaningAgentID, the CA's real tag set>
					ctMap.put(cleaningAgentID, CleaningAgent.getCleaningAgent(cleaningAgentID).getTags());
				}
				if (!tcMap.containsKey(tagID)) { // add Tag if never met
					// <tagID, the tag's real CA set>
					tcMap.put(tagID, Tag.getTag(tagID).getCleaningAgents());
				}
				// stick a tag to CA
				ctMap.get(cleaningAgentID).add(tagID);
				// link CA to tag
				tcMap.get(tagID).add(cleaningAgentID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the relations between tags
	 */
	private static void initTTRelations(Map<Integer, Set<Integer>> ctMap, Map<Integer, Set<Integer>> tcMap) {
		for (Set<Integer> group : ctMap.values()) { // iterate all CA tag sets
			for (int id1 : group) { // iterate each tag
				for (int id2 : group) { // take another tag
					if (id1 != id2) { // every two related tags
						if (Tag.getTag(id1).getTagType() != Tag.getTag(id2).getTagType()) {
							// log the relation if tag is of different type
							Tag.getTag(id1).addRelatedTag(id2);
						}
					}
				}
			}
		}
	}

	/**
	 * Generate InternationalString according to ResultSet object and column number, 
	 * allows reusing replicate codes of
	 */
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

	/**
	 * Initialize memos.
	 */
	public static void initMemos() {
		ResultSet r = SQLVisitor.visitMemos();
		try {
			while (r.next()) {
				new Memo(r.getInt(1), CleaningAgent.getCleaningAgent(r.getInt(3)), r.getString(4), r.getDate(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}