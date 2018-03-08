package cn.faury.android.library.downloader.file_download;

import android.text.TextUtils;

import cn.faury.android.library.common.util.DateUtils;
import cn.faury.android.library.common.util.FileUtils;
import cn.faury.android.library.common.util.UrlUtils;
import cn.faury.android.library.downloader.base.BaseUrlFileInfo;

/**
 * DetectUrlFile Info
 * <br/>
 * 探测到的网络文件信息
 *
 * @author wlf(Andy)
 * @email 411086563@qq.com
 */
public class DetectUrlFileInfo extends BaseUrlFileInfo {

    @SuppressWarnings("unused")
    private DetectUrlFileInfo() {
    }

    /**
     * constructor of DetectUrlFileInfo
     *
     * @param url             file url
     * @param fileSize        file size
     * @param eTag            file e tag
     * @param lastModified    file last modified datetime(in server)
     * @param acceptRangeType accept range type
     * @param fileDir         file dir
     * @param fileName        file name
     */
    DetectUrlFileInfo(String url, long fileSize, String eTag, String lastModified, String acceptRangeType, String 
            fileDir, String fileName) {
        super();
        this.mUrl = url;
        this.mFileSize = fileSize;
        this.mETag = eTag;
        this.mLastModified = lastModified;
        this.mAcceptRangeType = acceptRangeType;
        this.mFileDir = fileDir;
        this.mFileName = fileName;
        this.mCreateDatetime = DateUtils.getCurrentDateString();
    }

    /**
     * update DetectUrlFileInfo with new DetectUrlFileInfo
     *
     * @param detectUrlFileInfo new DetectUrlFileInfo
     */
    void update(DetectUrlFileInfo detectUrlFileInfo) {
        if (UrlUtils.isUrl(detectUrlFileInfo.mUrl)) {
            this.mUrl = detectUrlFileInfo.mUrl;
        }
        if (detectUrlFileInfo.mFileSize > 0 && detectUrlFileInfo.mFileSize != this.mFileSize) {
            this.mFileSize = detectUrlFileInfo.mFileSize;
        }
        if (!TextUtils.isEmpty(detectUrlFileInfo.mETag)) {
            this.mETag = detectUrlFileInfo.mETag;
        }
        if (!TextUtils.isEmpty(detectUrlFileInfo.mLastModified)) {
            this.mLastModified = detectUrlFileInfo.mLastModified;
        }
        if (!TextUtils.isEmpty(detectUrlFileInfo.mAcceptRangeType)) {
            this.mAcceptRangeType = detectUrlFileInfo.mAcceptRangeType;
        }
        if (FileUtils.isFilePath(detectUrlFileInfo.mFileDir)) {
            this.mFileDir = detectUrlFileInfo.mFileDir;
        }
        if (!TextUtils.isEmpty(detectUrlFileInfo.mFileName)) {
            this.mFileName = detectUrlFileInfo.mFileName;
        }
        if (!TextUtils.isEmpty(detectUrlFileInfo.mCreateDatetime)) {
            this.mCreateDatetime = detectUrlFileInfo.mCreateDatetime;
        }
    }

    void setFileDir(String fileDir) {
        this.mFileDir = fileDir;
    }

    void setFileName(String fileName) {
        this.mFileName = fileName;
    }
}
