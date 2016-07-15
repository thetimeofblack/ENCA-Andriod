package de.fhl.enca.bl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Store the basic information of the user.
 * @author Zhaowen.Gong
 * @version 30.06.2016
 */
public final class UserPreference implements Serializable {

	private static final long serialVersionUID = -5000180069279808816L;

	/**
	 * Provide different DateForamt for different interface language.
	 */
	private static Map<LanguageType, DateFormat> dateFormatMap = new HashMap<>();
	static {
		dateFormatMap.put(LanguageType.ENGLISH, new SimpleDateFormat("MM/dd/yyyy"));
		dateFormatMap.put(LanguageType.GERMAN, new SimpleDateFormat("dd.MM.yyyy"));
		dateFormatMap.put(LanguageType.CHINESE, new SimpleDateFormat("yyyy年MM月dd日"));
	}

	/**
	 * The Name of the user.
	 */
	private String name = "";

	/**
	 * The date the user uses the software at the first time.</br>
	 * Will be auto generated.
	 */
	private Date regDate = new Date();

	/**
	 * Indicate whether the god mode is activated.
	 */
	private boolean priority = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegDate() {
		return dateFormatMap.get(User.getInterfaceLanguage()).format(regDate);
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public boolean isPriority() {
		return priority;
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}
}