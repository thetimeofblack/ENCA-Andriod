package de.fhl.enca.bl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bobby
 * @version 21.06.2016
 * 
 * Class UserPreference
 * Store the basic information of the user.
 */
public final class UserPreference implements Serializable {

	private static final long serialVersionUID = -5000180069279808816L;
	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

	private boolean isFirstUse = true;
	private String name = "";
	private Date regDate = new Date();

	public boolean isFirstUse() {
		return isFirstUse;
	}

	public void setFirstUse(boolean isFirstUse) {
		this.isFirstUse = isFirstUse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegDate() {
		return FORMAT.format(regDate);
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}