package de.fhl.enca.bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bobby
 * @version 31.05.2016
 * 
 * Class User
 * This class contains metadata for user
 * It can be stored and read out of a file.
 */
public final class User {

	/**
	 * @author Bobby
	 * @version 31.05.2016
	 * 
	 * Class UserCarrier
	 * This class is designed to realize generating an object that carries
	 * user data and can be serialized.
	 */
	private static final class UserCarrier implements Serializable {

		private static final long serialVersionUID = 6837137300470719827L;

		private boolean isFirstUse;
		private String name;
		private Date regDate;
		private LanguageType interfaceLanguage;
		private LanguageType contentLanguage;

		public UserCarrier(boolean isFirstUse, String name, Date regDate, LanguageType interfaceLanguage, LanguageType contentLanguage) {
			this.isFirstUse = isFirstUse;
			this.name = name;
			this.regDate = regDate;
			this.interfaceLanguage = interfaceLanguage;
			this.contentLanguage = contentLanguage;
		}

		public boolean isFirstUse() {
			return isFirstUse;
		}

		public String getName() {
			return name;
		}

		public Date getRegDate() {
			return regDate;
		}

		public LanguageType getInterfaceLanguage() {
			return interfaceLanguage;
		}

		public LanguageType getContentLanguage() {
			return contentLanguage;
		}
	}

	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
	private static final LanguageType DefaultLanguage = LanguageType.ENGLISH;

	private static File directory;
	private static File file;

	private static boolean isFirstUse = true;
	private static String name = null;
	private static Date regDate = null;
	private static LanguageType interfaceLanguage = DefaultLanguage;
	private static LanguageType contentLanguage = DefaultLanguage;

	/* initialization */
	static {
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
	public static void readCarrier(UserCarrier carrier) {
		isFirstUse = carrier.isFirstUse();
		name = carrier.getName();
		regDate = carrier.getRegDate();
		interfaceLanguage = carrier.getInterfaceLanguage();
		contentLanguage = carrier.getContentLanguage();
	}

	public static UserCarrier getCarrier() {
		return new UserCarrier(isFirstUse, name, regDate, interfaceLanguage, contentLanguage);
	}

	/**
	 * Read user data from a serialized carrier
	 */
	public static void readUser() {
		try {
			ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(file));
			readCarrier((UserCarrier) oStream.readObject());
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
			oStream.writeObject(getCarrier());
			oStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void firstTimeInitialize() {
		setFirstUse(true);
		setName(null);
		setRegDate(null);
		setInterfaceLanguage(DefaultLanguage);
		setContentLanguage(DefaultLanguage);
		writeUser();
	}

	public static String getDateString() {
		return FORMAT.format(regDate);
	}

	/* getters and setters */
	public static void setFirstUse(boolean isFirstUse) {
		User.isFirstUse = isFirstUse;
	}

	public static boolean isFirstUse() {
		return isFirstUse;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		User.name = name;
	}

	public static Date getRegDate() {
		return regDate;
	}

	public static void setRegDate(Date regDate) {
		User.regDate = regDate;
	}

	public static LanguageType getInterfaceLanguage() {
		return interfaceLanguage;
	}

	public static void setInterfaceLanguage(LanguageType interfaceLanguage) {
		User.interfaceLanguage = interfaceLanguage;
	}

	public static LanguageType getContentLanguage() {
		return contentLanguage;
	}

	public static void setContentLanguage(LanguageType contentLanguage) {
		User.contentLanguage = contentLanguage;
	}
}