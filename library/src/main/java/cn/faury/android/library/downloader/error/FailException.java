package cn.faury.android.library.downloader.error;

/**
 * 失败异常，用于同步调用抛出的异常
 */
public abstract class FailException extends FailReason {

    public FailException(String type) {
        super(type);
    }

    public FailException(String detailMessage, String type) {
        super(detailMessage, type);
    }

    public FailException(String detailMessage, Throwable throwable, String type) {
        super(detailMessage, throwable, type);
    }

    public FailException(Throwable throwable, String type) {
        super(throwable, type);
    }

    public FailException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FailException(Throwable throwable) {
        super(throwable);
    }
}
