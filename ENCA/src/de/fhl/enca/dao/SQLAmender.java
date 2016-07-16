package de.fhl.enca.dao;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.LanguageType;
import de.fhl.enca.bl.Tag;

/**
 * Execute update, insert into and delete SQL operation
 * @author Zhaowen.Gong, Zeling.Wu
 * @version 30.06.2016
 * @see Connector
 */
public final class SQLAmender {

	/**
	 * Write memo to table CleaningAgents.
	 */
	public static void writeMemo(int cleaningAgentID, String memo) {
		String memoSQL = memo == null || memo.equals("") ? "NULL" : "'" + memo.replaceAll("\\'", "\\'\\'") + "'";
		Connector.executeNonSelect("update CleaningAgents set memo=" + memoSQL + " where cleaningAgentID=" + cleaningAgentID);
	}

	/**
	 * Write image to table Cleaningagents.
	 * @param content the content of the image to be written as array of bytes
	 */
	public static void writeImage(int cleaningAgentID, byte[] content) {
		Connector.executeImage("update CleaningAgents set image=? where cleaningAgentID=" + cleaningAgentID, content);
	}

	/**
	 * Add row into table TC
	 */
	public static void createTCRelation(int cleaningAgentID, int tagID) {
		Connector.executeNonSelect("insert into TC values (" + cleaningAgentID + "," + tagID + ")");
	}

	/**
	 * Delete row from table TC
	 */
	public static void removeTCRelation(int cleaningAgentID, int tagID) {
		Connector.executeNonSelect("delete from TC where cleaningAgentID=" + cleaningAgentID + " and tagID=" + tagID);
	}

	/**
	 * Modify the cleaning agent stored in table CleaningAgents
	 * @param cleaningAgent the cleaning agent to be modified
	 */
	public static void modifyCleaningAgent(CleaningAgent cleaningAgent) {
		Connector.executeNonSelect("update CleaningAgents set cleaningAgentID=" + cleaningAgent.getCleaningAgentID() + ",nameEn=" + cleaningAgent.getName().getSQLString(LanguageType.ENGLISH) + ",nameCn=" + cleaningAgent.getName().getSQLString(LanguageType.CHINESE) + ",nameDe=" + cleaningAgent.getName().getSQLString(LanguageType.GERMAN) + ",descriptionEn=" + cleaningAgent.getDescription().getSQLString(LanguageType.ENGLISH) + ",descriptionCn=" + cleaningAgent.getDescription().getSQLString(LanguageType.CHINESE) + ",descriptionDe=" + cleaningAgent.getDescription().getSQLString(LanguageType.GERMAN) + ",instructionEn=" + cleaningAgent.getInstruction().getSQLString(LanguageType.ENGLISH) + ",instructionCn=" + cleaningAgent.getInstruction().getSQLString(LanguageType.CHINESE) + ",instructionDe=" + cleaningAgent.getInstruction().getSQLString(LanguageType.GERMAN) + ",applicationTime=" + cleaningAgent.getApplicationTime() + ",frequency=" + cleaningAgent.getFrequency() + ",rate=" + cleaningAgent.getRate() + ",cleaningAgentType=" + (cleaningAgent.BelongsToSystem() ? 1 : 0) + ",mainLanguage=" + cleaningAgent.getMainLanguage().getId() + " where cleaningAgentID=" + cleaningAgent.getCleaningAgentID());
		writeMemo(cleaningAgent.getCleaningAgentID(), cleaningAgent.getMemo());
	}

	/**
	 * Create a cleaning agent in table CleaningAgents
	 * @param cleaningAgent the cleaning agent to be created
	 */
	public static void createCleaningAgent(CleaningAgent cleaningAgent) {
		Connector.executeNonSelect("insert into CleaningAgents (cleaningAgentID,nameEn,nameCn,nameDe,descriptionEn,descriptionCn,descriptionDe,instructionEn,instructionCn,instructionDe)" + "VALUES (" + cleaningAgent.getCleaningAgentID() + "," + cleaningAgent.getName().getSQLString(LanguageType.ENGLISH) + "," + cleaningAgent.getName().getSQLString(LanguageType.CHINESE) + "," + cleaningAgent.getName().getSQLString(LanguageType.GERMAN) + "," + cleaningAgent.getDescription().getSQLString(LanguageType.ENGLISH) + "," + cleaningAgent.getDescription().getSQLString(LanguageType.CHINESE) + "," + cleaningAgent.getDescription().getSQLString(LanguageType.GERMAN) + "," + cleaningAgent.getInstruction().getSQLString(LanguageType.ENGLISH) + "," + cleaningAgent.getInstruction().getSQLString(LanguageType.CHINESE) + "," + cleaningAgent.getInstruction().getSQLString(LanguageType.GERMAN) + ")");
		Connector.executeNonSelect("update CleaningAgents set applicationTime=" + cleaningAgent.getFrequency() + ",frequency=" + cleaningAgent.getFrequency() + ",rate=" + cleaningAgent.getRate() + ",cleaningAgentType=0" + ",mainLanguage=" + cleaningAgent.getMainLanguage().getId() + " where cleaningAgentID=" + cleaningAgent.getCleaningAgentID());
		writeMemo(cleaningAgent.getCleaningAgentID(), cleaningAgent.getMemo());
	}

	/**
	 * Delete the cleaning agent from table CleaningAgents
	 * @param cleaningAgent the cleaning agent to be deleted 
	 */
	public static void removeCleaningAgent(CleaningAgent cleaningAgent) {
		Connector.executeNonSelect("delete from CleaningAgents where cleaningAgentID=" + cleaningAgent.getCleaningAgentID());
	}

	/**
	 * Modify the tag in table Tags
	 * @param tag the tag to be modified
	 */
	public static void modifyTag(Tag tag) {
		Connector.executeNonSelect("update Tags set " + "nameEn=" + tag.getName().getSQLString(LanguageType.ENGLISH) + ",nameCn=" + tag.getName().getSQLString(LanguageType.CHINESE) + ",nameDe=" + tag.getName().getSQLString(LanguageType.GERMAN) + ",tagType=" + tag.getTagType().getId() + ",isSystem=" + (tag.belongsToSystem() ? 1 : 0) + " where tagID=" + tag.getTagID());
	}

	/**
	 * Create a tag in table Tags
	 * @param tag the tag to be created
	 */
	public static void createTag(Tag tag) {
		Connector.executeNonSelect("insert into Tags (tagID,nameEn,nameCn,nameDe,tagType,isSystem)" + "VALUES(" + tag.getTagID() + "," + tag.getName().getSQLString(LanguageType.ENGLISH) + "," + tag.getName().getSQLString(LanguageType.CHINESE) + "," + tag.getName().getSQLString(LanguageType.GERMAN) + "," + tag.getTagType().getId() + ",0" + ")");
	}

	/**
	 * Delete the tag from table Tags
	 * @param tag the tag to be deleted
	 */
	public static void removeTag(Tag tag) {
		Connector.executeNonSelect("delete from Tags where tagID=" + tag.getTagID());
	}
}