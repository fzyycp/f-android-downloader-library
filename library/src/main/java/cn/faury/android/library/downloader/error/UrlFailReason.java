package cn.faury.android.library.downloader.error;

/**
 * Url类失败原因类
 */
public abstract class UrlFailReason extends FailReason {

    /**
     * fail url
     */
    private String url;

    public UrlFailReason(String url, String type) {
        super(type);
        init(url);
    }

    public UrlFailReason(String url, String detailMessage, String type) {
        super(detailMessage, type);
        init(url);
    }

    public UrlFailReason(String url, String detailMessage, Throwable throwable, String type) {
        super(detailMessage, throwable, type);
        init(url);
    }

    public UrlFailReason(String url, Throwable throwable, String type) {
        super(throwable, type);
        init(url);
    }

    public UrlFailReason(String url, String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
        init(url);
    }

    public UrlFailReason(String url, Throwable throwable) {
        super(throwable);
        init(url);
    }

    private void init(String url) {
        this.url = url;
    }

    // --------------------------------------getters & setters--------------------------------------

    /**
     * set url
     *
     * @param url the url
     */
    protected final void setUrl(String url) {
        this.url = url;
    }

    /**
     * get url
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }
}
