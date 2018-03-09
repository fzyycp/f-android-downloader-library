package cn.faury.android.library.downloader.db.test;

import java.util.List;

import cn.faury.android.library.downloader.db.sqlite.bean.AbstractDatabaseBean;
import cn.faury.android.library.downloader.db.sqlite.dao.AbstractTableDao;

/**
 *
 */

public class DownloadFileDatabaseBean extends AbstractDatabaseBean {

    public static final String DB_NAME = "download_file.db";
    public static final int DB_VERSION = 3;

    /**
     * 构造函数
     *
     * @param dir       数据库目录
     */
    public DownloadFileDatabaseBean(String dir) {
        super(DB_NAME, DB_VERSION, dir);
    }

    /**
     * 子类注册表信息
     *
     * @param tablesList 表集合
     */
    @Override
    protected void onConfigTablesList(List<AbstractTableDao> tablesList) {
        tablesList.add(new DownloadFileTableDao(this));
    }
}
