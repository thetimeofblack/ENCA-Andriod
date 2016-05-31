package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Map;

public final class InternationalString {

	private Map<LanguageType, String> stringMap = new HashMap<LanguageType, String>(LanguageType.getLanguageCount());

	public InternationalString() {}

	public int search(String keyword) {
		String pattern = "\\p{ASCII}*" + keyword + "\\p{ASCII}*";
		int relevance = 0;
		for (String string : stringMap.values()) {
			if (string.matches(pattern)) {
				relevance++;
			}
		}
		return relevance;
	}

	public void setString(LanguageType lType, String content) {
		stringMap.put(lType, content);
	}

	public String getString(LanguageType lType) {
		return stringMap.get(lType);
	}

	public String getString() {
		return stringMap.get(Language.getContentlanguage());
	}
}