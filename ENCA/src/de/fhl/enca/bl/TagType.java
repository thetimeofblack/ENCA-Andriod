package de.fhl.enca.bl;

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
	ROOM("Room"), ITEM("Items"), OTHERS("Others");

	/*
	 * name: String representation of a type
	 */
	String name;

	private TagType(String name) {
		this.name = name;
	}

	public static TagType getTagType(String string) {
		for (TagType tagType : TagType.values()) {
			if (tagType.name.equals(string)) {
				return tagType;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name;

	}
}