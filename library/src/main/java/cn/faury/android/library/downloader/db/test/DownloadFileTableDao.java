package cn.faury.android.library.downloader.db.test;

import android.database.sqlite.SQLiteDatabase;

import cn.faury.android.library.common.helper.Logger;
import cn.faury.android.library.downloader.DownloadFileInfo;
import cn.faury.android.library.downloader.db.sqlite.bean.AbstractDatabaseBean;
import cn.faury.android.library.downloader.db.sqlite.dao.AbstractTableDao;

/**
 *
 */

public class DownloadFileTableDao extends AbstractTableDao {

    public DownloadFileTableDao(AbstractDatabaseBean databaseInfo) {
        super(databaseInfo, DownloadFileInfo.Table.TABLE_NAME_OF_DOWNLOAD_FILE);
    }

    /**
     * 创建数据库时执行
     *
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DownloadFileInfo.Table.getCreateTableSql());
    }

    /**
     * 更新数据库时执行
     *
     * @param db         SQLiteDatabase
     * @param oldVersion 旧版本
     * @param newVersion 新版本
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Logger.d(TAG, ".onUpgrade，oldVersion：" + oldVersion + "，oldVersion：" + newVersion);

        // upgrade to version 2
        if (newVersion == 2) {
            switch (oldVersion) {
                case 1:
                    // version 1 to 2
                    updateVersion1To2(db);
                    break;
            }
        }
        // upgrade to version 3
        else if (newVersion == 3) {
            switch (oldVersion) {
                case 1:
                    // version 1 to 3
                    updateVersion1To3(db);
                    break;
                case 2:
                    // version 2 to 3
                    updateVersion2To3(db);
                    break;
            }
        }
        // upgrade to version 4

    }

    // version 1 to 2
    private void updateVersion1To2(SQLiteDatabase db) {
        db.execSQL(DownloadFileInfo.Table.getUpdateTableVersion1To2Sql());
    }

    // version 2 to 3
    private void updateVersion2To3(SQLiteDatabase db) {
        db.execSQL(DownloadFileInfo.Table.getUpdateTableVersion2To3Sql());
    }

    // version 1 to 3
    private void updateVersion1To3(SQLiteDatabase db) {
        db.execSQL(DownloadFileInfo.Table.getUpdateTableVersion1To2Sql());
        db.execSQL(DownloadFileInfo.Table.getUpdateTableVersion2To3Sql());
    }

}
