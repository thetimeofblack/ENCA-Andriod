package de.fhl.enca.bl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public final class Language {

	private static final LanguageType DefaultLanguage = LanguageType.ENGLISH;

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

	private static LanguageType interfaceLanguage;

	private static LanguageType contentlanguage;

	static {
		try {
			interfaceLanguage = LanguageType.getLanguageType(Integer.valueOf(READER.readLine()));
		} catch (Exception e) {
			e.printStackTrace();
			interfaceLanguage = DefaultLanguage;
		}
		READER.setLineNumber(2);
		try {
			contentlanguage = LanguageType.getLanguageType(Integer.valueOf(READER.readLine()));
		} catch (Exception e) {
			e.printStackTrace();
			contentlanguage = DefaultLanguage;
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
			WRITER.write(interfaceLanguage.toString());
			WRITER.newLine();
			WRITER.write(contentlanguage.toString());
			WRITER.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}