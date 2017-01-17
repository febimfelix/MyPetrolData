package com.febi.mydata;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by flock on 17/1/17.
 */

public class MyData {
    private int id;
    private String date;
    private String amount;
    private int quantity;
    private String place;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public static void insertEntry(MyData myData) {
        ContentValues values = new ContentValues();
        values.put("amount", myData.getAmount());
        values.put("date", myData.getDate());
        values.put("place", myData.getPlace());
        values.put("quantity", myData.getQuantity());

        AppContext.mSqliteDatabse.insert("dataTable", null, values);
    }

    public static void editEntry(MyData myData) {
        ContentValues values = new ContentValues();
        values.put("_id", myData.getId());
        values.put("amount", myData.getAmount());
        values.put("date", myData.getDate());
        values.put("place", myData.getPlace());
        values.put("quantity", myData.getQuantity());
        AppContext.mSqliteDatabse.replace("dataTable", null, values);
    }

    public static ArrayList<MyData> getValuesFromDb() {
        ArrayList<MyData> myDataArrayList = null;
        String[] projection = {
                "_id",
                "amount",
                "date",
                "place",
                "quantity"
        };

        String sortOrder =
                "_id" + " DESC";

        Cursor cursor = AppContext.mSqliteDatabse.query(
                "dataTable",
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        if(cursor != null && cursor.getCount() > 0) {
            myDataArrayList     = new ArrayList<>();
            while (cursor.moveToNext()) {
                MyData myData   = new MyData();
                myData.setId(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                myData.setAmount(cursor.getString(cursor.getColumnIndexOrThrow("amount")));
                myData.setPlace(cursor.getString(cursor.getColumnIndexOrThrow("place")));
                myData.setDate(cursor.getString(cursor.getColumnIndexOrThrow("date")));
                myData.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));

                myDataArrayList.add(myData);
            }
            cursor.close();
        }

        return myDataArrayList;
    }

    public static void clearDatabase() {
        AppContext.mSqliteDatabse.delete("dataTable", null, null);
    }

    public void deleteRow(int id) {
        AppContext.mSqliteDatabse.delete("dataTable", "_id=?", new String[]{Integer.toString(id)});
    }
}
