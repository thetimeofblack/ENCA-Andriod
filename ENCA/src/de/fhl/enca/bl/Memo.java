package de.fhl.enca.bl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bobby
 * @version 01.06.2016
 * 
 * Class Memo
 * Object representing memo
 */
public final class Memo {

	/* static member */
	private static Map<Integer, Memo> memosAll = new HashMap<Integer, Memo>();

	/* non-static member */
	private int memoID;
	private CleaningAgent cleaningAgent;
	private String content;
	private Date date;

	/* static method */
	public static Memo getMemo(int ID) {
		return memosAll.get(ID);
	}

	/* non-static method */
	public Memo(int memoID, CleaningAgent cleaningAgent, String content, Date date) {
		this.memoID = memoID;
		this.cleaningAgent = cleaningAgent;
		this.content = content;
		this.date = date;
		memosAll.put(memoID, this);
	}

	public int search(String keyword) {
		return content.matches("\\p{ASCII}*" + keyword + "\\p{ASCII}*") ? 1 : 0;
	}

	/* getters and setters */
	public int getMemoID() {
		return memoID;
	}

	public void setMemoID(int memoID) {
		this.memoID = memoID;
	}

	public CleaningAgent getCleaningAgent() {
		return cleaningAgent;
	}

	public void setCleaningAgent(CleaningAgent cleaningAgent) {
		this.cleaningAgent = cleaningAgent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}