package com.enca.bl;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 85102 on 6/27/2016.
 */
public final class UserPreference implements Serializable {

    private static final long serialVersionUID = -5000180069279808816L;

    private static Map<LanguageType, DateFormat> dateFormatMap = new HashMap<>();
    static {
        dateFormatMap.put(LanguageType.ENGLISH, new SimpleDateFormat("MM/dd/yyyy"));
        dateFormatMap.put(LanguageType.GERMAN, new SimpleDateFormat("dd.MM.yyyy"));
        dateFormatMap.put(LanguageType.CHINESE, new SimpleDateFormat("yyyy年MM月dd日"));
    }

    private String name = "";
    private Date regDate = new Date();

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
}