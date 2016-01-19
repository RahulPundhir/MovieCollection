package com.android.media.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_CACHE = "cache";
    public static final String COLUMN_REQUEST_ID = "requestId";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_CACHE + "("
            + COLUMN_REQUEST_ID + " text not null, "
            + COLUMN_CONTENT + " text not null, "
            + COLUMN_TIMESTAMP + " text not null);";

    private static final String DB_NAME = "MediaDB";
    private static final int DB_VERSION = 1;
    private boolean mIsDBOpened;

    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context);
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public boolean isDBOpened() {
        return mIsDBOpened;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CACHE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        mIsDBOpened = true;
    }

    @Override
    public synchronized void close() {
        super.close();
        mIsDBOpened = false;
    }
}
