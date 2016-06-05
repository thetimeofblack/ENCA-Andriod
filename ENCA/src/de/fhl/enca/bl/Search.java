package de.fhl.enca.bl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class Search
 * Realizing search function
 */
public final class Search {

	/**
	 * Search searchable object with the given keyword,
	 * the result would be sorted according to the relevance.
	 * @param keyword
	 */
	public static Set<Searchable> search(Collection<Searchable> collection, String keyword) {
		Map<Searchable, Integer> tempMap = new HashMap<Searchable, Integer>();
		String[] subKeywords = keyword.split("\\p{Blank}|-");
		for (Searchable Searchable : collection) {
			int relevance = 0;
			for (String subKeyword : subKeywords) {
				relevance += Searchable.search(subKeyword);
			}
			if (relevance > 0) {
				tempMap.put(Searchable, relevance);
			}
		}
		List<Map.Entry<Searchable, Integer>> sortingList = new ArrayList<Map.Entry<Searchable, Integer>>();
		sortingList.addAll(tempMap.entrySet());
		sortingList.sort((o1, o2) -> o2.getValue() - o1.getValue());
		Set<Searchable> tempSet = new LinkedHashSet<Searchable>();
		for (Map.Entry<Searchable, Integer> entry : sortingList) {
			tempSet.add(entry.getKey());
		}
		return tempSet;
	}
}