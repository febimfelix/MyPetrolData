package com.febi.mydata;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

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
        new InitialTask().execute(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertEntry(MyData myData, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("amount", myData.getAmount());
        values.put("date", myData.getDate());
        values.put("place", myData.getPlace());
        values.put("quantity", myData.getQuantity());

        db.insert("dataTable", null, values);
    }

    private class InitialTask extends AsyncTask<Object, Void, Void>{

        @Override
        protected Void doInBackground(Object... params) {
            initialPopulation((SQLiteDatabase) params[0]);
            return null;
        }
    }

    private void initialPopulation(SQLiteDatabase db) {
        MyData myData = new MyData();
        myData.setAmount("2000");
        myData.setQuantity(0);
        myData.setPlace("Elamakkara");
        myData.setDate("6/1/2017");
        insertEntry(myData, db);

        myData.setAmount("2000");
        myData.setQuantity(0);
        myData.setPlace("Elamakkara");
        myData.setDate("12/1/2017");
        insertEntry(myData, db);

        myData.setAmount("2000");
        myData.setQuantity(0);
        myData.setPlace("Kumily");
        myData.setDate("25/12/2016");
        insertEntry(myData, db);

        myData.setAmount("2000");
        myData.setQuantity(0);
        myData.setPlace("Elamakkara");
        myData.setDate("23/12/2016");
        insertEntry(myData, db);

        myData.setAmount("1500");
        myData.setQuantity(0);
        myData.setPlace("Elamakkara");
        myData.setDate("2/12/2016");
        insertEntry(myData, db);

        myData.setAmount("1500");
        myData.setQuantity(0);
        myData.setPlace("Elamkunnapuzha");
        myData.setDate("9/12/2016");
        insertEntry(myData, db);

        myData.setAmount("1500");
        myData.setQuantity(0);
        myData.setPlace("Elamakkara");
        myData.setDate("15/12/2016");
        insertEntry(myData, db);

        myData.setAmount("1500");
        myData.setQuantity(0);
        myData.setPlace("Elamkunnapuzha");
        myData.setDate("16/11/2016");
        insertEntry(myData, db);

        myData.setAmount("1500");
        myData.setQuantity(0);
        myData.setPlace("High Court");
        myData.setDate("21/11/2016");
        insertEntry(myData, db);
    }
}
