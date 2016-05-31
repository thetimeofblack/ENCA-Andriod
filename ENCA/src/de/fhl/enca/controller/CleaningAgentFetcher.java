package de.fhl.enca.controller;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import de.fhl.enca.bl.CleaningAgent;

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
	 * Get all cleaning agents
	 */
	public static Set<CleaningAgent> fetchCleaningAgentsAll() {
		Set<CleaningAgent> tempSet = new HashSet<CleaningAgent>();
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentAll().values()) {
			tempSet.add(cleaningAgent);
		}
		return tempSet;
	}

	/**
	 * Search cleaning agents with the given keyword,
	 * the result would be sorted according to the relevance.
	 * @param keyword
	 */
	public static Set<CleaningAgent> searchCleaningAgent(String keyword) {
		Map<CleaningAgent, Integer> tempMap = new HashMap<CleaningAgent, Integer>();
		String[] subKeywords = keyword.split("\\p{Blank}|-");
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentAll().values()) {
			for (String subKeyword : subKeywords) {
				int relevance = cleaningAgent.search(subKeyword);
				if (relevance > 0) {
					tempMap.put(cleaningAgent, relevance);
				}
			}
		}
		Set<Map.Entry<CleaningAgent, Integer>> sortingSet = new TreeSet<Map.Entry<CleaningAgent, Integer>>(new Comparator<Map.Entry<CleaningAgent, Integer>>() {

			@Override
			public int compare(Entry<CleaningAgent, Integer> o1, Entry<CleaningAgent, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}
		});
		sortingSet.addAll(tempMap.entrySet());
		Set<CleaningAgent> tempSet = new LinkedHashSet<CleaningAgent>();
		for (Map.Entry<CleaningAgent, Integer> entry : sortingSet) {
			tempSet.add(entry.getKey());
		}
		return tempSet;
	}
}