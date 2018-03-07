package cn.faury.android.library.downloader.cacher;

import android.app.DownloadManager;
import android.content.Context;

import java.util.List;

import cn.faury.android.library.downloader.common.DownloadDbFileInfo;
import cn.faury.android.library.downloader.config.DownloadDbHelper;
import cn.faury.android.library.downloader.record.DownloadDbRecorderDeleter;
import cn.faury.android.library.downloader.record.DownloadDbRecorderMover;
import cn.faury.android.library.downloader.record.DownloadDbRecorderRenamer;
import cn.faury.android.library.downloader.record.DownloadDbRecorderReseter;

/**
 * 下载文件缓存器
 */

public class DownloadCacher implements DownloadDbRecorderReseter,DownloadDbRecorderMover,DownloadDbRecorderRenamer,DownloadDbRecorderDeleter {

    // db helper
    private DownloadDbHelper downloadDbHelper;

    // download file change observer
    private DownloadFileChangeObserver mDownloadFileChangeObserver;

    public DownloadCacher(Context context) {
        downloadDbHelper = new DownloadDbHelper(context);
        mDownloadFileChangeObserver = new DownloadFileChangeObserver();
        initDownloadFileInfoMapFromDb();
    }

    /**
     * delete download file
     *
     * @param url download url
     * @throws Exception any exception during delete
     */
    @Override
    public void deleteDownloadFile(String url) throws Exception {

    }

    /**
     * move download file name
     *
     * @param url        download url
     * @param newDirPath new file name
     * @throws Exception any exception during move
     */
    @Override
    public void moveDownloadFile(String url, String newDirPath) throws Exception {

    }

    /**
     * rename download file name
     *
     * @param url         download url
     * @param newFileName new file name
     * @throws Exception any exception during rename
     */
    @Override
    public void renameDownloadFile(String url, String newFileName) throws Exception {

    }

    /**
     * record status
     *
     * @param url          download url
     * @param status       record status
     * @param increaseSize increased size since last record
     * @throws Exception any fail exception during recording status
     */
    @Override
    public void recordStatus(String url, int status, int increaseSize) throws Exception {

    }

    /**
     * get DownloadFile by url
     *
     * @param url the url
     * @return DownloadFile recorded
     */
    @Override
    public DownloadDbFileInfo getDownloadFile(String url) {
        return null;
    }

    /**
     * get all DownloadFiles
     *
     * @return all DownloadFile recorded
     */
    @Override
    public List<DownloadDbFileInfo> getDownloadFiles() {
        return null;
    }

    /**
     * record download file
     *
     * @param url        file url
     * @param deleteMode true means delete all resource
     * @throws Exception any exception during record
     */
    @Override
    public void resetDownloadFile(String url, boolean deleteMode) throws Exception {

    }

    /**
     * reset download size
     *
     * @param url          download url
     * @param downloadSize the downloadSize reset to
     * @throws Exception any fail exception during recording status
     */
    @Override
    public void resetDownloadSize(String url, long downloadSize) throws Exception {

    }

    @Override
    public DownloadDbFileInfo createDownloadFileInfo(DownloadDbFileInfo detectUrlFileInfo) {
        return null;
    }
}
