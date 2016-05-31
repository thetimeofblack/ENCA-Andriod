package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum LanguageType {
	ENGLISH("English", Locale.ENGLISH, 0), GERMAN("Deutsch", Locale.GERMAN, 1), CHINESE("ÖÐÎÄ", Locale.CHINESE, 2);

	String name;
	Locale locale;
	int id;

	private static Map<Integer, LanguageType> LanguageList = new HashMap<Integer, LanguageType>();
	static {
		for (LanguageType lType : LanguageType.values()) {
			LanguageList.put(lType.id, lType);
		}
	}

	public static int getLanguageCount() {
		return LanguageType.values().length;
	}

	public static LanguageType getLanguageType(int id) {
		return LanguageList.get(id);
	}

	private LanguageType(String string, Locale locale, int id) {
		this.name = string;
		this.locale = locale;
		this.id = id;
	}

	public Locale getLocale() {
		return this.locale;
	}

	@Override
	public String toString() {
		return this.name;
	}
}