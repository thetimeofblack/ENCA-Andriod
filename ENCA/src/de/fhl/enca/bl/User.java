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

	private static boolean isFirstUse = true;
	private static UserPreference userPreference = new UserPreference();
	private static LanguagePreference languagePreference = new LanguagePreference();

	/* static method */
	/**
	 * Initialize the directory and file location, and read preference
	 */
	public static void initialize() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			directory = new File(System.getProperty("user.home") + "\\Documents\\Enca");
			file = new File(directory, "user.ini");
		}
		else if (System.getProperty("os.name").startsWith("Mac")){
			directory = new File(System.getProperty("user.home") + "/Library/Application Support/Enca");
			file = new File(directory, "user.ini");
		}
		isFirstUse = readUser();
	}

	/**
	 * Read user data from the file
	 * @return whether it is user's first use depending on the existance and integrity of the file
	 */
	private static boolean readUser() {
		if (directory.exists() && file.exists()) {
			try {
				ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(file));
				UserPreference userPreferenceTemp = (UserPreference) oStream.readObject();
				if (userPreferenceTemp != null) {
					userPreference = userPreferenceTemp;
				} else {
					oStream.close();
					return true;
				}
				LanguagePreference languagePreferenceTemp = (LanguagePreference) oStream.readObject();
				if (languagePreferenceTemp != null) {
					languagePreference = languagePreferenceTemp;
				} else {
					oStream.close();
					return true;
				}
				oStream.close();
				return false;
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Write user data to the file and set isFirstUse to false
	 * If the directory of the file does not exist, they will be created.
	 */
	public static void writeUser() {
		if (isFirstUse) {
			if (!directory.exists()) {
				directory.mkdirs();
			}
		}
		try {
			ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(file));
			oStream.writeObject(userPreference);
			oStream.writeObject(languagePreference);
			oStream.close();
			isFirstUse = false;
		} catch (IOException e) {
			e.printStackTrace();
			isFirstUse = true;
		}
	}

	/* getters and setters */
	public static boolean isFirstUse() {
		return isFirstUse;
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