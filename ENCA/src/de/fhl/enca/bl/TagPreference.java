package de.fhl.enca.bl;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



/**
 * @author David
 * @version 2016-05-30
 * Class TagPreference
 * This class contains metadata for tags. It can be stored and read out of
 * a file.
 */
public class TagPreference implements Serializable {
	private static final long serialVersionUID = -633570705543638913L;
	
	private static Map<TagType, Color> colors = new HashMap<TagType, Color>();
	static {
		colors.put(TagType.ROOM, Color.getHSBColor(0, 0, 0));
		colors.put(TagType.ITEM, Color.getHSBColor(0, 0, 0));
		colors.put(TagType.OTHERS, Color.getHSBColor(0, 0, 0));
		//Remember to specify colors!
	};
	private static Map<TagType, InternationalString> descripsions = new HashMap<TagType, InternationalString>();
	static {
		InternationalString desROOM = new InternationalString();
		desROOM.setString(LanguageType.CHINESE, "房间");
		desROOM.setString(LanguageType.ENGLISH, "Room");
		desROOM.setString(LanguageType.GERMAN, "Zimmer");
		
		InternationalString desITEM = new InternationalString();
		desITEM.setString(LanguageType.CHINESE, "物品");
		desITEM.setString(LanguageType.ENGLISH, "Item");
		desITEM.setString(LanguageType.GERMAN, "");
		//remember to add!
		
		InternationalString desOTHERS = new InternationalString();
		desOTHERS.setString(LanguageType.CHINESE, "其它");
		desOTHERS.setString(LanguageType.ENGLISH, "Others");
		desOTHERS.setString(LanguageType.GERMAN, "");
		//remember to add!
		
		descripsions.put(TagType.ROOM, desROOM);
		descripsions.put(TagType.ITEM, desITEM);
		descripsions.put(TagType.OTHERS, desOTHERS);
		
	};
	
	/*
	 * Getters
	 */
	public static Map<TagType, Color> getColors() {
		return colors;
	}
	public static Map<TagType, InternationalString> getDescripsions() {
		return descripsions;
	}
	
	/*
	 * Setters
	 */
	public static void setColors(Map<TagType, Color> colors) {
		TagPreference.colors = colors;
	}
	public static void setDescripsions(Map<TagType, InternationalString> descripsions) {
		TagPreference.descripsions = descripsions;
	}
}
