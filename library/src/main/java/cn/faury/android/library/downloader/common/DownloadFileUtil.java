package cn.faury.android.library.downloader.common;

import java.io.File;

import cn.faury.android.library.common.util.FileUtils;
import cn.faury.android.library.common.util.UrlUtils;
import cn.faury.android.library.downloader.record.Recorder;

/**
 */
public class DownloadFileUtil {

    /**
     * whether is downloading status
     *
     * @param downloadFileInfo download file
     * @return true means the download is downloading
     */
    public static boolean isDownloadingStatus(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        // only the status below is downloading
        switch (downloadFileInfo.getStatus()) {
            case DownloadStatus.WAITING:
            case DownloadStatus.RETRYING:
            case DownloadStatus.PREPARING:
            case DownloadStatus.PREPARED:
            case DownloadStatus.DOWNLOADING:
                return true;
        }
        return false;
    }

    /**
     * whether the download file can delete
     *
     * @param downloadFileInfo download file
     * @return true means can delete
     */
    public static boolean canDelete(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        if (isDownloadingStatus(downloadFileInfo)) {
            // only the file is downloading can NOT be deleted
            return false;
        }

        return true;
    }

    /**
     * whether the download file can move
     *
     * @param downloadFileInfo download file
     * @return true means can move
     */
    public static boolean canMove(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        if (isDownloadingStatus(downloadFileInfo)) {
            // only the file is downloading can NOT be moved
            return false;
        }

        return true;
    }

    /**
     * whether the download file can rename
     *
     * @param downloadFileInfo download file
     * @return true means can rename
     */
    public static boolean canRename(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        if (isDownloadingStatus(downloadFileInfo)) {
            // only the file is downloading can NOT be renamed
            return false;
        }

        return true;
    }

    /**
     * whether the download file is completed
     *
     * @param downloadFileInfo download file
     * @return true means is completed
     */
    public static boolean isCompleted(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        switch (downloadFileInfo.getStatus()) {
            case DownloadStatus.COMPLETED:
                return true;
        }

        return false;
    }

    /**
     * whether the url file is legal
     *
     * @param baseUrlFileInfo url file
     * @return true means is legal
     */
    public static boolean isLegal(BaseUrlFileInfo baseUrlFileInfo) {

        if (baseUrlFileInfo == null || !UrlUtils.isUrl(baseUrlFileInfo.getUrl())) {
            return false;
        }

        return true;
    }

    /**
     * whether has exception
     *
     * @param status download file status
     * @return true means has the exception
     */
    public static boolean hasException(int status) {

        switch (status) {
            case DownloadStatus.ERROR:
            case DownloadStatus.FILE_NOT_EXIST:
                return true;
        }

        return false;
    }

    /**
     * whether the temp file is exist
     *
     * @param downloadFileInfo download file
     * @return true means the saved file is exist
     */
    public static boolean isTempFileExist(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        if (downloadFileInfo.getDownloadedSize() >= 0) {
            return FileUtils.isFileExist(downloadFileInfo.getTempFilePath());
        }
        return false;
    }

    /**
     * try to rename temp file to save file
     *
     * @param downloadFileInfo download file
     * @return true means rename succeed or has been renamed
     */
    public static boolean tryToRenameTempFileToSaveFile(DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return false;
        }

        if (isCompleted(downloadFileInfo)) {
            File saveFile = new File(downloadFileInfo.getFilePath());
            if (saveFile.exists() && saveFile.length() == downloadFileInfo.getDownloadedSize() && saveFile.length
                    () == downloadFileInfo.getFileSize()) {
                return true;
            }
        } else {
            if (downloadFileInfo.getDownloadedSize() == downloadFileInfo.getFileSize()) {
                File tempFile = new File(downloadFileInfo.getTempFilePath());
                File saveFile = new File(downloadFileInfo.getFilePath());
                // the temp file exist, but the save not exist and it is really finished download, so rename the 
                // temp file to save file
                if (tempFile.exists() && tempFile.length() == downloadFileInfo.getDownloadedSize() && !saveFile
                        .exists()) {
                    // rename temp file to save file
                    boolean isSucceed = tempFile.renameTo(saveFile);
                    return isSucceed;

                }
            }
        }
        return false;
    }

    /**
     * try to recovery exception status
     *
     * @param record           the record
     * @param downloadFileInfo download file
     */
    public static void recoveryExceptionStatus(Recorder record, DownloadDbFileInfo downloadFileInfo) {

        if (!DownloadFileUtil.isLegal(downloadFileInfo)) {
            return;
        }

        String url = downloadFileInfo.getUrl();

        // check whether there is an exception status
        switch (downloadFileInfo.getStatus()) {
            // 1.exception status: downloading
            case DownloadStatus.WAITING:
            case DownloadStatus.PREPARING:
            case DownloadStatus.PREPARED:
            case DownloadStatus.DOWNLOADING:
                // recover paused
                try {
                    record.recordStatus(url, DownloadStatus.PAUSED, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            // 2.finished status, ignore
            case DownloadStatus.COMPLETED:
            case DownloadStatus.PAUSED:
            case DownloadStatus.ERROR:
                // ignore
                break;
            // 3.exception status: error but not finished
            case DownloadStatus.UNKNOWN:
            case DownloadStatus.RETRYING:
            default:
                // recover error
                try {
                    record.recordStatus(url, DownloadStatus.ERROR, 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


}
