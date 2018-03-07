package cn.faury.android.library.downloader.record;

/**
 *删除下载文件
 */

public interface DownloadDbRecorderDeleter extends DownloadDbRecorder {

    /**
     * delete download file
     *
     * @param url download url
     * @throws Exception any exception during delete
     */
    void deleteDownloadFile(String url) throws Exception;

}
