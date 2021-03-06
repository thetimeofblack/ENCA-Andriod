package de.fhl.enca.bl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulate strings in different languages into single object and operations for them.</br>
 * Language is based on enum LanguageType.</br>
 * Also responsible for string boundary check.
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
	 * Will also do boundary check and replace characters which could cause exception.
	 * @param keyword
	 * @return the relevance, whose value indicates the count that the keyword occurs in the content
	 */
	public int search(String keyword) {
		int relevance = 0;
		for (char c : new char[] { '*', '(', ')', '[', ']', '{', '}' , '.'}) {
			keyword = keyword.replaceAll("\\" + c, "\\\\" + c);
		}
		for (String string : stringMap.values()) {
			if (string != null && !string.equals("")) {
				if (string.equalsIgnoreCase(keyword)) {
					relevance += 10;
				} else {
					String[] subKeywords = keyword.split("\\p{Blank}|-|,|;");
					for (String subKeyword : subKeywords) {
						if (string != null && string.toLowerCase().matches(".*" + subKeyword.toLowerCase() + ".*")) {
							relevance++;
						}
					}
				}
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

	/**
	 * Get the sql command string of the content.</br>
	 * If the content is null, then "NULL" will be returned.</br>
	 * Will also replace the apostrophe (') to double ('').
	 */
	public String getSQLString(LanguageType type) {
		String result = getString(type);
		if (result != null && !result.equals("")) {
			return "'" + result.replaceAll("\\'", "\\'\\'") + "'";
		} else {
			return "NULL";
		}
	}
}