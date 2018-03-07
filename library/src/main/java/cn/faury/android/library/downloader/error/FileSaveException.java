package cn.faury.android.library.downloader.error;

import java.io.IOException;

/**
 *
 */

public class FileSaveException extends FailException {
    /**
     * rename temp file failed
     */
    public static final String TYPE_RENAME_TEMP_FILE_ERROR = FileSaveException.class.getName() +
            "_TYPE_RENAME_TEMP_FILE_ERROR";
    /**
     * the file saver has been stopped
     */
    public static final String TYPE_SAVER_HAS_BEEN_STOPPED = FileSaveException.class.getName() +
            "_TYPE_SAVER_HAS_BEEN_STOPPED";
    /**
     * the temp file does not exist
     */
    public static final String TYPE_TEMP_FILE_DOES_NOT_EXIST = FileSaveException.class.getName() +
            "_TYPE_TEMP_FILE_DOES_NOT_EXIST";
    /**
     * file can not storage
     */
    public static final String TYPE_FILE_CAN_NOT_STORAGE = FileSaveException.class.getName() +
            "_TYPE_FILE_CAN_NOT_STORAGE";

    public FileSaveException(String detailMessage, String type) {
        super(detailMessage, type);
    }

    public FileSaveException(Throwable throwable) {
        super(throwable);
    }

    @Override
    protected void onInitTypeWithThrowable(Throwable throwable) {
        super.onInitTypeWithThrowable(throwable);

        if (isTypeInit() || throwable == null) {
            return;
        }

        if (throwable instanceof FailReason) {
            FailReason failReason = (FailReason) throwable;
            setTypeByOriginalClassInstanceType(failReason.getOriginalCause());
            if (isTypeInit()) {
                return;
            }
            // other FailReason exceptions that need cast to FileSaveException

        } else {
            setTypeByOriginalClassInstanceType(throwable);
        }
    }

    private void setTypeByOriginalClassInstanceType(Throwable throwable) {
        if (throwable == null) {
            return;
        }
        if (throwable instanceof IOException) {
            // setType(TYPE_FILE_CAN_NOT_STORAGE);
        }
    }

}
