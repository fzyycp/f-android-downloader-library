package cn.faury.android.library.downloader.record;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;

/**
 *下载文件任务
 */

public interface DownloadDbRecorderReseter extends DownloadDbRecorder {

    /**
     * record download file
     *
     * @param url        file url
     * @param deleteMode true means delete all resource
     * @throws Exception any exception during record
     */
    void resetDownloadFile(String url, boolean deleteMode) throws Exception;

    /**
     * reset download size
     *
     * @param url          download url
     * @param downloadSize the downloadSize reset to
     * @throws Exception any fail exception during recording status
     */
    void resetDownloadSize(String url, long downloadSize) throws Exception;

    DownloadDbFileInfo createDownloadFileInfo(DownloadDbFileInfo detectUrlFileInfo);

}
