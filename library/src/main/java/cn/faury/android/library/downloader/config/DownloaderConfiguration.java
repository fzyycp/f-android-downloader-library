package cn.faury.android.library.downloader.config;

import android.content.Context;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.faury.android.library.common.util.FileUtils;
import cn.faury.android.library.common.util.StringUtils;
import cn.faury.android.library.downloader.core.config.BaseDownloadConfigBuilder;

/**
 * 下载器配置
 */

public class DownloaderConfiguration {

    /**
     * 构造器
     */
    private Builder builder;

    /**
     * engine use for downloading file
     */
    private ExecutorService downloadEngine;
    /**
     * engine use for detecting url file
     */
    private ExecutorService detectEngine;
    /**
     * engine use for operate downloaded file such as delete, move, rename and other async operations
     */
    private ExecutorService operationEngine;

    /**
     * create default configuration,use {@link Builder#build()} to create recommended
     *
     * @param context Context
     * @return default configuration
     */
    public static DownloaderConfiguration createDefault(Context context) {
        return new Builder(context).build();
    }

    /**
     * constructor of FileDownloadConfiguration
     *
     * @param builder FileDownloadConfiguration builder
     */
    private DownloaderConfiguration(Builder builder) {
        if (builder == null) {
            throw new NullPointerException("builder can not be empty!");
        }
        this.builder = builder;
        this.downloadEngine = Executors.newFixedThreadPool(builder.downloadTaskSize);
        this.detectEngine = Executors.newCachedThreadPool(); // no limit
        this.operationEngine = Executors.newCachedThreadPool(); // no limit
    }


    /**
     * get Context
     *
     * @return Context
     */
    public Context getContext() {
        return builder.context;
    }

    /**
     * get FileDownloadDir
     *
     * @return FileDownloadDir
     */
    public String getDownloadDir() {
        return builder.getDownloadDir();
    }

    /**
     * get RetryDownloadTimes
     *
     * @return retry download times
     */
    public int getRetryDownloadTimes() {
        return builder.getRetryDownloadTimes();
    }

    /**
     * get connect timeout
     *
     * @return connect timeout
     */
    public int getConnectTimeout() {
        return builder.getConnectTimeout();
    }

    public ExecutorService getDownloadEngine() {
        return downloadEngine;
    }

    public ExecutorService getDetectEngine() {
        return detectEngine;
    }

    public ExecutorService getOperationEngine() {
        return operationEngine;
    }

    public static class Builder extends BaseDownloadConfigBuilder {

        /**
         * 最大下载任务数：10
         */
        public static final int MAX_DOWNLOAD_TASK_SIZE = 10;
        /**
         * 默认下载任务数：2
         */
        public static final int DEFAULT_DOWNLOAD_TASK_SIZE = 2;
        /**
         * 默认下载文件名
         */
        public static final String DEFAULT_DOWNLOAD_DIR_NAME = "downloader";

        /**
         * 上下文
         */
        private Context context;

        /**
         * 文件下载保存目录
         */
        private String downloadDir;
        /**
         * 下载任务数
         */
        private int downloadTaskSize;

        /**
         * 构造函数
         *
         * @param context 上下文
         */
        public Builder(Context context) {
            super();
            this.context = context.getApplicationContext();
            // default: /sdcard/Android/data/{package_name}/files/file_downloader
            try {
                downloadDir = this.context.getExternalFilesDir(null).getAbsolutePath() + File.separator +
                        DEFAULT_DOWNLOAD_DIR_NAME;
            } catch (Exception e) {
                // if there is not sdcard,use /data/data/{package_name}/files/file_downloader for the default
                downloadDir = FileUtils.getPrivateDir(this.context).getAbsolutePath() + File.separator + DEFAULT_DOWNLOAD_DIR_NAME;
            }
            downloadTaskSize = DEFAULT_DOWNLOAD_TASK_SIZE;
        }

        /**
         * 获取下载目录
         *
         * @return 下载目录
         */
        public String getDownloadDir() {
            return downloadDir;
        }

        /**
         * 设置下载目录
         *
         * @param downloadDir 下载目录
         * @return 当前对象
         */
        public Builder setDownloadDir(String downloadDir) {
            if (StringUtils.isNotEmpty(downloadDir)) {
                this.downloadDir = downloadDir;
                FileUtils.createFolder(downloadDir);
            }
            return this;
        }

        /**
         * 获取下载任务数
         *
         * @return 下载任务数
         */
        public int getDownloadTaskSize() {
            return downloadTaskSize;
        }

        /**
         * 设置下载任务数
         *
         * @param downloadTaskSize 下载任务数
         * @return 当前对象
         */
        public Builder setDownloadTaskSize(int downloadTaskSize) {
            this.downloadTaskSize = downloadTaskSize;
            if (downloadTaskSize < 0) {
                this.downloadTaskSize = 1;
            } else if (downloadTaskSize > MAX_DOWNLOAD_TASK_SIZE) {
                this.downloadTaskSize = MAX_DOWNLOAD_TASK_SIZE;
            } else {
                this.downloadTaskSize = downloadTaskSize;
            }
            return this;
        }

        /**
         * 设置重试次数
         *
         * @param retryDownloadTimes 重试次数
         */
        @Override
        public Builder setRetryDownloadTimes(int retryDownloadTimes) {
            super.setRetryDownloadTimes(retryDownloadTimes);
            return this;
        }

        /**
         * 设置连接超时时间
         *
         * @param connectTimeout 连接超时时间
         */
        @Override
        public Builder setConnectTimeout(int connectTimeout) {
            super.setConnectTimeout(connectTimeout);
            return this;
        }

        /**
         * build FileDownloadConfiguration
         *
         * @return FileDownloadConfiguration instance
         */
        public DownloaderConfiguration build() {
            return new DownloaderConfiguration(this);
        }


    }
}
