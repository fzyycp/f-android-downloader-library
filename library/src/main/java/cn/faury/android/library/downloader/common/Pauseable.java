package cn.faury.android.library.downloader.common;

import cn.faury.android.library.downloader.listener.OnStopFileDownloadTaskListener;

/**
 * 可暂停的接口
 */
public interface Pauseable {

    /**
     * whether is downloading
     *
     * @param url file url
     * @return true means the download task of the url is running
     */
    boolean isDownloading(String url);

    /**
     * pause a download
     *
     * @param url                            file url
     * @param onStopFileDownloadTaskListener OnStopFileDownloadTaskListener impl
     */
    void pause(String url, OnStopFileDownloadTaskListener onStopFileDownloadTaskListener);
}
