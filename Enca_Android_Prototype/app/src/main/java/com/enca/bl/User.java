package com.enca.bl;

import android.os.Environment;

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

		public UserCarrier() {
			this.isFirstUse = User.isFirstUse;
			this.name = User.name;
			this.regDate = User.regDate;
			this.interfaceLanguage = User.interfaceLanguage;
			this.contentLanguage = User.contentLanguage;
		}
		
		public void applyCarrier() {
//			User.isFirstUse=this.isFirstUse;
			User.name=this.name;
			User.regDate=this.regDate;
			User.interfaceLanguage=this.interfaceLanguage;
			User.contentLanguage=this.contentLanguage;
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
		/* initialize directory and file location */
//		if (System.getProperty("os.name").startsWith("Windows")) {
//			directory = new File(System.getProperty("user.home") + "\\Documents\\Enca");
//			file = new File(directory, "user.ini");
//		}
		directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"Enca");
		file = new File(directory, "user.txt");
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
	public static void readUser() {
		try {
			ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(file));
			((UserCarrier) oStream.readObject()).applyCarrier();
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
			oStream.writeObject(new UserCarrier());
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