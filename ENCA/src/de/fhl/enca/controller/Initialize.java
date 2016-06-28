package de.fhl.enca.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
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

	/**
	 * Initialize all cleaning agents and store them into the memory
	 */
	private static void initCleaningAgents() {
		ResultSet r = SQLVisitor.visitCleaningAgentsAll();
		try {
			while (r.next()) {
				int id = r.getInt(1);
				CleaningAgent cleaningAgent = new CleaningAgent(id);
				if (id > CleaningAgent.getMaxID()) {
					CleaningAgent.setMaxID(id);
				}
				cleaningAgent.setName(iStringGenerator(r, 2));
				cleaningAgent.setDescription(iStringGenerator(r, 5));
				cleaningAgent.setInstruction(iStringGenerator(r, 8));
				cleaningAgent.setApplicationTime(r.getLong(11));
				cleaningAgent.setFrequency(r.getLong(12));
				cleaningAgent.setBelongsToSystem(r.getBoolean(13));
				cleaningAgent.setRate(r.getInt(14));
				cleaningAgent.setMainLanguage(LanguageType.getLanguageType(r.getInt(15)));
				cleaningAgent.setMemo(r.getString(17));
				CleaningAgent.addCleaningAgent(cleaningAgent);
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
				int id = r.getInt(1);
				new Tag(id, iStringGenerator(r, 2), TagType.getTagType(r.getString(5)), r.getBoolean(6));
				if (id > Tag.getMaxID()) {
					Tag.setMaxID(id);
				}
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
		Map<CleaningAgent, Set<Tag>> ctMap = new HashMap<>();
		initTCRelations(ctMap);
		initTTRelations(ctMap);
	}

	/**
	 * Initialize the relations between cleaning agents and tags
	 * If a cleaning agent is with on tags, it will be made related to Tag noTag.
	 */
	private static void initTCRelations(Map<CleaningAgent, Set<Tag>> ctMap) {
		ResultSet r = SQLVisitor.visitRelations();
		try {
			while (r.next()) {
				CleaningAgent cleaningAgent = CleaningAgent.getCleaningAgent(r.getInt(1));
				Tag tag = Tag.getTag(r.getInt(2));
				if (!ctMap.containsKey(cleaningAgent)) {
					ctMap.put(cleaningAgent, new HashSet<>());
				}
				cleaningAgent.addTag(tag);
				tag.addCleaningAgent(cleaningAgent);
				ctMap.get(cleaningAgent).add(tag);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentsAll()) {
			if (!ctMap.containsKey(cleaningAgent)) {
				Tag.getTag(0).addCleaningAgent(cleaningAgent);
			}
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
						tag1.addTagRelated(tag2);
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
}