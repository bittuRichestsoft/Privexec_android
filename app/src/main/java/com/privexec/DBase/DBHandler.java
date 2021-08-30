package com.privexec.DBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "AllApps.db";
    public static final String AllApps_Table = "all_apps_table";

    private static String CREATE_AllApps_Table = "CREATE TABLE IF NOT EXISTS " + AllApps_Table + " (_id INTEGER PRIMARY KEY, appName TEXT,packageName TEXT )";
    private static String DROP_AllApps_Table = "DROP TABLE IF EXISTS " + AllApps_Table;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL(CREATE_AllApps_Table);
     }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_AllApps_Table);
         onCreate(db);
    }


}
