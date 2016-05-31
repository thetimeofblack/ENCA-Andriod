package de.fhl.enca.bl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

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
	public static Set<Searchable> searchSearchable(Collection<Searchable> collection, String keyword) {
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
		Set<Map.Entry<Searchable, Integer>> sortingSet = new TreeSet<Map.Entry<Searchable, Integer>>(new Comparator<Map.Entry<Searchable, Integer>>() {

			@Override
			public int compare(Entry<Searchable, Integer> o1, Entry<Searchable, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}
		});
		sortingSet.addAll(tempMap.entrySet());
		Set<Searchable> tempSet = new LinkedHashSet<Searchable>();
		for (Map.Entry<Searchable, Integer> entry : sortingSet) {
			tempSet.add(entry.getKey());
		}
		return tempSet;
	}
}