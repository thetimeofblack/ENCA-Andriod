package de.fhl.enca.controller;

import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.dao.SQLAmender;

public final class TagOperator {

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

	public static void createTag(Tag newTag) {
		Tag.addTag(newTag);
		SQLAmender.createTag(newTag);
	}

	public static void removeTag(Tag oldTag) {
		killTag(oldTag);
		SQLAmender.removeTag(oldTag);
	}

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