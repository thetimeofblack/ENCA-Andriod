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

	public static void writeMemo(int cleaningAgentID, String memo) {
		Connector.executeNonSelect("update CleaningAgents set memo='" + memo + "' where cleaningAgentID=" + cleaningAgentID);
	}

	public static void writeImage(int cleaningAgentID, byte[] content) {
		Connector.executeImage("update CleaningAgents set image=? where cleaningAgentID=" + cleaningAgentID, content);
	}

	public static void createTCRelation(int cleaningAgentID, int tagID) {
		Connector.executeNonSelect("insert into TC values (" + cleaningAgentID + "," + tagID + ")");
	}

	public static void removeTCRelation(int cleaningAgentID, int tagID) {
		Connector.executeNonSelect("delete from TC where cleaningAgentID=" + cleaningAgentID + " and tagID=" + tagID);
	}

	public static void modifyCleaningAgent(CleaningAgent cleaningAgent) {
		Connector.executeNonSelect("update CleaningAgents set cleaningAgentID=" + cleaningAgent.getCleaningAgentID() + ",nameEn='" + cleaningAgent.getName().getString(LanguageType.ENGLISH) + "',nameCn='" + cleaningAgent.getName().getString(LanguageType.CHINESE) + "',nameDe='" + cleaningAgent.getName().getString(LanguageType.GERMAN) + "',descriptionEn=" + cleaningAgent.getDescription().getSQLString(LanguageType.ENGLISH) + ",descriptionCn=" + cleaningAgent.getDescription().getSQLString(LanguageType.CHINESE) + ",descriptionDe=" + cleaningAgent.getDescription().getSQLString(LanguageType.GERMAN) + ",instructionEn='" + cleaningAgent.getInstruction().getString(LanguageType.ENGLISH) + "',instructionCn='" + cleaningAgent.getInstruction().getString(LanguageType.CHINESE) + "',instructionDe='" + cleaningAgent.getInstruction().getString(LanguageType.GERMAN) + "',applicationTime=" + cleaningAgent.getApplicationTime() + ",frequency=" + cleaningAgent.getFrequency() + ",rate=" + cleaningAgent.getRate() + ",cleaningAgentType="+(cleaningAgent.BelongsToSystem()?1:0) + ",mainLanguage=" + cleaningAgent.getMainLanguage().getId() + ",Memo='" + cleaningAgent.getMemo() + "' where cleaningAgentID=" + cleaningAgent.getCleaningAgentID());
		//Connector.executeNonSelect("update CleaningAgents set application");
	}

	public static void createCleaningAgent(CleaningAgent cleaningAgent) {
		Connector.executeNonSelect("insert into CleaningAgents (cleaningAgentID,nameEn,nameCn,nameDe,descriptionEn,descriptionCn,descriptionDe,instructionEn,instructionCn,instructionDe)" + "VALUES (" + cleaningAgent.getCleaningAgentID() + ",'" + cleaningAgent.getName().getString(LanguageType.ENGLISH) + "','" + cleaningAgent.getName().getString(LanguageType.CHINESE) + "','" + cleaningAgent.getName().getString(LanguageType.GERMAN) + "'," + cleaningAgent.getDescription().getSQLString(LanguageType.ENGLISH) + "," + cleaningAgent.getDescription().getSQLString(LanguageType.CHINESE) + "," + cleaningAgent.getDescription().getSQLString(LanguageType.GERMAN) + ",'" + cleaningAgent.getInstruction().getString(LanguageType.ENGLISH) + "','" + cleaningAgent.getInstruction().getString(LanguageType.CHINESE) + "','" + cleaningAgent.getInstruction().getString(LanguageType.GERMAN) + "')");
		Connector.executeNonSelect("update CleaningAgents set applicationTime=" + cleaningAgent.getFrequency() + ",frequency=" + cleaningAgent.getFrequency() + ",rate=" + cleaningAgent.getRate() + ",cleaningAgentType=0" + ",mainLanguage=" + cleaningAgent.getMainLanguage().getId() + ",Memo='" + cleaningAgent.getMemo() + "' where cleaningAgentID=" + cleaningAgent.getCleaningAgentID());
	}

	public static void removeCleaningAgent(CleaningAgent cleaningAgent) {
		Connector.executeNonSelect("delete from CleaningAgents where cleaningAgentID=" + cleaningAgent.getCleaningAgentID());
	}

	public static void modifyTag(Tag tag) {
		Connector.executeNonSelect("update Tags set " + "nameEn='" + tag.getName().getString(LanguageType.ENGLISH) + "',nameCn='" + tag.getName().getString(LanguageType.CHINESE) + "',nameDe='" + tag.getName().getString(LanguageType.GERMAN) + "',tagType='" + tag.getTagType().getId() + "',isSystem=0 where tagID=" + tag.getTagID());
	}

	public static void createTag(Tag tag) {
		Connector.executeNonSelect("insert into Tags (tagID,nameEn,nameCn,nameDe,tagType,isSystem)" + "VALUES(" + tag.getTagID() + ",'" + tag.getName().getString(LanguageType.ENGLISH) + "','" + tag.getName().getString(LanguageType.CHINESE) + "','" + tag.getName().getString(LanguageType.GERMAN) + "','" + tag.getTagType().getId() + "',0" + ")");
	}

	public static void removeTag(Tag tag) {
		Connector.executeNonSelect("delete from Tags where tagID=" + tag.getTagID());
	}
}