package com.enca.bl;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Representing different languages supported in the software.</br>
 * Currently three languages are supported: English, German and Chinese.</br>
 * Used directly in class InternationalString and class LanguagePreference.
 *
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see InternationalString
 * @see LanguagePreference
 */
public enum LanguageType {
    ENGLISH("English", Locale.ENGLISH, 0), GERMAN("Deutsch", Locale.GERMAN, 1), CHINESE("中文", Locale.SIMPLIFIED_CHINESE, 2);

    private String name;
    private Locale locale;
    private int id;

    /**
     * An Integer to LanguageType map that allows searching LanguageType
     * using an integer.
     */
    private static Map<Integer, LanguageType> LanguageList = new HashMap<>();

    static {
        for (LanguageType lType : LanguageType.values()) {
            LanguageList.put(lType.id, lType);
        }
    }

    public static int getLanguageCount() {
        return LanguageType.values().length;
    }

    public static LanguageType getLanguageType(int id) {
        return LanguageList.get(id);
    }

    LanguageType(String string, Locale locale, int id) {
        this.name = string;
        this.locale = locale;
        this.id = id;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}