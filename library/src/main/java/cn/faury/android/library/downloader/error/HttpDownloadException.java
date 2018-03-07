package cn.faury.android.library.downloader.error;

/**
 *
 */

public class HttpDownloadException extends HttpFailReason {
    /**
     * http redirect times over limits
     */
    public static final String TYPE_REDIRECT_COUNT_OVER_LIMITS = HttpDownloadException.class.getName() +
            "_TYPE_REDIRECT_COUNT_OVER_LIMITS";
    /**
     * resources size illegal
     */
    public static final String TYPE_RESOURCES_SIZE_ILLEGAL = HttpDownloadException.class.getName() +
            "_TYPE_RESOURCES_SIZE_ILLEGAL";
    /**
     * eTag changed
     */
    public static final String TYPE_ETAG_CHANGED = HttpDownloadException.class.getName() + "_TYPE_ETAG_CHANGED";
    /**
     * contentRange validate fail
     */
    public static final String TYPE_CONTENT_RANGE_VALIDATE_FAIL = HttpDownloadException.class.getName() +
            "_TYPE_CONTENT_RANGE_VALIDATE_FAIL";
    /**
     * ResponseCode error,can not read server data
     */
    public static final String TYPE_RESPONSE_CODE_ERROR = HttpDownloadException.class.getName() +
            "_TYPE_RESPONSE_CODE_ERROR";

    public HttpDownloadException(String url, String detailMessage, String type) {
        super(url, detailMessage, type);
    }

    public HttpDownloadException(String url, Throwable throwable) {
        super(url, throwable);
    }

    @Override
    protected void onInitTypeWithFailReason(FailReason failReason) {
        super.onInitTypeWithFailReason(failReason);

        if (failReason == null) {
            return;
        }

        // other FailReason exceptions that need cast to HttpDownloadException

        // cast FileSaveException
        if (failReason instanceof FileSaveException) {

            FileSaveException fileSaveException = (FileSaveException) failReason;
            String type = fileSaveException.getType();

            if (FileSaveException.TYPE_FILE_CAN_NOT_STORAGE.equals(type)) {
                // ignore
            } else if (FileSaveException.TYPE_RENAME_TEMP_FILE_ERROR.equals(type)) {
                // ignore
            } else if (FileSaveException.TYPE_SAVER_HAS_BEEN_STOPPED.equals(type)) {
                // ignore
            } else if (FileSaveException.TYPE_TEMP_FILE_DOES_NOT_EXIST.equals(type)) {
                // ignore
            }
        }
    }

}
