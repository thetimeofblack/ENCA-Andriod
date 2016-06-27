package com.enca.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private static SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all data from the database.
     */

    public static Cursor executeSelect(String query) {
        return  database.rawQuery(query, null);
    }

    /**
     * Read the BLOB data as byte[]
     *
     * @param cleaningAgentID
     * @return image as byte[]
     */
    public static byte[] getImage(int cleaningAgentID) {
        byte[] data = null;
        Cursor cursor = database.rawQuery("SELECT image FROM CleaningAgents WHERE cleaningAgentID = ?", new String[]{String.valueOf(cleaningAgentID)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            data = cursor.getBlob(0);
            break;  //
        }
        cursor.close();
        return data;
    }


}