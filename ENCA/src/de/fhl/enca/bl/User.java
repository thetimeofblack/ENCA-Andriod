package de.fhl.enca.bl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
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

		private static final long serialVersionUID = 9171730458759247340L;

		private boolean isFirstUse = true;
		private String name = null;
		private Date regDate = null;

		public UserCarrier(boolean isFirstUse, String name, Date regDate) {
			super();
			this.isFirstUse = isFirstUse;
			this.name = name;
			this.regDate = regDate;
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
	}

	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private static File fileLocation = null;

	static {
		try {
			fileLocation = new File(LanguagePreference.class.getResource("/user/user").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private static boolean isFirstUse = true;

	private static String name = null;

	private static Date regDate = null;

	/* initialization */
	static {
		readUser();
	}

	/* static method */
	public static void readCarrier(UserCarrier carrier) {
		isFirstUse = carrier.isFirstUse();
		name = carrier.getName();
		regDate = carrier.getRegDate();
	}

	public static UserCarrier getCarrier() {
		return new UserCarrier(isFirstUse, name, regDate);
	}

	/**
	 * Read user data from a serialized carrier
	 */
	public static void readUser() {
		try {
			ObjectInputStream oStream = new ObjectInputStream(new FileInputStream(fileLocation));
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
			ObjectOutputStream oStream = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oStream.writeObject(getCarrier());
			oStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {
		setFirstUse(true);
		setName(null);
		setRegDate(null);
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
}