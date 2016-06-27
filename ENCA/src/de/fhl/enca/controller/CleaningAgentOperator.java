package de.fhl.enca.controller;

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

	public static void modifyCleaningAgent(CleaningAgent newCleaningAgent) {
		Set<Tag> oldTags = CleaningAgent.getCleaningAgent(newCleaningAgent.getCleaningAgentID()).getTags();
		Set<Tag> newTags = newCleaningAgent.getTags();
		Set<Tag> commonTags = new HashSet<>();
		Set<Tag> removedTags = new HashSet<>();
		Set<Tag> addedTags = new HashSet<>();
		for (Tag oldTag : oldTags) {
			if (newTags.contains(oldTag)) {
				commonTags.add(oldTag);
			} else {
				removedTags.add(oldTag);
			}
		}
		for (Tag newTag : newTags) {
			if (!commonTags.contains(newTag)) {
				addedTags.add(newTag);
			}
		}
		for (Tag addedTag : addedTags) {
			SQLAmender.createTCRelation(newCleaningAgent.getCleaningAgentID(), addedTag.getTagID());
			for (Tag commonTag : commonTags) {
				if (!addedTag.getTagsRelated().contains(commonTag)) {
					addedTag.addTagRelated(commonTag);
					commonTag.addTagRelated(addedTag);
				}
			}
		}
		for (Tag removedTag : removedTags) {
			SQLAmender.removeTCRelation(newCleaningAgent.getCleaningAgentID(), removedTag.getTagID());
			for (Tag commonTag : commonTags) {
				boolean isRelated = false;
				for (CleaningAgent cleaningAgent : removedTag.getCleaningAgents()) {
					if (cleaningAgent.getTags().contains(commonTag)) {
						isRelated = true;
						break;
					}
				}
				if (!isRelated) {
					removedTag.removeTagRelated(commonTag);
					commonTag.removeTagRelated(removedTag);
				}
			}
		}
	}

	public static void createCleaningAgent(CleaningAgent cleaningAgent) {

	}

	public static void saveMemo(CleaningAgent cleaningAgent, String memo) {
		cleaningAgent.setMemo(memo);
		CleaningAgent.refreshCleaningAgentWithMemo(cleaningAgent);
		SQLAmender.writeMemo(cleaningAgent.getCleaningAgentID(), memo);
	}
}