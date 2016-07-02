package com.enca.bl;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulate strings in different languages into single object and operations for them.</br>
 * Language is based on enum LanguageType.
 *
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see LanguageType
 */
public final class InternationalString {

    /**
     * Map that stores strings in different languages
     */
    private Map<LanguageType, String> stringMap = new HashMap<LanguageType, String>(LanguageType.getLanguageCount());

    public InternationalString() {
    }

    /**
     * Search those strings with given keyword
     *
     * @param keyword
     * @return the relevance, whose value indicates the count that
     * the keyword occurs in the content.
     */
    public int search(String keyword) {
        String pattern = ".*" + keyword + ".*";
        int relevance = 0;
        for (String string : stringMap.values()) {
            if (string != null && string.toLowerCase().matches(pattern)) {
                relevance++;
            }
        }
        return relevance;
    }

    public void setString(LanguageType lType, String content) {
        stringMap.put(lType, content);
    }

    public String getString(LanguageType lType) {
        return stringMap.get(lType);
    }

    /**
     * Get string according to the content language located in class LanguagePreference
     */

    public String getContentString() {
        return stringMap.get(User.getContentLanguage());
    }

    public String getInterfaceString() {
        return stringMap.get(User.getInterfaceLanguage());
    }
}