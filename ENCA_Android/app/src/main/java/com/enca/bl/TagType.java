package com.enca.bl;

/**
 * @author David
 * @version 2016-05-30
 * 
 * Enum TagType
 * Tags are classified in to three distinctive categories which are: ROOM,
 * ITEM and OTHERS. TagType enum provides consistent and type-safe marking 
 * of tag type. It is used globally in this application.
 */
public enum TagType {
	ROOM("Room", 0), ITEM("Items", 1), OTHERS("Others", 2);

	/**
	 * name: String representation of a type
	 */
	private String name;
	private int id;

	public static TagType getTagType(String string) {
		for (TagType tagType : TagType.values()) {
			if (tagType.name.equals(string)) {
				return tagType;
			}
		}
		return null;
	}

	private TagType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.name;
	}
}