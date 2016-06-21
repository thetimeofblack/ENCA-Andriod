package de.fhl.enca.bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class User
 * This class contains metadata for user.
 * It can be stored and read out of a file.
 */
public final class User {

	private static File directory;
	private static File file;

	private static UserPreference userPreference;
	private static LanguagePreference languagePreference;

	/* initialization */
	static {
		/* initialize directory and file location */
		if (System.getProperty("os.name").startsWith("Windows")) {
			directory = new File(System.getProperty("user.home") + "\\Documents\\Enca");
			file = new File(directory, "user.ini");
		}
		if (!directory.exists()) {
			directory.mkdirs();
		}
		if (!file.exists()) {
			firstTimeInitialize();
		}
		readUser();
	}

	/* static method */
	/**
	 * Read user data from a serialized carrier from the file
	 */
	private static void readUser() {
		try {
			ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(file));
			userPreference = (UserPreference) oStream.readObject();
			languagePreference = (LanguagePreference) oStream.readObject();
			oStream.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serialize the carrier and write it to the file
	 */
	public static void writeUser() {
		try {
			ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(file));
			oStream.writeObject(userPreference);
			oStream.writeObject(languagePreference);
			oStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void firstTimeInitialize() {
		userPreference = new UserPreference();
		languagePreference = new LanguagePreference();
		writeUser();
	}

	/* getters and setters */
	public static boolean isFirstUse() {
		return userPreference.isFirstUse();
	}

	public static void setFirstUse(boolean isFirstUse) {
		userPreference.setFirstUse(isFirstUse);
	}

	public static String getName() {
		return userPreference.getName();
	}

	public static void setName(String name) {
		userPreference.setName(name);
	}

	public static String getDateString() {
		return userPreference.getRegDate();
	}

	public static void setRegDate(Date regDate) {
		userPreference.setRegDate(regDate);
	}

	public static LanguageType getInterfaceLanguage() {
		return languagePreference.getInterfaceLanguage();
	}

	public static void setInterfaceLanguage(LanguageType interfaceLanguage) {
		languagePreference.setInterfaceLanguage(interfaceLanguage);
	}

	public static LanguageType getContentLanguage() {
		return languagePreference.getContentLanguage();
	}

	public static void setContentLanguage(LanguageType contentLanguage) {
		languagePreference.setContentLanguage(contentLanguage);
	}
}