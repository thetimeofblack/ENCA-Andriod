package com.enca.bl;

import java.io.Serializable;

/**
 * Store the language preference of the user.
 *
 * @author Zhaowen.Gong
 * @version 30.06.2016
 * @see LanguageType
 */
public final class LanguagePreference implements Serializable {

    private static final long serialVersionUID = -170811844308052715L;
    private static final LanguageType DefaultLanguage = LanguageType.ENGLISH;

    private LanguageType interfaceLanguage = DefaultLanguage;
    private LanguageType contentLanguage = DefaultLanguage;

    public LanguageType getInterfaceLanguage() {
        return interfaceLanguage;
    }

    public void setInterfaceLanguage(LanguageType interfaceLanguage) {
        this.interfaceLanguage = interfaceLanguage;
    }

    public LanguageType getContentLanguage() {
        return contentLanguage;
    }

    public void setContentLanguage(LanguageType contentLanguage) {
        this.contentLanguage = contentLanguage;
    }

}