package cn.faury.android.library.downloader.file_move;

import cn.faury.android.library.downloader.file_download.db_recorder.DownloadFileDbRecorder;

/**
 * DownloadFileMover
 * <br/>
 * 移动下载文件
 *
 * @author wlf(Andy)
 * @email 411086563@qq.com
 * @since 0.3.0
 */
public interface DownloadFileMover extends DownloadFileDbRecorder {

    /**
     * move download file name
     *
     * @param url        download url
     * @param newDirPath new file name
     * @throws Exception any exception during move
     */
    void moveDownloadFile(String url, String newDirPath) throws Exception;
}