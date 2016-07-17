package de.fhl.enca.bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import javafx.scene.paint.Color;

/**
 * Contain metadata of user, providing saving and reading function of the metadata.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see UserPreference
 * @see LanguagePreference
 * @see TagPreference
 */
public final class User {

	/**
	 * Directory and file settings.
	 */
	static final String WINDOWS_DIR = "\\AppData\\Roaming\\Enca";
	static final String OSX_DIR = "/Library/Application Support/Enca";
	static final String FILE_NAME = "user.ini";

	/**
	 * Represent the directory of save file.
	 */
	private static File directory;

	/**
	 * Represent the save file.
	 */
	private static File file;

	/**
	 * Whether it is user's first use.</br>
	 * Will influence the login interface.
	 */
	private static boolean isFirstUse = true;
	private static UserPreference userPreference = new UserPreference();
	private static LanguagePreference languagePreference = new LanguagePreference();
	private static TagPreference tagPreference = new TagPreference();

	/* static method */
	/**
	 * Initialize the directory and file location, and read preference.</br>
	 * Different operation systems are distinguished.
	 */
	public static void initialize() {
		if (System.getProperty("os.name").startsWith("Windows")) {
			directory = new File(System.getProperty("user.home") + WINDOWS_DIR);
		} else if (System.getProperty("os.name").startsWith("Mac")) {
			directory = new File(System.getProperty("user.home") + OSX_DIR);
		}
		file = new File(directory, FILE_NAME);
		isFirstUse = readUser();
	}

	/**
	 * Read user data from the file.
	 * @return whether it is user's first use depending on the existence and integrity of the file
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
				TagPreference tagPreferenceTemp = (TagPreference) oStream.readObject();
				if (tagPreferenceTemp != null) {
					tagPreference = tagPreferenceTemp;
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
	 * Write user data to the file and set isFirstUse to false.</br>
	 * If the directory of the file does not exist, it will be created.
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
			oStream.writeObject(tagPreference);
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

	public static boolean getPriority() {
		return userPreference.isPriority();
	}

	public static void setPriority(boolean priority) {
		userPreference.setPriority(priority);
	}

	public static Color getTagColor(TagType type) {
		return tagPreference.getColors(type);
	}

	public static InternationalString getTagDescription(TagType type) {
		return tagPreference.getDescripsions(type);
	}
}