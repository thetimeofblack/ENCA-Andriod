package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class InternationalString
 * This class is designed to encapsulate strings in different languages into single object.
 */
public final class InternationalString {

	/**
	 * Map that stores strings in different languages
	 */
	private Map<LanguageType, String> stringMap = new HashMap<>(LanguageType.getLanguageCount());

	public InternationalString() {}

	/**
	 * Search those strings with given keyword
	 * @param keyword
	 * @return the relevance, whose value indicates the count that the keyword occurs in the content.
	 */
	public int search(String keyword) {
		int relevance = 0;
		for (String string : stringMap.values()) {
			if (string != null && string.toLowerCase().matches(".*" + keyword.toLowerCase() + ".*")) {
				relevance++;
			}
		}
		return relevance;
	}

	public void setString(LanguageType lType, String content) {
		stringMap.put(lType, content);
	}

	public String getString(LanguageType lType) {
		String result = stringMap.get(lType);
		if (result != null && !result.equals("")) {
			return result;
		} else {
			for (String string : stringMap.values()) {
				if (string != null && !string.equals("")) {
					return string;
				}
			}
			return null;
		}
	}
}