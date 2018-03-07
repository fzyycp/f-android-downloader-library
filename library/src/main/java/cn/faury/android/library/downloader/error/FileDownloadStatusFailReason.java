package cn.faury.android.library.downloader.error;

/**
 *
 */

public class FileDownloadStatusFailReason extends HttpFailReason {
    /**
     * URL illegal
     */
    public static final String TYPE_URL_ILLEGAL = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_URL_ILLEGAL";
    /**
     * url over redirect count
     */
    public static final String TYPE_URL_OVER_REDIRECT_COUNT = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_URL_OVER_REDIRECT_COUNT";
    /**
     * bad http response code, not 2XX
     */
    public static final String TYPE_BAD_HTTP_RESPONSE_CODE = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_BAD_HTTP_RESPONSE_CODE";
    /**
     * the file need to download does not exist
     */
    public static final String TYPE_HTTP_FILE_NOT_EXIST = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_HTTP_FILE_NOT_EXIST";
    /**
     * file save path illegal
     */
    public static final String TYPE_FILE_SAVE_PATH_ILLEGAL = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_FILE_SAVE_PATH_ILLEGAL";
    /**
     * storage space can not write
     */
    public static final String TYPE_STORAGE_SPACE_CAN_NOT_WRITE = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_STORAGE_SPACE_CAN_NOT_WRITE";
    /**
     * rename temp file failed
     */
    public static final String TYPE_RENAME_TEMP_FILE_ERROR = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_RENAME_TEMP_FILE_ERROR";
    /**
     * storage space is full
     */
    public static final String TYPE_STORAGE_SPACE_IS_FULL = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_STORAGE_SPACE_IS_FULL";
    /**
     * the file need to save does not exist
     */
    public static final String TYPE_SAVE_FILE_NOT_EXIST = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_SAVE_FILE_NOT_EXIST";
    /**
     * url file does not detect
     */
    public static final String TYPE_FILE_NOT_DETECT = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_FILE_NOT_DETECT";
    /**
     * the download file error
     */
    public static final String TYPE_DOWNLOAD_FILE_ERROR = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_DOWNLOAD_FILE_ERROR";

    /**
     * the url file has been changed, need to re-download
     */
    public static final String TYPE_URL_FILE_CHANGED = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_URL_FILE_CHANGED";

    /**
     * file is downloading
     *
     * @deprecated not an error,not use since 0.2.0
     */
    @Deprecated
    public static final String TYPE_FILE_IS_DOWNLOADING = FileDownloadStatusFailReason.class.getName() +
            "_TYPE_FILE_IS_DOWNLOADING";

    public FileDownloadStatusFailReason(String url, String detailMessage, String type) {
        super(url, detailMessage, type);
    }

    public FileDownloadStatusFailReason(String url, Throwable throwable) {
        super(url, throwable);
    }

    @Override
    protected void onInitTypeWithFailReason(FailReason failReason) {
        super.onInitTypeWithFailReason(failReason);

        if (failReason == null) {
            return;
        }

        // other FailReason exceptions that need cast to FileDownloadStatusFailReason

        // cast HttpFailReason
        if (failReason instanceof HttpFailReason) {
            // cast HttpFailReason
            HttpFailReason httpFailReason = (HttpFailReason) failReason;
            String type = httpFailReason.getType();
            setType(type);

            if (isTypeInit()) {
                return;
            }

            // case others
            // case HttpDownloadException
            if (failReason instanceof HttpDownloadException) {

                HttpDownloadException httpDownloadException = (HttpDownloadException) failReason;
                type = httpDownloadException.getType();

                if (HttpDownloadException.TYPE_CONTENT_RANGE_VALIDATE_FAIL.equals(type)) {
                    // ignore
                } else if (HttpDownloadException.TYPE_ETAG_CHANGED.equals(type)) {
                    setType(TYPE_URL_FILE_CHANGED);
                } else if (HttpDownloadException.TYPE_REDIRECT_COUNT_OVER_LIMITS.equals(type)) {
                    setType(TYPE_URL_OVER_REDIRECT_COUNT);
                } else if (HttpDownloadException.TYPE_RESOURCES_SIZE_ILLEGAL.equals(type)) {
                    setType(TYPE_DOWNLOAD_FILE_ERROR);
                } else if (HttpDownloadException.TYPE_RESPONSE_CODE_ERROR.equals(type)) {
                    setType(TYPE_BAD_HTTP_RESPONSE_CODE);
                }
            }
            // cast DetectUrlFileFailReason
//            else if (failReason instanceof DetectUrlFileFailReason) {
//
//                DetectUrlFileFailReason detectUrlFileFailReason = (DetectUrlFileFailReason) failReason;
//                type = detectUrlFileFailReason.getType();
//
//                if (DetectUrlFileFailReason.TYPE_BAD_HTTP_RESPONSE_CODE.equals(type)) {
//                    setType(TYPE_BAD_HTTP_RESPONSE_CODE);
//                } else if (DetectUrlFileFailReason.TYPE_HTTP_FILE_NOT_EXIST.equals(type)) {
//                    setType(TYPE_FILE_NOT_DETECT);
//                } else if (DetectUrlFileFailReason.TYPE_URL_ILLEGAL.equals(type)) {
//                    setType(TYPE_URL_ILLEGAL);
//                } else if (DetectUrlFileFailReason.TYPE_URL_OVER_REDIRECT_COUNT.equals(type)) {
//                    setType(TYPE_URL_OVER_REDIRECT_COUNT);
//                }
//            }

        }
        // cast FileSaveException
        else if (failReason instanceof FileSaveException) {

            FileSaveException fileSaveException = (FileSaveException) failReason;
            String type = fileSaveException.getType();

            if (FileSaveException.TYPE_FILE_CAN_NOT_STORAGE.equals(type)) {
                setType(TYPE_STORAGE_SPACE_CAN_NOT_WRITE);
            } else if (FileSaveException.TYPE_RENAME_TEMP_FILE_ERROR.equals(type)) {
                setType(TYPE_RENAME_TEMP_FILE_ERROR);
            } else if (FileSaveException.TYPE_SAVER_HAS_BEEN_STOPPED.equals(type)) {
                // ignore
            } else if (FileSaveException.TYPE_TEMP_FILE_DOES_NOT_EXIST.equals(type)) {
                setType(TYPE_SAVE_FILE_NOT_EXIST);
            }
        }
    }

}
