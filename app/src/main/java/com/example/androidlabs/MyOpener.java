package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyOpener extends SQLiteOpenHelper {

    //Database name
    protected final static String DATABASE_NAME = "ChatRoom";
    //Database version
    protected final static int VERSION_NUM = 1;
    //Table name
    public final static String TABLE_NAME = "ChatLog";
    //Table columns
    public final static String COL_ID = "_id";
    public final static String COL_MESSAGE = "message";
    public final static String COL_TYPE = "text";

    public MyOpener(Context context) {

        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TYPE + " INTEGER, "
                + COL_MESSAGE + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }
}
