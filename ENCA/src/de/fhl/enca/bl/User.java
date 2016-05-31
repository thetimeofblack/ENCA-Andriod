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

		private static final long serialVersionUID = -7407640364449951106L;

		private String name = null;
		private Date regDate = null;

		public UserCarrier(String name, Date regDate) {
			this.name = name;
			this.regDate = regDate;
		}

		/* getters */
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
			fileLocation = new File(LanguagePreference.class.getClassLoader().getResource("\\user\\user").toURI());
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
		name = carrier.getName();
		regDate = carrier.getRegDate();
	}

	public static UserCarrier getCarrier() {
		return new UserCarrier(name, regDate);
	}

	/**
	 * Read user data from a serialized carrier
	 */
	public static void readUser() {
		ObjectInputStream oStream = null;
		try {
			oStream = new ObjectInputStream(new FileInputStream(fileLocation));
			readCarrier((UserCarrier) oStream.readObject());
			oStream.close();
			isFirstUse = false;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Serialize the carrier and write it to the file
	 */
	public static void writeUser() {
		ObjectOutputStream oStream = null;
		try {
			oStream = new ObjectOutputStream(new FileOutputStream(fileLocation));
			oStream.writeObject(getCarrier());
			oStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDateString() {
		return FORMAT.format(regDate);
	}

	/* getters and setters */
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