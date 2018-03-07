package cn.faury.android.library.downloader.task;

import android.text.TextUtils;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.faury.android.library.common.helper.Logger;
import cn.faury.android.library.common.util.DeviceUtils;
import cn.faury.android.library.common.util.UrlUtils;
import cn.faury.android.library.downloader.cacher.DetectUrlFileCacher;
import cn.faury.android.library.downloader.common.DetectUrlFileInfo;
import cn.faury.android.library.downloader.common.DownloadDbFileInfo;
import cn.faury.android.library.downloader.common.DownloadFileUtil;
import cn.faury.android.library.downloader.common.Pauseable;
import cn.faury.android.library.downloader.config.DownloadStatusObserver;
import cn.faury.android.library.downloader.config.DownloaderConfiguration;
import cn.faury.android.library.downloader.config.DownloaderConfiguration.Builder;
import cn.faury.android.library.downloader.core.DownloadTask;
import cn.faury.android.library.downloader.core.config.DownloadConfiguration;
import cn.faury.android.library.downloader.error.FileDownloadStatusFailReason;
import cn.faury.android.library.downloader.listener.OnStopFileDownloadTaskListener;
import cn.faury.android.library.downloader.listener.OnTaskRunFinishListener;
import cn.faury.android.library.downloader.record.DownloadDbRecorder;

/**
 * 下载任务管理
 */

public class DownloadTaskManager implements Pauseable {
    public static final String TAG = DownloadTaskManager.class.getName();

    /**
     * FileDownload Configuration, which stored global configurations
     */
    private DownloaderConfiguration mConfiguration;
    /**
     * DownloadRecorder, which to record download files info
     */
    private DownloadDbRecorder mDownloadRecorder;
    /**
     * DetectUrlFileCacher, which stored detect url files
     */
    private DetectUrlFileCacher mDetectUrlFileCacher;

    /**
     * DownloadStatusObserver, which to observe download file status in the download tasks
     */
    private DownloadStatusObserver mDownloadStatusObserver;


    /**
     * all download tasks those are running
     */
    private Map<String, DownloadTask> mRunningDownloadTaskMap = new ConcurrentHashMap<String, DownloadTask>();

    private Object mDownloadTaskLock = new Object();

    /**
     * constructor of DownloadTaskManager
     *
     * @param configuration
     * @param downloadRecorder
     */
    public DownloadTaskManager(DownloaderConfiguration configuration, DownloadDbRecorder downloadRecorder) {

        this.mConfiguration = configuration;
        this.mDownloadRecorder = downloadRecorder;

        // init DetectUrlFileCacher
        mDetectUrlFileCacher = new DetectUrlFileCacher();
        // init DownloadFileStatusObserver
        mDownloadStatusObserver = new DownloadStatusObserver();
    }

    /**
     * whether is downloading
     *
     * @param url file url
     * @return true means the download task of the url is running
     */
    @Override
    public boolean isDownloading(String url) {
        return false;
    }

    /**
     * start a download task
     */
    private void startInternal(String callerUrl, DownloadDbFileInfo downloadFileInfo, DownloadConfiguration
            downloadConfiguration) {
        // start a download task
        addAndRunDownloadTask(callerUrl, downloadFileInfo, downloadConfiguration);
    }

    /**
     * start/continue a download
     * <br/>
     * if the caller cares for the download status, please register an listener before by using
     * <br/>
     *
     * @param url                   file url
     * @param downloadConfiguration download configuration
     */
    public void start(String url, final DownloadConfiguration downloadConfiguration) {
        DownloadDbFileInfo downloadFileInfo = getDownloadFile(url);
        // has been downloaded
        if (downloadFileInfo != null) {
            // continue download task
            startInternal(url, downloadFileInfo, downloadConfiguration);
        }
        // not download
        else {
            DetectUrlFileInfo detectUrlFileInfo = getDetectUrlFile(url);
            // detected
            if (detectUrlFileInfo != null) {
                // create download task
                createAndStartByDetectUrlFile(url, detectUrlFileInfo, downloadConfiguration);
            }
            // not detect
            else {
                final String finalUrl = url;
                // detect first
                detect(finalUrl, new OnDetectBigUrlFileListener() {
                    @Override
                    public void onDetectUrlFileFailed(String url, DetectBigUrlFileFailReason failReason) {
                        // notify download status caller
                        notifyDownloadStatusFailed(finalUrl, new OnFileDownloadStatusFailReason(finalUrl, failReason)
                                , false);
                    }

                    @Override
                    public void onDetectUrlFileExist(String url) {
                        // continue download task
                        startInternal(finalUrl, getDownloadFile(finalUrl), downloadConfiguration);
                    }

                    @Override
                    public void onDetectNewDownloadFile(String url, String fileName, String savedDir, long fileSize) {
                        // create and start download
                        createAndStart(finalUrl, savedDir, fileName, downloadConfiguration);
                    }
                }, downloadConfiguration);
            }
        }
    }

    /**
     * get DownloadFile by file url,private
     *
     * @param url file url
     * @return DownloadFile
     */
    private DownloadDbFileInfo getDownloadFile(String url) {
        return mDownloadRecorder.getDownloadFile(url);
    }

    /**
     * get DetectUrlFile by file url
     *
     * @param url file url
     * @return DetectUrlFile
     */
    private DetectUrlFileInfo getDetectUrlFile(String url) {
        return mDetectUrlFileCacher.getDetectUrlFile(url);
    }

    /**
     * pause a download
     *
     * @param url                            file url
     * @param onStopFileDownloadTaskListener OnStopFileDownloadTaskListener impl
     */
    @Override
    public void pause(String url, OnStopFileDownloadTaskListener onStopFileDownloadTaskListener) {

    }


    // --------------------------------------exec tasks--------------------------------------

    /**
     * start a download task
     */
    private void addAndRunDownloadTask(final String callerUrl, DownloadDbFileInfo downloadFileInfo,
                                       DownloadConfiguration downloadConfiguration) {

        FileDownloadStatusFailReason failReason = null;// null means there are not errors

        // 1.check url
        if (!UrlUtils.isUrl(callerUrl)) {
            failReason = new FileDownloadStatusFailReason(callerUrl, "url illegal !",
                    FileDownloadStatusFailReason.TYPE_URL_ILLEGAL);
        }

        // 2.network check
        if (failReason == null && !DeviceUtils.isNetworkAvailable(mConfiguration.getContext())) {
            failReason = new FileDownloadStatusFailReason(callerUrl, "network not available !",
                    FileDownloadStatusFailReason.TYPE_NETWORK_DENIED);
        }

        // 3.check downloadFileInfo
        if (failReason == null) {
            if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
                failReason = new FileDownloadStatusFailReason(callerUrl, "the download file does not exist or " +
                        "illegal !", FileDownloadStatusFailReason.TYPE_DOWNLOAD_FILE_ERROR);
            }
            if (failReason == null) {
                if (TextUtils.isEmpty(callerUrl) || !callerUrl.equals(downloadFileInfo.getUrl()) || !downloadFileInfo
                        .equals(getDownloadFile(callerUrl))) {
                    failReason = new FileDownloadStatusFailReason(callerUrl, "the download file does not exist or "
                            + "illegal !", FileDownloadStatusFailReason.TYPE_DOWNLOAD_FILE_ERROR);
                }
            }
        }

        // 4.check download size
        if (failReason == null && downloadFileInfo.getDownloadedSize() > downloadFileInfo.getFileSize()) {
            failReason = new FileDownloadStatusFailReason(callerUrl, "download size illegal, please delete or " +
                    "re-download !", FileDownloadStatusFailReason.TYPE_DOWNLOAD_FILE_ERROR);
        }

        // error occur
        if (failReason != null) {
            // notify caller
            notifyDownloadStatusFailed(callerUrl, failReason, downloadFileInfo != null);
            return;
        }

        synchronized (mDownloadTaskLock) {
            // check the old task
            DownloadTask taskInMap = mRunningDownloadTaskMap.get(callerUrl);
            if (taskInMap != null) {
                // running, ignore
                Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
                Logger.d(TAG, "mRunningDownloadTaskMap，忽略1：" + callerUrl + "，old task：" + taskInMap.hashCode() +
                        "，线程数：" + threads.size());
                return;
            }
        }

        // use global configuration first
        int retryDownloadTimes = mConfiguration.getRetryDownloadTimes();
        int connectTimeout = mConfiguration.getConnectTimeout();
        String requestMethod = DownloadConfiguration.DEFAULT_REQUEST_METHOD;
        Map<String, String> headers = null;

        if (downloadConfiguration != null) {
            int localRetryDownloadTimes = downloadConfiguration.getRetryDownloadTimes(callerUrl);
            if (localRetryDownloadTimes != Builder.DEFAULT_RETRY_DOWNLOAD_TIMES) {
                retryDownloadTimes = localRetryDownloadTimes;
            }
            int localConnectTimeout = downloadConfiguration.getConnectTimeout(callerUrl);
            if (localConnectTimeout != Builder.DEFAULT_CONNECT_TIMEOUT) {
                connectTimeout = localConnectTimeout;
            }
            String localRequestMethod = downloadConfiguration.getRequestMethod(callerUrl);
            if (TextUtils.isEmpty(localRequestMethod)) {
                localRequestMethod = DownloadConfiguration.DEFAULT_REQUEST_METHOD;
            }
            if (!TextUtils.isEmpty(localRequestMethod)) {
                requestMethod = localRequestMethod;
            }
            headers = downloadConfiguration.getHeaders(callerUrl);
        }

        // create retryable download task
        final RetryableDownloadTaskImpl downloadTask = new RetryableDownloadTaskImpl(FileDownloadTaskParam
                .createByDownloadFile(downloadFileInfo, requestMethod, headers), mDownloadRecorder,
                mDownloadStatusObserver);
        downloadTask.setCloseConnectionEngine(mConfiguration.getFileOperationEngine());
        // set RetryDownloadTimes
        downloadTask.setRetryDownloadTimes(retryDownloadTimes);
        downloadTask.setConnectTimeout(connectTimeout);
        downloadTask.setOnTaskRunFinishListener(new OnTaskRunFinishListener() {
            @Override
            public void onTaskRunFinish() {

                synchronized (mDownloadTaskLock) {
                    mRunningDownloadTaskMap.remove(downloadTask.getUrl());

                    Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
                    Log.e(TAG, "mRunningDownloadTaskMap，--移除--：" + downloadTask.getUrl() + "，task：" + downloadTask
                            .hashCode() + "，线程数：" + threads.size());
                }
            }
        });

        synchronized (mDownloadTaskLock) {

            // check the old task again
            DownloadTask taskInMap = mRunningDownloadTaskMap.get(downloadTask.getUrl());
            if (taskInMap != null) {
                // running, ignore
                Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
                Log.d(TAG, "mRunningDownloadTaskMap，忽略2：" + downloadTask.getUrl() + "，old task：" + taskInMap.hashCode
                        () + "，线程数：" + threads.size());
                return;
            }

            // record in the task map
            mRunningDownloadTaskMap.put(downloadTask.getUrl(), downloadTask);

            Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
            Log.d(TAG, "mRunningDownloadTaskMap，--增加--：" + downloadTask.getUrl() + "，task：" + downloadTask.hashCode()
                    + "，线程数：" + threads.size());
        }
        // exec the task
        mConfiguration.getFileDownloadEngine().execute(downloadTask);
    }
}
