package com.android.media.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class CacheDAO implements ICacheDAO {

    private static final String EQUALS = "=";
    private static final String PARAMETER = "?";
    private static final int CURSOR_REQUEST_ID_INDEX = 0;
    private static final int CURSOR_CONTENT_INDEX = 1;
    private static final int CURSOR_TIMESTAMP_INDEX = 2;
    private Context context;
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {
            DBHelper.COLUMN_REQUEST_ID,
            DBHelper.COLUMN_CONTENT,
            DBHelper.COLUMN_TIMESTAMP};
    private Object mSyncObj = new Object();

    public CacheDAO(Context context) {
        this.context = context;
        this.dbHelper = DBHelper.getInstance(context);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void open() {
        synchronized (mSyncObj) {
            try {
                database = dbHelper.getWritableDatabase();
            } catch (Error e) {
                Log.d("Database Open Exception", e.getCause().toString());
            }
        }
    }

    @Override
    public void close() {
        synchronized (mSyncObj) {
            dbHelper.close();
        }
    }

    @Override
    synchronized public void createAndInsertCacheEntity(String requestId, String content, long timestamp) {
        if (database != null) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_REQUEST_ID, requestId);
            values.put(DBHelper.COLUMN_CONTENT, content);
            values.put(DBHelper.COLUMN_TIMESTAMP, timestamp+"");
            database.insert(DBHelper.TABLE_CACHE,
                    null,
                    values);
        }
    }

    @Override
    synchronized public void deleteCacheEntity(String requestId) {
        if (database != null) {
            database.delete(DBHelper.TABLE_CACHE,
                    DBHelper.COLUMN_REQUEST_ID + EQUALS + PARAMETER,
                    new String[]{requestId});
        }
    }

    @Override
    public List<CacheEntity> getAllCacheEntities() {
        if (database != null) {
            List<CacheEntity> comments = new ArrayList<CacheEntity>();
            Cursor cursor = database.query(DBHelper.TABLE_CACHE,
                    allColumns,
                    null,
                    null,
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                CacheEntity comment = cursorToCacheEntity(cursor);
                comments.add(comment);
                cursor.moveToNext();
            }
            cursor.close();
            return comments;
        }
        return null;
    }

    @Override
    public CacheEntity getCacheEntity(String requestId) {
        if (database != null) {
            Cursor cursor = database.query(DBHelper.TABLE_CACHE,
                    allColumns,
                    DBHelper.COLUMN_REQUEST_ID + EQUALS + PARAMETER,
                    new String[]{requestId},
                    null,
                    null,
                    null);
            CacheEntity cacheEntity = null;
            if (cursor.moveToFirst()) {
                cacheEntity = cursorToCacheEntity(cursor);
            }
            cursor.close();
            return cacheEntity;
        }
        return null;
    }

    @Override
    public void deleteAllCacheEntries() {
        if (database != null) {
            database.delete(DBHelper.TABLE_CACHE, null, null);
        }
    }

    private CacheEntity cursorToCacheEntity(Cursor cursor) {
        CacheEntity comment = new CacheEntity();
        comment.setRequestId(cursor.getString(CURSOR_REQUEST_ID_INDEX));
        comment.setContent(cursor.getString(CURSOR_CONTENT_INDEX));
        comment.setTimestamp(cursor.getString(CURSOR_TIMESTAMP_INDEX));
        return comment;
    }

    /**
     * This method is used to update cache entity.
     *
     * @param requestId
     * @param jsonEntity
     * @param timestamp
     */
    @Override
    synchronized public void updateCacheEntity(String requestId, String jsonEntity, long timestamp) {
        if (database != null) {
            ContentValues values = new ContentValues();
            values.put(DBHelper.COLUMN_CONTENT, jsonEntity);
            values.put(DBHelper.COLUMN_TIMESTAMP, timestamp+"");
            database.update(DBHelper.TABLE_CACHE, values, DBHelper.COLUMN_REQUEST_ID + EQUALS + PARAMETER,
                    new String[]{requestId});
        }
    }
}
