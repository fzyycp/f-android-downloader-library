package cn.faury.android.library.downloader.common;

import java.io.File;

/**
 * 网络文件基本信息
 */

public abstract class BaseUrlFileInfo {

    /**
     * file url
     */
    protected String url;
    /**
     * file total size
     */
    protected long fileSize;
    /**
     * file eTag
     */
    protected String eTag;
    /**
     * file last modified datetime(in server)
     */
    protected String lastModified;
    /**
     * accept range type
     */
    protected String acceptRangeType;
    /**
     * save file dir
     */
    protected String fileDir;
    /**
     * save file name
     */
    protected String fileName;
    /**
     * create download datetime, yyyy-MM-dd HH:mm:ss
     */
    protected String createDatetime;

    public String getUrl() {
        return url;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getETag() {
        return eTag;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getAcceptRangeType() {
        return acceptRangeType;
    }

    public String getFileDir() {
        return fileDir;
    }

    public String getFileName() {
        return fileName;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public String getFilePath() {
        return getFileDir() + File.separator + getFileName();
    }

    @Override
    public String toString() {
        return "BaseUrlFileInfo{" +
                "url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", eTag='" + eTag + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", acceptRangeType='" + acceptRangeType + '\'' +
                ", fileDir='" + fileDir + '\'' +
                ", fileName='" + fileName + '\'' +
                ", createDatetime='" + createDatetime + '\'' +
                '}';
    }
}
