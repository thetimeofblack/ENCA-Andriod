package de.fhl.enca.bl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulate strings in different languages into single object and operations for them.</br>
 * Language is based on enum LanguageType.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see LanguageType
 */
public final class InternationalString implements Serializable {

	private static final long serialVersionUID = 6190737407260272144L;

	/**
	 * Map that stores strings in different languages.
	 */
	private Map<LanguageType, String> stringMap = new HashMap<>(LanguageType.getLanguageCount());

	/**
	 * Default constructor.
	 */
	public InternationalString() {}

	/**
	 * Search those strings with given keyword.
	 * @param keyword
	 * @return the relevance, whose value indicates the count that the keyword occurs in the content
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

	/**
	 * Set content to the InternationalString object.
	 * @param type the language the content is in
	 * @param content the content to be set
	 */
	public void setString(LanguageType type, String content) {
		stringMap.put(type, content);
	}

	/**
	 * Get content according to the given language.</br>
	 * If the result is null, content of other language will be given.
	 * @param type the given language
	 */
	public String getString(LanguageType type) {
		String result = stringMap.get(type);
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

	public String getSQLString(LanguageType type) {
		String result = getString(type);
		if (result != null && !result.equals("")) {
			return "'" + result + "'";
		} else {
			return "NULL";
		}
	}
}