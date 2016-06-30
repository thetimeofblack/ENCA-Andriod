package de.fhl.enca.controller;

import java.io.ByteArrayInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import de.fhl.enca.bl.CleaningAgent;
import de.fhl.enca.bl.Tag;
import de.fhl.enca.dao.SQLVisitor;
import javafx.scene.image.Image;

/**
 * Contains methods of fetching cleaning agents.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class CleaningAgentFetcher {

	/**
	 * Fetch cleaning agents related to all of the given tags.
	 * @param tags the given set of tags
	 * @return set of cleaning agents which are related to all of the given tags
	 */
	public static Set<CleaningAgent> fetchCleaningAgentsOfTags(Set<Tag> tags) {
		Set<CleaningAgent> result = null;
		for (Tag tag : tags) {
			if (result == null) {
				result = tag.getCleaningAgents();
			} else {
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

	/**
	 * Search cleaning agents with the given keyword.</br>
	 * The result would be sorted according to the relevance.
	 * @param source given cleaning agents
	 * @param keyword the given keyword
	 * @return the search result containing cleaning agents with positive relevance
	 */
	public static Set<CleaningAgent> fetchResult(Set<CleaningAgent> source, String keyword) {
		Map<CleaningAgent, Integer> tempMap = new HashMap<>();
		String[] subKeywords = keyword.split("\\p{Blank}|-");
		for (CleaningAgent cleaningAgent : source) {
			int relevance = 0;
			for (String subKeyword : subKeywords) {
				relevance += cleaningAgent.search(subKeyword);
			}
			if (relevance > 0) {
				tempMap.put(cleaningAgent, relevance);
			}
		}
		List<Entry<CleaningAgent, Integer>> sortingList = new ArrayList<>(tempMap.entrySet());
		sortingList.sort((o1, o2) -> o2.getValue() - o1.getValue());
		Set<CleaningAgent> tempSet = new LinkedHashSet<>();
		for (Map.Entry<CleaningAgent, Integer> entry : sortingList) {
			tempSet.add(entry.getKey());
		}
		return tempSet;
	}

	/**
	 * Get the image of the given cleaning agent.</br>
	 * Class Image is within the framework of JavaFX.
	 * @param cleaningAgent the given cleaning agent
	 * @return the image of the cleaning agent
	 */
	public static Image fetchImageOfCleaningAgent(CleaningAgent cleaningAgent) {
		ResultSet resultSet = SQLVisitor.visitImage(cleaningAgent.getCleaningAgentID());
		try {
			if (resultSet.next()) {
				return new Image(new ByteArrayInputStream(resultSet.getBytes(1)));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}