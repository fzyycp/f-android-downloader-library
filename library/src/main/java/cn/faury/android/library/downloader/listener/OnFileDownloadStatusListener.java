package cn.faury.android.library.downloader.listener;

import android.os.Handler;
import android.os.Looper;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;
import cn.faury.android.library.downloader.error.FileDownloadStatusFailReason;

/**
 * 文件下载状态改变监听器
 */
public interface OnFileDownloadStatusListener {

    /**
     * waiting download
     *
     * @param downloadFileInfo download file info
     */
    void onFileDownloadStatusWaiting(DownloadDbFileInfo downloadFileInfo);

    /**
     * preparing
     *
     * @param downloadFileInfo download file info
     */
    void onFileDownloadStatusPreparing(DownloadDbFileInfo downloadFileInfo);

    /**
     * prepared(connected)
     *
     * @param downloadFileInfo download file info
     */
    void onFileDownloadStatusPrepared(DownloadDbFileInfo downloadFileInfo);

    /**
     * downloading
     *
     * @param downloadFileInfo download file info
     * @param downloadSpeed    download speed, KB/s
     * @param remainingTime    remain time, seconds
     */
    void onFileDownloadStatusDownloading(DownloadDbFileInfo downloadFileInfo, float downloadSpeed, long remainingTime);

    /**
     * download paused
     *
     * @param downloadFileInfo download file info
     */
    void onFileDownloadStatusPaused(DownloadDbFileInfo downloadFileInfo);

    /**
     * download completed
     *
     * @param downloadFileInfo download file info
     */
    void onFileDownloadStatusCompleted(DownloadDbFileInfo downloadFileInfo);

    /**
     * download error
     *
     * @param url              file url
     * @param downloadFileInfo download file info,may null
     * @param failReason       fail reason
     */
    void onFileDownloadStatusFailed(String url, DownloadDbFileInfo downloadFileInfo, FileDownloadStatusFailReason
            failReason);

    /**
     * Callback helper for main thread
     */
    public static class MainThreadHelper {

        /**
         * waiting download
         *
         * @param downloadFileInfo download file info
         */
        public static void onFileDownloadStatusWaiting(final DownloadDbFileInfo downloadFileInfo, final
        OnFileDownloadStatusListener onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusWaiting(downloadFileInfo);
                }
            });
        }

        /**
         * preparing
         *
         * @param downloadFileInfo download file info
         */
        public static void onFileDownloadStatusPreparing(final DownloadDbFileInfo downloadFileInfo, final
        OnFileDownloadStatusListener onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusPreparing(downloadFileInfo);
                }
            });
        }

        /**
         * prepared(connected)
         *
         * @param downloadFileInfo download file info
         */
        public static void onFileDownloadStatusPrepared(final DownloadDbFileInfo downloadFileInfo, final
        OnFileDownloadStatusListener onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusPrepared(downloadFileInfo);
                }
            });
        }

        /**
         * downloading
         *
         * @param downloadFileInfo download file info
         * @param downloadSpeed    download speed,KB/s
         * @param remainingTime    remain time,seconds
         */
        public static void onFileDownloadStatusDownloading(final DownloadDbFileInfo downloadFileInfo, final float
                downloadSpeed, final long remainingTime, final OnFileDownloadStatusListener 
                onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusDownloading(downloadFileInfo, downloadSpeed, 
                            remainingTime);
                }
            });
        }

        /**
         * download paused
         *
         * @param downloadFileInfo download file info
         */
        public static void onFileDownloadStatusPaused(final DownloadDbFileInfo downloadFileInfo, final
        OnFileDownloadStatusListener onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusPaused(downloadFileInfo);
                }
            });
        }

        /**
         * download completed
         *
         * @param downloadFileInfo download file info
         */
        public static void onFileDownloadStatusCompleted(final DownloadDbFileInfo downloadFileInfo, final
        OnFileDownloadStatusListener onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusCompleted(downloadFileInfo);
                }
            });
        }

        /**
         * download error
         *
         * @param url              file url
         * @param downloadFileInfo download file info
         * @param failReason       fail reason
         */
        public static void onFileDownloadStatusFailed(final String url, final DownloadDbFileInfo downloadFileInfo,
                                                      final FileDownloadStatusFailReason failReason, final 
                                                      OnFileDownloadStatusListener onFileDownloadStatusListener) {
            if (onFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onFileDownloadStatusListener == null) {
                        return;
                    }
                    onFileDownloadStatusListener.onFileDownloadStatusFailed(url, downloadFileInfo, failReason);
                }
            });
        }
    }
}
