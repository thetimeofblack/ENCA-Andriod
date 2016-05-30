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

public final class CleaningAgentFetcher {

	public static Set<CleaningAgent> fetchCleaningAgentsAll() {
		Set<CleaningAgent> tempSet = new HashSet<CleaningAgent>();
		for (CleaningAgent cleaningAgent : CleaningAgent.getCleaningAgentAll().values()) {
			tempSet.add(cleaningAgent);
		}
		return tempSet;
	}

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