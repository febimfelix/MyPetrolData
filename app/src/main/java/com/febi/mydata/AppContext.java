package com.febi.mydata;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by flock on 17/1/17.
 */

public class AppContext extends Application {
    public static SQLiteDatabase mSqliteDatabse;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDataDbHelper myDataDbHelper   = new MyDataDbHelper(this);
        mSqliteDatabse                  = myDataDbHelper.getWritableDatabase();
    }
}
