package cn.faury.android.library.downloader.listener;

import android.os.Handler;
import android.os.Looper;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;

/**
 * 文件下载状态改变监听器
 */
public interface OnRetryableFileDownloadStatusListener extends OnFileDownloadStatusListener {

    /**
     * retry download
     *
     * @param downloadFileInfo download file info
     * @param retryTimes       the times to retry
     */
    void onFileDownloadStatusRetrying(DownloadDbFileInfo downloadFileInfo, int retryTimes);

    /**
     * Callback helper for main thread
     */
    public static class MainThreadHelper {
        /**
         * retry download
         *
         * @param downloadFileInfo download file info
         * @param retryTimes       the times to retry
         */
        public static void onFileDownloadStatusRetrying(final DownloadDbFileInfo downloadFileInfo, final int 
                retryTimes, final OnRetryableFileDownloadStatusListener onRetryableFileDownloadStatusListener) {
            if (onRetryableFileDownloadStatusListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onRetryableFileDownloadStatusListener == null) {
                        return;
                    }
                    onRetryableFileDownloadStatusListener.onFileDownloadStatusRetrying(downloadFileInfo, retryTimes);
                }
            });
        }
    }
}
