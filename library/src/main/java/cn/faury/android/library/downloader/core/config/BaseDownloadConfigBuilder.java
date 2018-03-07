package cn.faury.android.library.downloader.core.config;

/**
 * 基础下载配置Builder
 */
public class BaseDownloadConfigBuilder {

    /**
     * 最大重试次数：10
     */
    public static final int MAX_RETRY_DOWNLOAD_TIMES = 10;
    /**
     * 默认重试次数：1
     */
    public static final int DEFAULT_RETRY_DOWNLOAD_TIMES = 1;
    /**
     * 默认连接超时时间10秒
     */
    public static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;// 10s
    /**
     * 最小连接超时时间5秒
     */
    public static final int MIN_CONNECT_TIMEOUT = 5 * 1000;// 5s
    /**
     * 最大连接超时时间120秒
     */
    public static final int MAX_CONNECT_TIMEOUT = 120 * 1000;// 120s

    /**
     * 重试下载次数
     */
    protected int retryDownloadTimes;

    /**
     * 连接超时时间
     */
    protected int connectTimeout;

    /**
     * 构造函数
     */
    public BaseDownloadConfigBuilder() {
        retryDownloadTimes = DEFAULT_RETRY_DOWNLOAD_TIMES;
        connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    }

    /**
     * 获取重试次数
     *
     * @return 重试次数
     */
    public int getRetryDownloadTimes() {
        return retryDownloadTimes;
    }

    /**
     * 设置重试次数
     *
     * @param retryDownloadTimes 重试次数
     */
    public BaseDownloadConfigBuilder setRetryDownloadTimes(int retryDownloadTimes) {
        if (retryDownloadTimes < 0) {
            this.retryDownloadTimes = 0;
        } else if (retryDownloadTimes > MAX_RETRY_DOWNLOAD_TIMES) {
            this.retryDownloadTimes = MAX_RETRY_DOWNLOAD_TIMES;
        } else {
            this.retryDownloadTimes = retryDownloadTimes;
        }
        return this;
    }

    /**
     * 获取连接超时时间
     *
     * @return 连接超时时间
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * 设置连接超时时间
     *
     * @param connectTimeout 连接超时时间
     */
    public BaseDownloadConfigBuilder setConnectTimeout(int connectTimeout) {
        if (connectTimeout < MIN_CONNECT_TIMEOUT) {
            this.connectTimeout = MIN_CONNECT_TIMEOUT;
        } else if (connectTimeout > MAX_CONNECT_TIMEOUT) {
            this.connectTimeout = MAX_CONNECT_TIMEOUT;
        } else {
            this.connectTimeout = connectTimeout;
        }
        return this;
    }
}
