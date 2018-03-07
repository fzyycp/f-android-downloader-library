package cn.faury.android.library.downloader.common;

import cn.faury.android.library.common.util.DateUtils;
import cn.faury.android.library.common.util.FileUtils;
import cn.faury.android.library.common.util.StringUtils;
import cn.faury.android.library.common.util.UrlUtil;

/**
 * 探测到的网络文件信息
 */
public class DetectUrlFileInfo extends BaseUrlFileInfo {

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
    public DetectUrlFileInfo(String url, long fileSize, String eTag, String lastModified, String acceptRangeType, String
            fileDir, String fileName) {
        super();
        this.url = url;
        this.fileSize = fileSize;
        this.eTag = eTag;
        this.lastModified = lastModified;
        this.acceptRangeType = acceptRangeType;
        this.fileDir = fileDir;
        this.fileName = fileName;
        this.createDatetime = DateUtils.getCurrentDateString();
    }

    /**
     * update DetectUrlFileInfo with new DetectUrlFileInfo
     *
     * @param detectUrlFileInfo new DetectUrlFileInfo
     */
    public void update(DetectUrlFileInfo detectUrlFileInfo) {
        if (UrlUtil.isUrl(detectUrlFileInfo.url)) {
            this.url = detectUrlFileInfo.url;
        }
        if (detectUrlFileInfo.fileSize > 0 && detectUrlFileInfo.fileSize != this.fileSize) {
            this.fileSize = detectUrlFileInfo.fileSize;
        }
        if (StringUtils.isNotEmpty(detectUrlFileInfo.eTag)) {
            this.eTag = detectUrlFileInfo.eTag;
        }
        if (StringUtils.isNotEmpty(detectUrlFileInfo.lastModified)) {
            this.lastModified = detectUrlFileInfo.lastModified;
        }
        if (StringUtils.isNotEmpty(detectUrlFileInfo.acceptRangeType)) {
            this.acceptRangeType = detectUrlFileInfo.acceptRangeType;
        }
        if (FileUtils.isFilePath(detectUrlFileInfo.fileDir)) {
            this.fileDir = detectUrlFileInfo.fileDir;
        }
        if (StringUtils.isNotEmpty(detectUrlFileInfo.fileName)) {
            this.fileName = detectUrlFileInfo.fileName;
        }
        if (StringUtils.isNotEmpty(detectUrlFileInfo.createDatetime)) {
            this.createDatetime = detectUrlFileInfo.createDatetime;
        }
    }

    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
