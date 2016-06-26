package com.enca.controller;

import com.enca.bl.CleaningAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Bobby
 * @version 31.05.2016
 *
 *          Class Search Realizing search function
 */
public final class Search {

	/**
	 * Search searchable object with the given keyword, the result would be
	 * sorted according to the relevance.
	 *
	 * @param keyword
	 */
	public static Set<CleaningAgent> search(Set<CleaningAgent> collection, String keyword) {
		Map<CleaningAgent, Integer> tempMap = new HashMap<>();
//        Set<CleaningAgent> tempSet  = new LinkedHashSet<>();
		String[] subKeywords = keyword.toLowerCase().split("\\p{Blank}|-");
		for (CleaningAgent cleaningAgent : collection) {
			int relevance = 0;
			for (String subKeyword : subKeywords) {
				relevance += cleaningAgent.search(subKeyword);
			}
			if (relevance > 0) {
//                tempSet.add(cleaningAgent);
				tempMap.put(cleaningAgent, relevance);
			}
		}

		List<Map.Entry<CleaningAgent, Integer>> sortingList = new ArrayList<>();
		sortingList.addAll(tempMap.entrySet());
//		 sortingList.sort((o1, o2) -> o2.getValue() - o1.getValue());
//
//		sortingList.sort(new Comparator<Map.Entry<CleaningAgent, Integer>>() {
//
//			@Override
//			public int compare(Map.Entry<CleaningAgent, Integer> o1, Map.Entry<CleaningAgent, Integer> o2) {
//				return o2.getValue() - o1.getValue();
//			}
//		});

		Collections.sort(sortingList, new Comparator<Map.Entry<CleaningAgent, Integer>>() {
			@Override
			public int compare(Map.Entry<CleaningAgent, Integer> o1, Map.Entry<CleaningAgent, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		Set<CleaningAgent> tempSet = new LinkedHashSet<>();
		for (Map.Entry<CleaningAgent, Integer> entry : sortingList) {
			tempSet.add(entry.getKey());
		}
		return tempSet;
	}
}