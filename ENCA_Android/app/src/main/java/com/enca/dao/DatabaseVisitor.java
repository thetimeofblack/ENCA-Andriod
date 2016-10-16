package com.enca.dao;

import android.database.Cursor;

/**
 * Execute SQL operations
 *
 * @author Xiaoqi.Ma
 * @version 02.07.2016
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
        return DatabaseAccess.getImage(cleaningAgentID);
    }


}
