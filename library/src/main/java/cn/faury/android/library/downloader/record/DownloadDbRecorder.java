package cn.faury.android.library.downloader.record;

import java.util.List;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;

/**
 * 数据库记录器（记录下载文件状态）
 */

public interface DownloadDbRecorder extends Recorder {

    /**
     * get DownloadFile by url
     *
     * @param url the url
     * @return DownloadFile recorded
     */
    DownloadDbFileInfo getDownloadFile(String url);

    /**
     * get all DownloadFiles
     *
     * @return all DownloadFile recorded
     */
    List<DownloadDbFileInfo> getDownloadFiles();

}
