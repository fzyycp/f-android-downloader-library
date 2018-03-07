package cn.faury.android.library.downloader.config;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;
import cn.faury.android.library.downloader.db.base.BaseContentDbDao;

/**
 *
 */

public class DownloadContentDbDao extends BaseContentDbDao {

    public DownloadContentDbDao(SQLiteOpenHelper dbHelper) {
        super(dbHelper, DownloadDbFileInfo.Table.TABLE_NAME_OF_DOWNLOAD_FILE, DownloadDbFileInfo.Table
                .COLUMN_NAME_OF_FIELD_ID);
    }

    /**
     * the database has been created
     *
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DownloadDbFileInfo.Table.getCreateTableSql());
    }

    /**
     * the database has been upgraded
     *
     * @param db         SQLiteDatabase
     * @param oldVersion oldVersion
     * @param newVersion newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DownloadDbFileInfo.Table.getUpdateTableVersion1To2Sql());
    }
}
