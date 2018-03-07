package cn.faury.android.library.downloader.record;

/**
 * 重命名下载文件
 */

public interface DownloadDbRecorderRenamer extends DownloadDbRecorder {

    /**
     * rename download file name
     *
     * @param url         download url
     * @param newFileName new file name
     * @throws Exception any exception during rename
     */
    void renameDownloadFile(String url, String newFileName) throws Exception;

}
