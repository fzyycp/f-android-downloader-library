package cn.faury.android.library.downloader.db.storage;

import android.database.sqlite.SQLiteDatabase;

import cn.faury.android.library.common.sqlite.dao.BaseTableDao;
import cn.faury.android.library.downloader.DownloadFileInfo;

/**
 * 下载数据保存表数据库
 */

public class DownloadTableDao extends BaseTableDao {
    public static final String TABLE_NAME = DownloadFileInfo.Table.TABLE_NAME_OF_DOWNLOAD_FILE;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DownloadFileInfo.Table.getCreateTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
