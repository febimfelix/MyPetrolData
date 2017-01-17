package com.febi.mydata;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by flock on 17/1/17.
 */

public class MyDataDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE dataTable (_id INTEGER PRIMARY KEY, amount TEXT, date TEXT," +
                    "place TEXT, quantity INTEGER)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "mydata.db";

    public MyDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
