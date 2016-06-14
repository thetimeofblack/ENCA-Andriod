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

	public static void initialize() {
		initCleaningAgents();
		initTags();
		initRelations();
	}

	public static void initializeConcurrently() {
		new Thread(() -> initialize()).start();
	}

	/**
	 * Initialize all cleaning agents and store them into the memory
	 */
	private static void initCleaningAgents() {
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
				builder.getResult();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize all tags and store them into the memory
	 */
	private static void initTags() {
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
	private static void initRelations() {
		/* Key: a cleaning agent, value: the set of related tags of the cleaning agent */
		Map<CleaningAgent, Set<Tag>> ctMap = new HashMap<>();
		/* Key: a tag, value: the set of related cleaning agents of the tag */
		Map<Tag, Set<CleaningAgent>> tcMap = new HashMap<>();
		initTCRelations(ctMap, tcMap);
		initTTRelations(ctMap);
	}

	/**
	 * Initialize the relations between cleaning agents and tags
	 */
	private static void initTCRelations(Map<CleaningAgent, Set<Tag>> ctMap, Map<Tag, Set<CleaningAgent>> tcMap) {
		ResultSet r = SQLVisitor.visitRelations();
		try {
			while (r.next()) {
				CleaningAgent cleaningAgent = CleaningAgent.getCleaningAgent(r.getInt(1));
				Tag tag = Tag.getTag(r.getInt(2));
				/* Add the cleaning agent and its tags set to ctMap */
				if (!ctMap.containsKey(cleaningAgent)) {
					ctMap.put(cleaningAgent, cleaningAgent.getTags());
				}
				/* Add the tag and its cleaning agents set to tcMap */
				if (!tcMap.containsKey(tag)) {
					tcMap.put(tag, tag.getCleaningAgents());
				}
				/* Add the tag into the tags set of the cleaning agent */
				ctMap.get(cleaningAgent).add(tag);
				/* Add the cleaning agent into the cleaning agents set of the tag */
				tcMap.get(tag).add(cleaningAgent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the relations between tags
	 */
	private static void initTTRelations(Map<CleaningAgent, Set<Tag>> ctMap) {
		/* Go through every single tags set */
		for (Set<Tag> group : ctMap.values()) {
			/* The following two layers of loop is to build the relation of every two tags in a tags set */
			for (Tag tag1 : group) {
				for (Tag tag2 : group) {
					/* Ensure the two tags are not with the same tagType*/
					if (tag1.getTagType() != tag2.getTagType()) {
						tag1.getTagsRelated().add(tag2);
					}
				}
			}
		}
	}

	/**
	 * Generate InternationalString according to ResultSet object and column number, 
	 * allows reusing replicate codes.
	 */
	private static InternationalString iStringGenerator(ResultSet r, int i) {
		InternationalString iString = new InternationalString();
		try {
			iString.setString(LanguageType.ENGLISH, r.getString(i));
			iString.setString(LanguageType.CHINESE, r.getString(i + 1));
			iString.setString(LanguageType.GERMAN, r.getString(i + 2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return iString;
	}

	/**
	 * Initialize memos.
	 */
	private static void initMemos() {
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