package cn.faury.android.library.downloader.listener;

import android.os.Handler;
import android.os.Looper;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;

/**
 * the listener for listening the DownloadFile change
 */
public interface OnDownloadChangeListener {

    /**
     * an new DownloadFile created
     *
     * @param downloadFileInfo new DownloadFile created
     */
    void onDownloadFileCreated(DownloadDbFileInfo downloadFileInfo);

    /**
     * an DownloadFile updated
     *
     * @param downloadFileInfo DownloadFile updated
     * @param type             the update type
     */
    void onDownloadFileUpdated(DownloadDbFileInfo downloadFileInfo, Type type);

    /**
     * an DownloadFile deleted
     *
     * @param downloadFileInfo DownloadFile deleted
     */
    void onDownloadFileDeleted(DownloadDbFileInfo downloadFileInfo);

    /**
     * Callback helper for main thread
     */
    class MainThreadHelper {

        /**
         * an new DownloadFile created
         *
         * @param downloadFileInfo new DownloadFile created
         */
        public static void onDownloadFileCreated(final DownloadDbFileInfo downloadFileInfo, final
        OnDownloadChangeListener onDownloadFileChangeListener) {
            if (onDownloadFileChangeListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onDownloadFileChangeListener == null) {
                        return;
                    }
                    onDownloadFileChangeListener.onDownloadFileCreated(downloadFileInfo);
                }
            });
        }

        /**
         * an DownloadFile updated
         *
         * @param downloadFileInfo DownloadFile updated
         * @param type             the update type
         */
        public static void onDownloadFileUpdated(final DownloadDbFileInfo downloadFileInfo, final Type type, final
        OnDownloadChangeListener onDownloadFileChangeListener) {
            if (onDownloadFileChangeListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onDownloadFileChangeListener == null) {
                        return;
                    }
                    onDownloadFileChangeListener.onDownloadFileUpdated(downloadFileInfo, type);
                }
            });
        }

        /**
         * an DownloadFile deleted
         *
         * @param downloadFileInfo DownloadFile deleted
         */
        public static void onDownloadFileDeleted(final DownloadDbFileInfo downloadFileInfo, final
        OnDownloadChangeListener onDownloadFileChangeListener) {
            if (onDownloadFileChangeListener == null) {
                return;
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (onDownloadFileChangeListener == null) {
                        return;
                    }
                    onDownloadFileChangeListener.onDownloadFileDeleted(downloadFileInfo);
                }
            });
        }
    }

    /**
     * DownloadFile Update Type
     */
    enum Type {

        /**
         * download status changed
         */
        DOWNLOAD_STATUS,
        /**
         * downloaded size changed
         */
        DOWNLOADED_SIZE,
        /**
         * save dir changed
         */
        SAVE_DIR,
        /**
         * save file name changed
         */
        SAVE_FILE_NAME,
        /**
         * other,except {@link #DOWNLOAD_STATUS},{@link #DOWNLOADED_SIZE},{@link #SAVE_DIR} and {@link #SAVE_FILE_NAME}
         */
        OTHER
    }
}
