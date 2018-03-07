package cn.faury.android.library.downloader.record;

/**
 *移动下载文件
 */

public interface DownloadDbRecorderMover extends DownloadDbRecorder {

    /**
     * move download file name
     *
     * @param url        download url
     * @param newDirPath new file name
     * @throws Exception any exception during move
     */
    void moveDownloadFile(String url, String newDirPath) throws Exception;

}
