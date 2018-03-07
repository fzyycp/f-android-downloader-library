package cn.faury.android.library.downloader;

import android.content.Context;

import java.util.List;

import cn.faury.android.library.common.helper.Logger;
import cn.faury.android.library.common.util.CollectionsUtils;
import cn.faury.android.library.downloader.cacher.DownloadCacher;
import cn.faury.android.library.downloader.common.DownloadDbFileInfo;
import cn.faury.android.library.downloader.common.DownloadFileUtil;
import cn.faury.android.library.downloader.config.DownloaderConfiguration;
import cn.faury.android.library.downloader.task.DownloadTaskManager;

/**
 *
 */

final class DownloaderManager {

    private static final String TAG = DownloaderManager.class.getName();
    /**
     * single instance
     */
    private static DownloaderManager sInstance;
    /**
     * init lock
     */
    private Object mInitLock = new Object();

    /**
     * FileDownload Configuration, which stored global configurations
     */
    private DownloaderConfiguration mConfiguration;
    /**
     * DownloadTaskManager, which to manage download tasks
     */
    private DownloadTaskManager mDownloadTaskManager;
    /**
     * DownloadFileCacher, which stored download files
     */
    private DownloadCacher mDownloadFileCacher;

    //  constructor of Downloader, private only
    private DownloaderManager(Context context) {

        Context appContext = context.getApplicationContext();

        // init DownloadFileCacher
        mDownloadFileCacher = new DownloadCacher(appContext);

        // check the download status, if there is an exception status, try to recover it
        checkAndRecoveryExceptionStatus(getDownloadFiles());
    }

    /**
     * get all DownloadFiles
     *
     * @return all DownloadFiles
     */
    public List<DownloadDbFileInfo> getDownloadFiles() {
        return mDownloadFileCacher.getDownloadFiles();
    }

    /**
     * check and recovery exception status
     */
    private void checkAndRecoveryExceptionStatus(List<DownloadDbFileInfo> downloadFileInfos) {

        Logger.d(TAG, "checkAndRecoveryExceptionStatus 异常恢复检查！");

        if (CollectionsUtils.isEmpty(downloadFileInfos)) {
            return;
        }

        for (DownloadDbFileInfo downloadFileInfo : downloadFileInfos) {

            if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
                continue;
            }

            String url = downloadFileInfo.getUrl();

            // initialized and isDownloading, ignore
            if (isInit() && getDownloadTaskManager().isDownloading(url)) {
                continue;
            }

            // recovery status if necessary
            DownloadFileUtil.recoveryExceptionStatus(mDownloadFileCacher, downloadFileInfo);
        }
    }

    /**
     * get Downloader single instance
     *
     * @param context Context
     * @return the Downloader single instance
     */
    public static DownloaderManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DownloaderManager.class) {
                if (sInstance == null) {
                    sInstance = new DownloaderManager(context);
                }
            }
        }
        return sInstance;
    }

    /**
     * start/continue a download
     *
     * @param url file url
     */
    public void start(String url) {
        getDownloadTaskManager().start(url, null);
    }

    /**
     * init with a Configuration
     *
     * @param configuration Configuration
     */
    public void init(DownloaderConfiguration configuration) {
        synchronized (mInitLock) {
            this.mConfiguration = configuration;
        }
    }

    /**
     * whether the file-downloader is initialized
     *
     * @return true means initialized
     */
    public boolean isInit() {
        synchronized (mInitLock) {
            return mConfiguration != null;
        }
    }

    /**
     * check whether the file-downloader is initialized
     */
    private void checkInit() {
        if (!isInit()) {
            throw new IllegalStateException("Please init the downloader by using " + Downloader.class
                    .getSimpleName() + ".init(DownloaderConfiguration)");
        }
    }

    private DownloadTaskManager getDownloadTaskManager() {
        checkInit();
        if (mDownloadTaskManager == null) {
            mDownloadTaskManager = new DownloadTaskManager(mConfiguration, mDownloadFileCacher);
        }
        return mDownloadTaskManager;
    }

}
