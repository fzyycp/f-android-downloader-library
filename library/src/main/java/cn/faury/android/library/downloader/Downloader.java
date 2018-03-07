package cn.faury.android.library.downloader;

import android.content.Context;

import cn.faury.android.library.downloader.config.DownloaderConfiguration;

/**
 * 下载器
 */

public final class Downloader {

    private static DownloaderManager downloaderManager;

    public static void init(DownloaderConfiguration configuration) {
        if (configuration == null) {
            return;
        }
        Context context = configuration.getContext();
        downloaderManager = DownloaderManager.getInstance(context);
        downloaderManager.init(configuration);
    }

    /**
     * 启动一个下载任务
     *
     * @param url 下载路径
     */
    public void start(String url) {
        downloaderManager.start(url);
    }
}
