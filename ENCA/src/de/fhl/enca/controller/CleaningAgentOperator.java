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
 * @author Bobby
 * @version 26.06.2016
 * 
 * Class CleaningAgentModifier
 * Modify and create cleaning agent
 */
public final class CleaningAgentOperator {

	public static void saveMemo(CleaningAgent cleaningAgent, String memo) {
		cleaningAgent.setMemo(memo);
		CleaningAgent.refreshCleaningAgentWithMemo(cleaningAgent);
		SQLAmender.writeMemo(cleaningAgent.getCleaningAgentID(), memo);
	}

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

	public static void createCleaningAgent(CleaningAgent newCleaningAgent) {
		bearCleaningAgent(newCleaningAgent);
		SQLAmender.createCleaningAgent(newCleaningAgent);
		attachTTRelation(newCleaningAgent.getTags());
	}

	public static void removeCleaningAgent(CleaningAgent oldCleaningAgent) {
		killCleaningAgent(oldCleaningAgent);
		SQLAmender.removeCleaningAgent(oldCleaningAgent);
		detachTTRelation(oldCleaningAgent.getTags(), oldCleaningAgent.getTags());
	}

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