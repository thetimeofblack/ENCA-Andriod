package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class Language {

	private static final int DefaultLanguageIndex = 0;
	private static final Map<Integer, Locale> LanguageList = new HashMap<Integer, Locale>() {

		private static final long serialVersionUID = -7841679423247561396L;
		{
			put(0, Locale.ENGLISH);
			put(1, Locale.GERMAN);
			put(2, Locale.CHINESE);
		}
	};

	private static int interfaceLanguageIndex = DefaultLanguageIndex;

	private static int contentlanguageIndex = DefaultLanguageIndex;

	public static void setInterfacelanguage(int index) {
		if (LanguageList.containsKey(index)) {
			interfaceLanguageIndex = index;
		}
	}

	public static void setContentLanguageIndex(int index) {
		if (LanguageList.containsKey(index)) {
			contentlanguageIndex = index;
		}
	}

	public static int getInterfaceLanguageIndex() {
		return interfaceLanguageIndex;
	}

	public static int getContentlanguageIndex() {
		return contentlanguageIndex;
	}

	public static Locale getinterfaceLanguage() {
		return LanguageList.get(interfaceLanguageIndex);
	}

	public static Locale getContentLanguage() {
		return LanguageList.get(contentlanguageIndex);
	}

	public static int getLanguageCount() {
		return LanguageList.size();
	}

	public static Set<Integer> getKeySet() {
		return LanguageList.keySet();
	}
}