package cn.faury.android.library.downloader.common;

/**
 * 文件下载状态
 */
public final class DownloadStatus {

    /**
     * UNKNOWN
     */
    public static final int UNKNOWN = 0;
    /**
     * WAITING(wait for other task finish,there is no more task place to hold this task)
     */
    public static final int WAITING = 1;
    /**
     * PREPARING(prepare to connect the url source)
     */
    public static final int PREPARING = 2;
    /**
     * PREPARED(the url source has been connected)
     */
    public static final int PREPARED = 3;
    /**
     * DOWNLOADING
     */
    public static final int DOWNLOADING = 4;
    /**
     * COMPLETED(the file has been fully downloaded without errors)
     */
    public static final int COMPLETED = 5;
    /**
     * PAUSED(the download is paused)
     */
    public static final int PAUSED = 6;
    /**
     * ERROR(the download encountered serious error and may not recovery)
     */
    public static final int ERROR = 7;
    /**
     * FILE_NOT_EXIST(the file has been started downloading ,but removed or delete unexpected)
     */
    public static final int FILE_NOT_EXIST = 8;
    /**
     * RETRYING(retry to re-connect the url source after failed)
     */
    public static final int RETRYING = 9;
}
