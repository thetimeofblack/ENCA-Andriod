package de.fhl.enca.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.InternationalString;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.bl.TagType;
import de.fhl.enca.dao.SQLVisitor;

/**
 * Contain the operations of initialization.</br>
 * During the initialization, all of tags and cleaning agents will be read into memory.</br>
 * The relations between cleaning agents and tags and the relations between tags will be realized.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class Initialize {

	/**
	 * Do all the initialization.
	 */
	public static void initialize() {
		initCleaningAgents();
		initTags();
		initRelations();
	}

	/**
	 * Initialize all cleaning agents and store them into the memory.</br>
	 * Will also generate and set max id.
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
				cleaningAgent.setRate(r.getInt(13));
				cleaningAgent.setBelongsToSystem(r.getBoolean(14));
				cleaningAgent.setMainLanguage(LanguageType.getLanguageType(r.getInt(15)));
				cleaningAgent.setMemo(r.getString(17));
				CleaningAgent.addCleaningAgent(cleaningAgent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize all tags and store them into the memory.</br>
	 * 	Will also generate and set max id.
	 */
	private static void initTags() {
		ResultSet r = SQLVisitor.visitTagsAll();
		try {
			while (r.next()) {
				int id = r.getInt(1);
				Tag.addTag(new Tag(id, iStringGenerator(r, 2), TagType.getTagType(r.getInt(5)), r.getBoolean(6)));
				if (id > Tag.getMaxID()) {
					Tag.setMaxID(id);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the relations between cleaning agents and tags and the relations between tags.</br>
	 * Will also add 'no tag' tag to those cleaning agents without tags.
	 * @see CleaningAgentOperator
	 */
	private static void initRelations() {
		ResultSet r = SQLVisitor.visitRelations();
		try {
			while (r.next()) {
				CleaningAgent cleaningAgent = CleaningAgent.getCleaningAgent(r.getInt(1));
				Tag tag = Tag.getTag(r.getInt(2));
				cleaningAgent.addTag(tag);
				tag.addCleaningAgent(cleaningAgent);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentsAll()) {
			if (cleaningAgent.getTags().isEmpty()) {
				Tag.getTag(0).addCleaningAgent(cleaningAgent);
			} else {
				CleaningAgentOperator.attachTTRelation(cleaningAgent.getTags());
			}
		}
	}

	/**
	 * Generate InternationalString according to ResultSet object and column number.</br>
	 * Generation is based on the current line and three String data in three columns starting at the index.</br>
	 * Designed for reusing replicate codes.
	 * @param r the ResultSet source
	 * @param i the first column index
	 * @return the generated InternationalString instance
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