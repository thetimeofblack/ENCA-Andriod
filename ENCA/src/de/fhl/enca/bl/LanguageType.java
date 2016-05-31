package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Enum LanguageType
 * Three languages are supported: English, German and Chinese.
 * LanguageType enum provides consistent and type-safe marking 
 * of language preferences. It is used globally in this application.
 */
public enum LanguageType {
	ENGLISH("English", Locale.ENGLISH, 0), GERMAN("Deutsch", Locale.GERMAN, 1), CHINESE("ÖÐÎÄ", Locale.CHINESE, 2);

	private String name;
	private Locale locale;
	private int id;

	/**
	 * An Integer to LanguageType map that allows searching LanguageType
	 * using an integer.
	 */
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

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.name;
	}
}