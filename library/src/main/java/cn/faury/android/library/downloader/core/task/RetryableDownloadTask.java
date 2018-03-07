package cn.faury.android.library.downloader.core.task;

/**
 * RetryableDownloadTask interface
 */
public interface RetryableDownloadTask extends DownloadTask {

    /**
     * set RetryDownloadTimes
     *
     * @param retryDownloadTimes
     */
    void setRetryDownloadTimes(int retryDownloadTimes);
}
