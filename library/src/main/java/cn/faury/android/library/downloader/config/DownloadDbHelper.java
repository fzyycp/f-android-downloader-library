package cn.faury.android.library.downloader.config;

import android.content.Context;

import java.util.List;

import cn.faury.android.library.downloader.db.base.BaseContentDbHelper;
import cn.faury.android.library.downloader.db.base.ContentDbDao;

/**
 * 下载文件数据库处理类
 */

public class DownloadDbHelper extends BaseContentDbHelper {

    private static final String DB_NAME = "download_file.db";

    private static final int DB_VERSION = 1;

    public DownloadDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * config daos
     *
     * @param contentDbDaos current daos
     */
    @Override
    protected void onConfigContentDbDaos(List<ContentDbDao> contentDbDaos) {
        DownloadContentDbDao downloadFileDao = new DownloadContentDbDao(this);
        // config DownloadFileDao dao
        contentDbDaos.add(downloadFileDao);
    }
}
