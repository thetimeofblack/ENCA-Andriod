package de.fhl.enca.bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class LanguagePreference
 * This class contains metadata for language preferences.
 * It can be stored and read out of a file.
 */
public final class LanguagePreference {

	/**
	 * @author Bobby
	 * @version 31.05.2016
	 * 
	 * Class LanguagePreferenceCarrier
	 * This class is designed to realize generating an object that carries
	 * language preference data and can be serialized.
	 */
	private static final class LanguagePreferenceCarrier implements Serializable {

		private static final long serialVersionUID = -7118590040292976226L;

		private LanguageType interfaceLanguage = DefaultLanguage;
		private LanguageType contentlanguage = DefaultLanguage;

		public LanguagePreferenceCarrier(LanguageType interfaceLanguage, LanguageType contentlanguage) {
			this.interfaceLanguage = interfaceLanguage;
			this.contentlanguage = contentlanguage;
		}

		/* getters */
		public LanguageType getInterfaceLanguage() {
			return interfaceLanguage;
		}

		public LanguageType getContentlanguage() {
			return contentlanguage;
		}
	}

	private static File fileLocation = null;

	static {
		try {
			fileLocation = new File(LanguagePreference.class.getResource("/user/language").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static final LanguageType DefaultLanguage = LanguageType.ENGLISH;

	private static LanguageType interfaceLanguage = DefaultLanguage;

	private static LanguageType contentlanguage = DefaultLanguage;

	/* initialization */
	static {
		readLanguagePreference();
	}

	/* static method */
	public static void readCarrier(LanguagePreferenceCarrier carrier) {
		interfaceLanguage = carrier.getInterfaceLanguage();
		contentlanguage = carrier.getContentlanguage();
	}

	public static LanguagePreferenceCarrier getCarrier() {
		return new LanguagePreferenceCarrier(interfaceLanguage, contentlanguage);
	}

	/**
	 * Read language preference from a serialized carrier
	 */
	public static void readLanguagePreference() {
		ObjectInputStream oStream = null;
		try {
			oStream = new ObjectInputStream(new FileInputStream(fileLocation));
			readCarrier((LanguagePreferenceCarrier) oStream.readObject());
			oStream.close();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serialize the carrier and write it to the file
	 */
	public static void writeLanguagePreference() {
		ObjectOutputStream oStream = null;
		try {
			oStream = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oStream.writeObject(getCarrier());
			oStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {
		setInterfaceLanguage(DefaultLanguage);
		setContentlanguage(DefaultLanguage);
		writeLanguagePreference();
	}

	/* getters and setters */
	public static LanguageType getInterfaceLanguage() {
		return interfaceLanguage;
	}

	public static void setInterfaceLanguage(LanguageType interfaceLanguage) {
		LanguagePreference.interfaceLanguage = interfaceLanguage;
	}

	public static LanguageType getContentlanguage() {
		return contentlanguage;
	}

	public static void setContentlanguage(LanguageType contentlanguage) {
		LanguagePreference.contentlanguage = contentlanguage;
	}
}