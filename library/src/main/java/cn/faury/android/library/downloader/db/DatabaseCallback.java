package cn.faury.android.library.downloader.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * 数据库回调
 */

public interface DatabaseCallback {

    /**
     * the database has been created
     *
     * @param db SQLiteDatabase
     */
    void onCreate(SQLiteDatabase db);

    /**
     * the database has been upgraded
     *
     * @param db         SQLiteDatabase
     * @param oldVersion oldVersion
     * @param newVersion newVersion
     */
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}