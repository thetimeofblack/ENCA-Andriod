package com.enca.dao;

import android.database.Cursor;

/**
 * Created by 85102 on 6/18/2016.
 */
public class DatabaseVisitor {

    public static Cursor visitCleaningAgentsAll() {
        return DatabaseAccess.executeSelect("select * from CleaningAgents");
    }

    public static Cursor visitTagsAll() {
        return DatabaseAccess.executeSelect("select * from Tags");
    }

    public static Cursor visitRelations() {
        return DatabaseAccess.executeSelect("select * from TC");
    }

    public static Cursor visitMemos() {
        return DatabaseAccess.executeSelect("select * from Memos");
    }

    public static byte[] visitImage(int cleaningAgentID) {
        return  DatabaseAccess.getImage(cleaningAgentID);
    }


}
