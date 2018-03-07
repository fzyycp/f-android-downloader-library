package cn.faury.android.library.downloader.db.base;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */

public abstract class BaseContentDbDao implements ContentDbDao {

    /**
     * default id field name
     */
    public static final String DEFAULT_TABLE_ID_FIELD_NAME = "_id";

    private SQLiteOpenHelper mDbHelper;
    private String mTableName;
    private String mTableIdFieldName = DEFAULT_TABLE_ID_FIELD_NAME;

    public BaseContentDbDao(SQLiteOpenHelper dbHelper, String tableName, String tableIdFieldName) {
        super();
        this.mDbHelper = dbHelper;
        this.mTableName = tableName;
        this.mTableIdFieldName = tableIdFieldName;
    }

    @Override
    public long insert(ContentValues values) {
        long id = -1;
        SQLiteDatabase database = null;
        try {
            database = mDbHelper.getWritableDatabase();
            id = database.insert(mTableName, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int delete(String whereClause, String[] whereArgs) {
        int count = -1;
        SQLiteDatabase database = null;
        try {
            database = mDbHelper.getWritableDatabase();
            count = database.delete(mTableName, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int update(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase database = null;
        int count = -1;
        try {
            database = mDbHelper.getWritableDatabase();
            count = database.update(mTableName, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public Cursor query(String[] projection, String selection, String[] selectionArgs, String orderBy) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = mDbHelper.getReadableDatabase();
            cursor = database.query(true, mTableName, null, selection, selectionArgs, null, null, orderBy, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public String getTableName() {
        return mTableName;
    }

    @Override
    public String getTableIdFieldName() {
        return mTableIdFieldName;
    }
}
