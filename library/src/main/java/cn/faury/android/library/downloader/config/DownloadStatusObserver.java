package cn.faury.android.library.downloader.config;

import android.util.Log;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import cn.faury.android.library.common.util.CollectionsUtils;
import cn.faury.android.library.common.util.UrlUtils;
import cn.faury.android.library.downloader.common.DownloadDbFileInfo;
import cn.faury.android.library.downloader.common.DownloadFileUtil;
import cn.faury.android.library.downloader.core.DownloadStatusConfiguration;
import cn.faury.android.library.downloader.error.FileDownloadStatusFailReason;
import cn.faury.android.library.downloader.listener.OnFileDownloadStatusListener;
import cn.faury.android.library.downloader.listener.OnRetryableFileDownloadStatusListener;

/**
 * FileDownloadStatus Observer
 */
public class DownloadStatusObserver implements OnRetryableFileDownloadStatusListener {

    private static final String TAG = DownloadStatusObserver.class.getSimpleName();

    // listeners
    private Set<DownloadStatusListenerInfo> mDownloadStatusListenerInfos = new 
            CopyOnWriteArraySet<DownloadStatusListenerInfo>();

    /**
     * add a OnFileDownloadStatusListener
     *
     * @param onFileDownloadStatusListener OnFileDownloadStatusListener
     * @param downloadStatusConfiguration  Configuration for the OnFileDownloadStatusListener impl
     */
    public void addOnFileDownloadStatusListener(OnFileDownloadStatusListener onFileDownloadStatusListener, 
                                                DownloadStatusConfiguration downloadStatusConfiguration) {
        if (onFileDownloadStatusListener == null) {
            return;
        }
        // find whether is added 
        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {
            if (listenerInfo == null) {
                return;
            }

            if (listenerInfo.mListener == onFileDownloadStatusListener) {
                return;// has been added
            }
        }

        // need add
        DownloadStatusListenerInfo listenerInfo = new DownloadStatusListenerInfo(downloadStatusConfiguration, 
                onFileDownloadStatusListener);
        mDownloadStatusListenerInfos.add(listenerInfo);

        String urls = (downloadStatusConfiguration != null && !CollectionsUtils.isEmpty(downloadStatusConfiguration
                .getListenUrls())) ? downloadStatusConfiguration.getListenUrls().toString() : "all";

        Log.i(TAG, "file-downloader-listener 添加【文件下载状态监听器】成功，该listener监听的urls：" + urls);
    }

    /**
     * remove a OnFileDownloadStatusListener
     *
     * @param onFileDownloadStatusListener added OnFileDownloadStatusListener impl
     */
    public void removeOnFileDownloadStatusListener(OnFileDownloadStatusListener onFileDownloadStatusListener) {
        if (onFileDownloadStatusListener == null) {
            return;
        }
        // find and remove
        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {
            if (listenerInfo == null) {
                // not need to remove, may has been removed
                continue;
            }
            if (listenerInfo.mListener == onFileDownloadStatusListener) {
                // find, remove
                mDownloadStatusListenerInfos.remove(listenerInfo);

                String urls = (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty
                        (listenerInfo.mDownloadStatusConfiguration.getListenUrls())) ? listenerInfo
                        .mDownloadStatusConfiguration.getListenUrls().toString() : "all";

                Log.i(TAG, "file-downloader-listener 移除【文件下载状态监听器】成功，该listener监听的urls：" + urls);
                break;
            }
        }
    }

    /**
     * notifyStatusWaiting
     */
    private void notifyStatusWaiting(DownloadDbFileInfo downloadFileInfo, OnFileDownloadStatusListener listener) {
        // main thread notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusWaiting(downloadFileInfo, listener);

        String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为等待】，文件的url：" + url);
    }

    /**
     * notifyStatusRetrying
     */
    private void notifyStatusRetrying(DownloadDbFileInfo downloadFileInfo, int retryTimes, OnFileDownloadStatusListener
            listener) {
        if (listener instanceof OnRetryableFileDownloadStatusListener) {
            // main thread notify caller
            MainThreadHelper.onFileDownloadStatusRetrying(downloadFileInfo,
                    retryTimes, (OnRetryableFileDownloadStatusListener) listener);

            String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

            Log.i(TAG, "file-downloader-listener 通知【文件下载状态为重试】，重试次数：" + retryTimes + "，文件的url：" + url);
        }
    }

    /**
     * notifyStatusPreparing
     */
    private void notifyStatusPreparing(DownloadDbFileInfo downloadFileInfo, OnFileDownloadStatusListener listener) {
        // main thread notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusPreparing(downloadFileInfo, listener);

        String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为准备中（正在连接）】，文件的url：" + url);
    }

    /**
     * notifyStatusPrepared
     */
    private void notifyStatusPrepared(DownloadDbFileInfo downloadFileInfo, OnFileDownloadStatusListener listener) {
        // main thread notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusPrepared(downloadFileInfo, listener);

        String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为已准备（已连接）】，文件的url：" + url);
    }

    /**
     * notifyStatusDownloading
     */
    private void notifyStatusDownloading(DownloadDbFileInfo downloadFileInfo, float downloadSpeed, long remainingTime, 
                                         OnFileDownloadStatusListener listener) {
        // notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusDownloading(downloadFileInfo, 
                downloadSpeed, remainingTime, listener);

        String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为正在下载】，文件的url：" + url);
    }

    /**
     * notifyStatusPaused
     */
    private void notifyStatusPaused(DownloadDbFileInfo downloadFileInfo, OnFileDownloadStatusListener listener) {
        // main thread notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusPaused(downloadFileInfo, listener);

        String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为暂停】，文件的url：" + url);
    }

    /**
     * notifyStatusCompleted
     */
    private void notifyStatusCompleted(DownloadDbFileInfo downloadFileInfo, OnFileDownloadStatusListener listener) {
        // main thread notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusCompleted(downloadFileInfo, listener);

        String url = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为完成】，文件的url：" + url);
    }

    /**
     * notifyStatusFailed
     */
    private void notifyStatusFailed(String url, DownloadDbFileInfo downloadFileInfo, FileDownloadStatusFailReason 
            failReason, OnFileDownloadStatusListener listener) {
        // main thread notify caller
        OnFileDownloadStatusListener.MainThreadHelper.onFileDownloadStatusFailed(url, downloadFileInfo, failReason, 
                listener);

        String downloadFileUrl = downloadFileInfo != null ? downloadFileInfo.getUrl() : "unknown";
        String failMsg = failReason != null ? failReason.getMessage() : "unknown";

        Log.i(TAG, "file-downloader-listener 通知【文件下载状态为失败】，文件的url：" + url + "，downloadFileUrl：" + downloadFileUrl +
                "，失败原因：" + failMsg);
    }

    @Override
    public void onFileDownloadStatusWaiting(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusWaiting(downloadFileInfo, listenerInfo.mListener);
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusWaiting(downloadFileInfo, listenerInfo.mListener);
            }
        }
    }

    @Override
    public void onFileDownloadStatusRetrying(DownloadDbFileInfo downloadFileInfo, int retryTimes) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this || !
                    (listenerInfo.mListener instanceof OnRetryableFileDownloadStatusListener)) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusRetrying(downloadFileInfo, retryTimes, listenerInfo.mListener);
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusRetrying(downloadFileInfo, retryTimes, listenerInfo.mListener);
            }
        }
    }


    @Override
    public void onFileDownloadStatusPreparing(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusPreparing(downloadFileInfo, listenerInfo.mListener);
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusPreparing(downloadFileInfo, listenerInfo.mListener);
            }
        }
    }

    @Override
    public void onFileDownloadStatusPrepared(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusPrepared(downloadFileInfo, listenerInfo.mListener);
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusPrepared(downloadFileInfo, listenerInfo.mListener);
            }
        }
    }

    @Override
    public void onFileDownloadStatusDownloading(DownloadDbFileInfo downloadFileInfo, float downloadSpeed, long 
            remainingTime) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }

                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusDownloading(downloadFileInfo, downloadSpeed, remainingTime, listenerInfo.mListener);
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusDownloading(downloadFileInfo, downloadSpeed, remainingTime, listenerInfo.mListener);
            }
        }
    }

    @Override
    public void onFileDownloadStatusPaused(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusPaused(downloadFileInfo, listenerInfo.mListener);
                        // remove the listener
                        if (listenerInfo.mDownloadStatusConfiguration.isAutoRelease()) {
                            mDownloadStatusListenerInfos.remove(listenerInfo);
                        }
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusPaused(downloadFileInfo, listenerInfo.mListener);
            }
        }
    }

    @Override
    public void onFileDownloadStatusCompleted(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusCompleted(downloadFileInfo, listenerInfo.mListener);
                        // remove the listener
                        if (listenerInfo.mDownloadStatusConfiguration.isAutoRelease()) {
                            mDownloadStatusListenerInfos.remove(listenerInfo);
                        }
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusCompleted(downloadFileInfo, listenerInfo.mListener);
            }
        }
    }

    @Override
    public void onFileDownloadStatusFailed(String url, DownloadDbFileInfo downloadFileInfo, 
                                           FileDownloadStatusFailReason failReason) {

        if (!UrlUtils.isUrl(url)) {
            return;
        }

        for (DownloadStatusListenerInfo listenerInfo : mDownloadStatusListenerInfos) {

            if (listenerInfo == null || listenerInfo.mListener == null || listenerInfo.mListener == this) {
                continue;
            }

            // notify match url listeners
            if (listenerInfo.mDownloadStatusConfiguration != null && !CollectionsUtils.isEmpty(listenerInfo
                    .mDownloadStatusConfiguration.getListenUrls())) {
                for (String listenUrl : listenerInfo.mDownloadStatusConfiguration.getListenUrls()) {
                    if (!UrlUtils.isUrl(listenUrl)) {
                        continue;
                    }
                    if (url.equals(listenUrl) || url.trim().equals(listenUrl.trim())) {
                        // find match url, notify caller
                        notifyStatusFailed(url, downloadFileInfo, failReason, listenerInfo.mListener);
                        // remove the listener
                        if (listenerInfo.mDownloadStatusConfiguration.isAutoRelease()) {
                            mDownloadStatusListenerInfos.remove(listenerInfo);
                        }
                    }
                }
            }
            // Configuration or ListenUrls is null or empty, notify all
            else {
                // global register listener, notify all callers
                notifyStatusFailed(url, downloadFileInfo, failReason, listenerInfo.mListener);
            }
        }
    }

    /**
     * release
     */
    public void release() {
        mDownloadStatusListenerInfos.clear();
    }

    /**
     * DownloadStatusListenerInfo
     */
    private static class DownloadStatusListenerInfo {

        private DownloadStatusConfiguration mDownloadStatusConfiguration;
        private OnFileDownloadStatusListener mListener;

        public DownloadStatusListenerInfo(DownloadStatusConfiguration downloadStatusConfiguration, 
                                          OnFileDownloadStatusListener listener) {
            mDownloadStatusConfiguration = downloadStatusConfiguration;
            mListener = listener;
        }
    }

}