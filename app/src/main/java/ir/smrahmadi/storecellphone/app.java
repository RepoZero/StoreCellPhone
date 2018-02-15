package ir.smrahmadi.storecellphone;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import ir.smrahmadi.storecellphone.SQLiteDB.Connection;

/**
 * Created by cloner on 12/23/17.
 */

public class app extends Application {

    public static Connection MyDataBase;
    static public SQLiteDatabase DB;

    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBase = new Connection(this);
        DB = MyDataBase.getWritableDatabase();

    }
}
