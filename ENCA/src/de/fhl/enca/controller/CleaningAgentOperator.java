package de.fhl.enca.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.dao.SQLAmender;

/**
 * Contain operations of modifying, creating and deleting cleaning agent.</br>
 * Contain both operations of memory and operations of SQL database.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see de.fhl.enca.dao.SQLAmender
 */
public final class CleaningAgentOperator {

	/**
	 * Save memo for the given cleaning agent.
	 * @param cleaningAgent the given cleaning agent
	 * @param memo the memo to be saved
	 */
	public static void saveMemo(CleaningAgent cleaningAgent, String memo) {
		cleaningAgent.setMemo(memo);
		CleaningAgent.refreshCleaningAgentWithMemo(cleaningAgent);
		SQLAmender.writeMemo(cleaningAgent.getCleaningAgentID(), memo);
	}

	/**
	 * Save image for the given cleaning agent.
	 * @param cleaningAgent the given cleaning agent
	 * @param file the chosen local file in which the image is stored
	 */
	public static void saveImage(CleaningAgent cleaningAgent, File file) {
		byte[] content = new byte[65536];
		try {
			FileInputStream stream = new FileInputStream(file);
			stream.read(content);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SQLAmender.writeImage(cleaningAgent.getCleaningAgentID(), content);
	}

	/**
	 * Modify the given cleaning agent.</br>
	 * The new data is already stored in the new cleaning agent object.</br>
	 * Will remove the old cleaning agent with that id from the map and its related tags,
	 * and then add new cleaning agent to the map and its related tags.
	 * Then modify the data in the database.</br>
	 * The relations between tags influenced by this modification will also be modified.</br>
	 * It is the only method needed to be called when modifying a cleaning agent.
	 * @param newCleaningAgent the cleaning agent to be modified
	 */
	public static void modifyCleaningAgent(CleaningAgent newCleaningAgent) {
		Set<Tag> oldTags = CleaningAgent.getCleaningAgent(newCleaningAgent.getCleaningAgentID()).getTags();
		Set<Tag> newTags = newCleaningAgent.getTags();
		Set<Tag> commonTags = new HashSet<>();
		Set<Tag> removedTags = new HashSet<>();
		for (Tag oldTag : oldTags) {
			if (newTags.contains(oldTag)) {
				commonTags.add(oldTag);
			} else {
				removedTags.add(oldTag);
			}
		}
		killCleaningAgent(CleaningAgent.getCleaningAgent(newCleaningAgent.getCleaningAgentID()));
		bearCleaningAgent(newCleaningAgent);
		SQLAmender.modifyCleaningAgent(newCleaningAgent);
		detachTTRelation(removedTags, oldTags);
		attachTTRelation(newTags);
	}

	/**
	 * Create the given cleaning agent.</br>
	 * Add the new cleaning agent to the map and then add relations between the new cleaning agent and
	 * its related tags and relations among its related tags.
	 * Will also write the data to the database.</br>
	 * It is the only method needed to be called when creating a cleaning agent.
	 * @param newCleaningAgent the cleaning agent to be created
	 */
	public static void createCleaningAgent(CleaningAgent newCleaningAgent) {
		bearCleaningAgent(newCleaningAgent);
		SQLAmender.createCleaningAgent(newCleaningAgent);
		attachTTRelation(newCleaningAgent.getTags());
	}

	/**
	 * Remove the given cleaning agent.</br>
	 * Remove the old cleaning agent from the map and then remove the relations between the old cleaning agent and
	 * its related tags and relations among its related tags.
	 * Will also delete the data from the database.</br>
	 * It is the only method needed to be called when removing a cleaning agent.
	 * @param oldCleaningAgent the cleaning agent to be removed.
	 */
	public static void removeCleaningAgent(CleaningAgent oldCleaningAgent) {
		killCleaningAgent(oldCleaningAgent);
		SQLAmender.removeCleaningAgent(oldCleaningAgent);
		detachTTRelation(oldCleaningAgent.getTags(), oldCleaningAgent.getTags());
	}

	/**
	 * Bear the given cleaning agent.</br>
	 * Store the cleaning agent in the map and create relations between the cleaning agent and its related tags.
	 * Will also write the relation to the database.</br>
	 * If the cleaning agent has no related tags, then it will be related to 'no tag' tag.</br>
	 * It is used in method modifyCleaningAgent() and createCleaningAgent().
	 * @param newCleaningAgent the cleaning agent to be born
	 */
	private static void bearCleaningAgent(CleaningAgent newCleaningAgent) {
		CleaningAgent.addCleaningAgent(newCleaningAgent);
		if (!newCleaningAgent.getTags().isEmpty()) {
			for (Tag tag : newCleaningAgent.getTags()) {
				tag.addCleaningAgent(newCleaningAgent);
				SQLAmender.createTCRelation(newCleaningAgent.getCleaningAgentID(), tag.getTagID());
			}
		} else {
			Tag.getTag(0).addCleaningAgent(newCleaningAgent);
		}
	}

	/**
	 * Kill the given cleaning agent.</br>
	 * Remove the cleaning agent from the map and remove relationship between the cleaning agent and its related tags.
	 * Will also delete the relation from the database.</br>
	 * If the cleaning agent has no related tags, then it will be removed from related cleaning agents of 'no tag' tag.</br>
	 * It is used in method modifyCleaningAgent() and removeCleaningAgent().
	 * @param oldCleaningAgent the cleaning agent to be killed
	 */
	private static void killCleaningAgent(CleaningAgent oldCleaningAgent) {
		if (!oldCleaningAgent.getTags().isEmpty()) {
			for (Tag tag : oldCleaningAgent.getTags()) {
				tag.removeCleaningAgent(oldCleaningAgent);
				SQLAmender.removeTCRelation(oldCleaningAgent.getCleaningAgentID(), tag.getTagID());
			}
		} else {
			Tag.getTag(0).removeCleaningAgent(oldCleaningAgent);
		}
		CleaningAgent.removeCleaningAgent(oldCleaningAgent);
	}

	/**
	 * Attach relations between tags.</br>
	 * Will create relations between every two different tags.</br>
	 * It is used in method modifyCleaningAgent() and createCleaningAgent(), and also class Initialize.
	 * @param attachingTags tags to be attached relations
	 * @see Initialize
	 */
	public static void attachTTRelation(Set<Tag> attachingTags) {
		for (Tag attachingTag1 : attachingTags) {
			for (Tag attachingTag2 : attachingTags) {
				if (attachingTag1 != attachingTag2) {
					if (!attachingTag1.getTagsRelated().contains(attachingTag2)) {
						attachingTag1.addTagRelated(attachingTag2);
						attachingTag2.addTagRelated(attachingTag1);
					}
				}
			}
		}
	}

	/**
	 * Detach relations between tags.</br>
	 * Will remove relations between a detaching tag and a detached tag
	 * which do not have common cleaning agents anymore.</br>
	 * It is used in method modifyCleaningAgent() and removeCleaningAgent().
	 * @param detachingTags related tags removed from a cleaning agent
	 * @param detachedTags related tags of the removed cleaning agent
	 */
	private static void detachTTRelation(Set<Tag> detachingTags, Set<Tag> detachedTags) {
		for (Tag detachingTag : detachingTags) {
			for (Tag detachedTag : detachedTags) {
				if (detachingTag != detachedTag) {
					boolean isRelated = false;
					for (CleaningAgent cleaningAgent : detachingTag.getCleaningAgents()) {
						if (cleaningAgent.getTags().contains(detachedTag)) {
							isRelated = true;
							break;
						}
					}
					if (!isRelated) {
						detachingTag.removeTagRelated(detachedTag);
						detachedTag.removeTagRelated(detachingTag);
					}
				}
			}
		}
	}
}