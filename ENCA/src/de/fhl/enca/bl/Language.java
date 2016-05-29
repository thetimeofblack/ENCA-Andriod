package de.fhl.enca.bl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
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
	private static final File FILE = new File("res\\user\\user.txt");
	private static final LineNumberReader READER = initReader();

	private static LineNumberReader initReader() {
		try {
			return new LineNumberReader(new FileReader(FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static BufferedWriter WRITER = null;

	private static int interfaceLanguageIndex;

	private static int contentlanguageIndex;

	static {
		try {
			interfaceLanguageIndex = Integer.valueOf(READER.readLine());
		} catch (Exception e) {
			e.printStackTrace();
			interfaceLanguageIndex = DefaultLanguageIndex;
		}
		READER.setLineNumber(2);
		try {
			contentlanguageIndex = Integer.valueOf(READER.readLine());
		} catch (Exception e) {
			e.printStackTrace();
			contentlanguageIndex = DefaultLanguageIndex;
		}
		try {
			READER.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveUser() {
		try {
			WRITER = new BufferedWriter(new FileWriter(FILE, false));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		try {
			WRITER.write(String.valueOf(interfaceLanguageIndex));
			WRITER.newLine();
			WRITER.write(String.valueOf(contentlanguageIndex));
			WRITER.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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