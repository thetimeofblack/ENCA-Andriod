package de.fhl.enca.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.dao.SQLVisitor;
import javafx.scene.image.Image;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class CleaningAgentFetcher
 * This class contains methods of operating cleaning agents
 * which are stored in memory in map CleaningAgent.cleaningAgentAll.
 */
public final class CleaningAgentFetcher {

	/**
	 * Fetch cleaning agents related to all of the given tags
	 */
	public static Set<CleaningAgent> fetchCleaningAgentsOfTags(Set<Tag> tags) {
		Set<CleaningAgent> result = null;
		for (Tag tag : tags) {
			if (result == null) {
				/* Put all of the related cleaning agents of the first tag into the result */
				result = tag.getCleaningAgents();
			} else {
				/* Go through the result, only save those cleaning agents which are related to current tag */
				Set<CleaningAgent> tempSet = new HashSet<>();
				for (CleaningAgent cleaningAgent : result) {
					if (cleaningAgent.getTags().contains(tag)) {
						tempSet.add(cleaningAgent);
					}
				}
				result = tempSet;
			}
		}
		return result;
	}

	public static Image fetchImageOfCleaningAgent(int cleaningAgentID) {
		ResultSet resultSet = SQLVisitor.visitImage(cleaningAgentID);
		try {
			if (resultSet.next()) {
				return resultSet.getObject(1, Image.class);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}