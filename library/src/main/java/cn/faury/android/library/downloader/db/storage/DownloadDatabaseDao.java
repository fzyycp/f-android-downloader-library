package cn.faury.android.library.downloader.db.storage;

import android.content.Context;

import java.util.List;

import cn.faury.android.library.common.sqlite.dao.BaseDatabaseDao;
import cn.faury.android.library.common.sqlite.dao.BaseTableDao;

/**
 * 下载数据保存数据库
 */

public class DownloadDatabaseDao extends BaseDatabaseDao {

    public static final String DB_NAME = "download_file.db";
    private static final int DB_VERSION = 3;

    public DownloadDatabaseDao(Context context,String dir) {
        super(DB_NAME, DB_VERSION, dir);
    }

    @Override
    protected void onConfigTablesList(List<BaseTableDao> list) {
        list.add(new DownloadTableDao());
    }
}
