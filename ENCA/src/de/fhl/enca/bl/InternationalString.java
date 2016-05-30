package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Map;

public final class InternationalString {

	private Map<Integer, String> stringMap = new HashMap<Integer, String>(Language.getLanguageCount());

	public InternationalString(String[] strings) {
		for (int index : Language.getKeySet()) {
			stringMap.put(index, strings[index]);
		}
	}

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

	/* getters and setters */
	public String getPreferredString() {
		return stringMap.get(Language.getContentlanguageIndex());
	}

	public String getSpecificString(int index) {
		return stringMap.get(index);
	}

	public void setString(int index, String string) {
		if (Language.getKeySet().contains(index)) {
			stringMap.put(index, string);
		}
	}
}