package cn.faury.android.library.downloader.common;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.File;

import cn.faury.android.library.common.util.DateUtils;
import cn.faury.android.library.common.util.FileUtils;
import cn.faury.android.library.common.util.StringUtils;
import cn.faury.android.library.common.util.UrlUtils;

/**
 * 下载文件数据库模型
 */

public class DownloadDbFileInfo extends BaseUrlFileInfo {


    /**
     * temp file suffix
     */
    private static final String TEMP_FILE_SUFFIX = "temp";

    /**
     * id
     */
    private Integer id;
    /**
     * downloadedSize
     */
    private long downloadedSize;
    /**
     * TempFileName
     */
    private String tempFileName;
    /**
     * download status
     */
    private int status = DownloadStatus.UNKNOWN;

    private DownloadDbFileInfo() {
    }

    /**
     * constructor of HttpDownloader, use {@link DetectUrlFileInfo} to create
     *
     * @param detectUrlFileInfo DetectUrlFile
     */
    public DownloadDbFileInfo(DetectUrlFileInfo detectUrlFileInfo) {
        this.url = detectUrlFileInfo.getUrl();
        this.fileName = detectUrlFileInfo.getFileName();
        this.fileSize = detectUrlFileInfo.getFileSize();
        this.eTag = detectUrlFileInfo.getETag();
        this.lastModified = detectUrlFileInfo.getLastModified();
        this.acceptRangeType = detectUrlFileInfo.getAcceptRangeType();
        this.fileDir = detectUrlFileInfo.getFileDir();
        this.tempFileName = fileName + "." + TEMP_FILE_SUFFIX;
        // this.status = Status.DOWNLOAD_STATUS_WAITING;// download status
        this.createDatetime = DateUtils.getCurrentDateString();
    }

    /**
     * constructor of HttpDownloader, use {@link Cursor} to create
     *
     * @param cursor database cursor
     */
    public DownloadDbFileInfo(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            int id = -1;
            String url = null;
            long downloadedSize = 0;
            long fileSize = 0;
            String eTag = null;
            String lastModified = null;
            String acceptRangeType = null;
            String fileDir = null;
            String tempFileName = null;
            String fileName = null;
            int status = DownloadStatus.UNKNOWN;
            String createDatetime = null;

            int columnIndex = -1;
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_ID);
            if (columnIndex != -1) {
                id = cursor.getInt(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_URL);
            if (columnIndex != -1) {
                url = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_DOWNLOADED_SIZE);
            if (columnIndex != -1) {
                downloadedSize = cursor.getLong(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_FILE_SIZE);
            if (columnIndex != -1) {
                fileSize = cursor.getLong(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_E_TAG);
            if (columnIndex != -1) {
                eTag = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_LAST_MODIFIED);
            if (columnIndex != -1) {
                lastModified = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_ACCEPT_RANGE_TYPE);
            if (columnIndex != -1) {
                acceptRangeType = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_FILE_DIR);
            if (columnIndex != -1) {
                fileDir = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_TEMP_FILE_NAME);
            if (columnIndex != -1) {
                tempFileName = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_FILE_NAME);
            if (columnIndex != -1) {
                fileName = cursor.getString(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_STATUS);
            if (columnIndex != -1) {
                status = cursor.getInt(columnIndex);
            }
            columnIndex = cursor.getColumnIndex(Table.COLUMN_NAME_OF_FIELD_CREATE_DATETIME);
            if (columnIndex != -1) {
                createDatetime = cursor.getString(columnIndex);
            }
            if (id > 0 && StringUtils.isNotEmpty(url)) {
                // init fields
                this.id = id;
                this.url = url;
                this.downloadedSize = downloadedSize;
                this.fileSize = fileSize;
                this.eTag = eTag;
                this.lastModified = lastModified;
                this.acceptRangeType = acceptRangeType;
                this.fileDir = fileDir;
                this.tempFileName = tempFileName;
                this.fileName = fileName;
                this.status = status;
                this.createDatetime = createDatetime;
            } else {
                throw new IllegalArgumentException("id or url from cursor illegal!");
            }
        } else {
            throw new NullPointerException("cursor illegal!");
        }
    }

    /**
     * update DownloadFileInfo with new DownloadFileInfo
     *
     * @param downloadFileInfo new DownloadFile
     */
    public void update(DownloadDbFileInfo downloadFileInfo) {
        if (downloadFileInfo.id != null && downloadFileInfo.id > 0) {
            this.id = downloadFileInfo.id;
        }
        if (UrlUtils.isUrl(downloadFileInfo.url)) {
            this.url = downloadFileInfo.url;
        }
        if (downloadFileInfo.downloadedSize > 0 && downloadFileInfo.downloadedSize != this.downloadedSize) {
            this.downloadedSize = downloadFileInfo.downloadedSize;
        }
        if (downloadFileInfo.fileSize > 0 && downloadFileInfo.fileSize != this.fileSize) {
            this.fileSize = downloadFileInfo.fileSize;
        }
        if (StringUtils.isNotEmpty(downloadFileInfo.eTag)) {
            this.eTag = downloadFileInfo.eTag;
        }
        if (StringUtils.isNotEmpty(downloadFileInfo.lastModified)) {
            this.lastModified = downloadFileInfo.lastModified;
        }
        if (StringUtils.isNotEmpty(downloadFileInfo.acceptRangeType)) {
            this.acceptRangeType = downloadFileInfo.acceptRangeType;
        }
        if (FileUtils.isFilePath(downloadFileInfo.fileDir)) {
            this.fileDir = downloadFileInfo.fileDir;
        }
        if (StringUtils.isNotEmpty(downloadFileInfo.tempFileName)) {
            this.tempFileName = downloadFileInfo.tempFileName;
        }
        if (StringUtils.isNotEmpty(downloadFileInfo.fileName)) {
            this.fileName = downloadFileInfo.fileName;
        }
        if (downloadFileInfo.status != this.status) {
            this.status = downloadFileInfo.status;
        }
        if (StringUtils.isNotEmpty(downloadFileInfo.createDatetime)) {
            this.createDatetime = downloadFileInfo.createDatetime;
        }
    }

    /**
     * get ContentValues for all fields
     */
    ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Table.COLUMN_NAME_OF_FIELD_URL, url);
        values.put(Table.COLUMN_NAME_OF_FIELD_DOWNLOADED_SIZE, downloadedSize);
        values.put(Table.COLUMN_NAME_OF_FIELD_FILE_SIZE, fileSize);
        values.put(Table.COLUMN_NAME_OF_FIELD_E_TAG, eTag);
        values.put(Table.COLUMN_NAME_OF_FIELD_LAST_MODIFIED, lastModified);
        values.put(Table.COLUMN_NAME_OF_FIELD_ACCEPT_RANGE_TYPE, acceptRangeType);
        values.put(Table.COLUMN_NAME_OF_FIELD_FILE_DIR, fileDir);
        values.put(Table.COLUMN_NAME_OF_FIELD_TEMP_FILE_NAME, tempFileName);
        values.put(Table.COLUMN_NAME_OF_FIELD_FILE_NAME, fileName);
        values.put(Table.COLUMN_NAME_OF_FIELD_STATUS, status);
        values.put(Table.COLUMN_NAME_OF_FIELD_CREATE_DATETIME, createDatetime);
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (StringUtils.isNotEmpty(url)) {
            if (o instanceof DownloadDbFileInfo) {
                DownloadDbFileInfo other = (DownloadDbFileInfo) o;
                return url.equals(other.url);
            }
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        if (StringUtils.isNotEmpty(url)) {
            return url.hashCode();
        }
        return super.hashCode();
    }

    public Integer getId() {
        return id;
    }

    public DownloadDbFileInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public DownloadDbFileInfo setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
        return this;
    }

    public String getTempFileName() {
        return tempFileName;
    }

    public DownloadDbFileInfo setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public DownloadDbFileInfo setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getTempFilePath() {
        return getFileDir() + File.separator + tempFileName;
    }

    @Override
    public String toString() {
        return "DownloadDbFileInfo{" +
                "id=" + id +
                ", downloadedSize=" + downloadedSize +
                ", tempFileName='" + tempFileName + '\'' +
                ", status=" + status +
                '}' + super.toString();
    }

    /**
     * {@link DownloadDbFileInfo} database table info
     */
    public static final class Table {

        /**
         * table name
         */
        public static final String TABLE_NAME_OF_DOWNLOAD_FILE = "tb_download_file";

        /**
         * id field name
         */
        public static final String COLUMN_NAME_OF_FIELD_ID = "_id";
        /**
         * url field name
         */
        public static final String COLUMN_NAME_OF_FIELD_URL = "url";
        /**
         * downloadedSize field name
         */
        public static final String COLUMN_NAME_OF_FIELD_DOWNLOADED_SIZE = "downloaded_size";
        /**
         * fileSize field name
         */
        public static final String COLUMN_NAME_OF_FIELD_FILE_SIZE = "file_size";
        /**
         * eTag field name
         */
        public static final String COLUMN_NAME_OF_FIELD_E_TAG = "e_tag";
        /**
         * last modified datetime(in server) field name
         */
        public static final String COLUMN_NAME_OF_FIELD_LAST_MODIFIED = "last_modified";
        /**
         * acceptRangeType field name
         */
        public static final String COLUMN_NAME_OF_FIELD_ACCEPT_RANGE_TYPE = "accept_range_type";
        /**
         * fileSize field name
         */
        public static final String COLUMN_NAME_OF_FIELD_FILE_DIR = "file_dir";
        /**
         * tempFileName field name
         */
        public static final String COLUMN_NAME_OF_FIELD_TEMP_FILE_NAME = "temp_file_name";
        /**
         * fileName field name
         */
        public static final String COLUMN_NAME_OF_FIELD_FILE_NAME = "file_name";
        /**
         * status field name
         */
        public static final String COLUMN_NAME_OF_FIELD_STATUS = "status";
        /**
         * create download datetime
         */
        public static final String COLUMN_NAME_OF_FIELD_CREATE_DATETIME = "create_datetime";

        /**
         * the sql to create table
         */
        public static String getCreateTableSql() {
            return "CREATE TABLE IF NOT EXISTS " //
                    + TABLE_NAME_OF_DOWNLOAD_FILE //
                    + "(" + COLUMN_NAME_OF_FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"//
                    + COLUMN_NAME_OF_FIELD_URL + " TEXT UNIQUE,"//
                    + COLUMN_NAME_OF_FIELD_DOWNLOADED_SIZE + " INTEGER,"//
                    + COLUMN_NAME_OF_FIELD_FILE_SIZE + " INTEGER,"//
                    + COLUMN_NAME_OF_FIELD_E_TAG + " TEXT,"//
                    + COLUMN_NAME_OF_FIELD_LAST_MODIFIED + " TEXT,"//
                    + COLUMN_NAME_OF_FIELD_ACCEPT_RANGE_TYPE + " TEXT,"//
                    + COLUMN_NAME_OF_FIELD_FILE_DIR + " TEXT,"//
                    + COLUMN_NAME_OF_FIELD_TEMP_FILE_NAME + " TEXT,"//
                    + COLUMN_NAME_OF_FIELD_FILE_NAME + " TEXT,"//
                    + COLUMN_NAME_OF_FIELD_STATUS + " INTEGER,"//
                    + COLUMN_NAME_OF_FIELD_CREATE_DATETIME + " TEXT" + ")";//
        }

        /**
         * 版本更新语句
         *
         * @return SQL
         */
        public static String getUpdateTableVersion1To2Sql() {
            return "";
        }
    }
}
