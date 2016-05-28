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