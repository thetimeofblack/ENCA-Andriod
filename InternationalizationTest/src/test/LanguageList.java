package test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class LanguageList {

	private static Map<Integer, Locale> map = null;
	private static int defaultIndex;

	private static void initialize() {
		map = new HashMap<Integer, Locale>();
		map.put(0, Locale.ENGLISH);
		map.put(1, Locale.CHINESE);
		defaultIndex = 0;
	}

	public static Locale get(int index) {
		if (map == null) {
			initialize();
		}
		return map.get(index);
	}

	public static int getDefaultIndex() {
		if (map == null) {
			initialize();
		}
		return defaultIndex;
	}
}