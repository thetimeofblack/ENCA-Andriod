package de.fhl.enca.controller;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.dao.SQLAmender;

/**
 * Contains operations of modifying, creating and deleting tag.</br>
 * Contains both operations of memory and operations of SQL database.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see de.fhl.enca.dao.SQLAmender
 */
public final class TagOperator {

	/**
	 * Modify the given tag.</br>
	 * The new data is already stored in the new tag object.</br>
	 * Will remove the old tag with that id from the map and its related cleaning agents,
	 * and then add new tag to the map and its related cleaning agents.
	 * Then modify the data in the database.</br>
	 * It is the only method needed to be called when modifying a tag.
	 * @param newTag the tag to be modified
	 */
	public static void modifyTag(Tag newTag) {
		Tag oldTag = Tag.getTag(newTag.getTagID());
		for (CleaningAgent cleaningAgent : oldTag.getCleaningAgents()) {
			cleaningAgent.removeTag(oldTag);
			cleaningAgent.addTag(newTag);
		}
		Tag.removeTag(oldTag);
		Tag.addTag(newTag);
		SQLAmender.modifyTag(newTag);
	}

	/**
	 * Create the given tag.</br>
	 * Add the new tag to the map.
	 * Will also write the data to the database.</br>
	 * It is the only method needed to be called when creating a tag.
	 * @param newTag the tag to be created
	 */
	public static void createTag(Tag newTag) {
		Tag.addTag(newTag);
		SQLAmender.createTag(newTag);
	}

	/**
	 * Remove the given tag.</br>
	 * Remove the old tag from the map.
	 * Will also delete the data from the database.</br>
	 * It is the only method needed to be called when removing a tag.
	 * @param oldTag the tag to be removed
	 */
	public static void removeTag(Tag oldTag) {
		killTag(oldTag);
		SQLAmender.removeTag(oldTag);
	}

	/**
	 * Kill the given tag.</br>
	 * Remove the tag from its related cleaning agents and related tags.
	 * Then remove itself from the map.</br>
	 * It is used in method removeTag().
	 * @param oldTag the tag to be killed
	 */
	private static void killTag(Tag oldTag) {
		for (CleaningAgent cleaningAgent : oldTag.getCleaningAgents()) {
			cleaningAgent.removeTag(oldTag);
			SQLAmender.removeTCRelation(cleaningAgent.getCleaningAgentID(), oldTag.getTagID());
		}
		for (Tag relatedTag : oldTag.getTagsRelated()) {
			relatedTag.removeTagRelated(oldTag);
		}
		Tag.removeTag(oldTag);
	}
}