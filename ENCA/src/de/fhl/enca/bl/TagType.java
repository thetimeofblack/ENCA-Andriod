package de.fhl.enca.bl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Haoze Zhang
 * @version 2016-05-30
 * 
 * Enum TagType
 * Tags are classified in to three distinctive categories which are: ROOM,
 * ITEM and OTHERS. TagType enum provides consistent and type-safe marking 
 * of tag type. It is used globally in this application.
 */

public enum TagType {

	ROOM("Room", 0), ITEM("Items", 1), OTHERS("Others", 2);

	/* Map: <ID, Type> */
	private static Map<Integer, TagType> tagTypeMap = new HashMap<>();
	static {
		for (TagType tagType : TagType.values()) {
			tagTypeMap.put(tagType.getId(), tagType);
		}
	}

	/**
	 * name: String representation of a type;
	 * id: ID number of a type.
	 */
	private String name;
	private int id;

	private TagType(String name, int id) {
		this.name = name;
		this.id = id;
	}

	/* Getters */
	public static TagType getTagType(int id) {
		return tagTypeMap.get(id);
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.name;
	}
}