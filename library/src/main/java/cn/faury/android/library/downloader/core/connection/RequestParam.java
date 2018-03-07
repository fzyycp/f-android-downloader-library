package cn.faury.android.library.downloader.core.connection;

import android.text.TextUtils;

import java.util.Map;

/**
 * 请求参数
 */

public class RequestParam {

    private String mUrl;
    private int mConnectTimeout = 15000; // 15s
    private String mCharset;
    private long mRangeStartPos = -1;
    private long mRangeEndPos = -1;
    private String mETag;
    private String mLastModified;
    private String mRequestMethod = "GET";// get default
    private Map<String, String> mHeaders;

    public RequestParam(String url, int connectTimeout, String charset) {
        mUrl = url;
        mConnectTimeout = connectTimeout;
        mCharset = charset;
    }

    public RequestParam(String url, int connectTimeout, String charset, long rangeStartPos, long rangeEndPos,
                        String ETag, String lastModified) {
        mUrl = url;
        mConnectTimeout = connectTimeout;
        mCharset = charset;
        mRangeStartPos = rangeStartPos;
        mRangeEndPos = rangeEndPos;
        mETag = ETag;
        mLastModified = lastModified;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    //        public void setConnectTimeout(int connectTimeout) {
    //            mConnectTimeout = connectTimeout;
    //        }
    //
    //        public void setCharset(String charset) {
    //            mCharset = charset;
    //        }
    //
    //        public void setRangeStartPos(long rangeStartPos) {
    //            mRangeStartPos = rangeStartPos;
    //        }
    //
    //        public void setRangeEndPos(long rangeEndPos) {
    //            mRangeEndPos = rangeEndPos;
    //        }
    //
    //        public void setETag(String ETag) {
    //            mETag = ETag;
    //        }
    //
    //        public void setLastModified(String lastModified) {
    //            mLastModified = lastModified;
    //        }

    public void setRequestMethod(String requestMethod) {
        if (TextUtils.isEmpty(requestMethod)) {
            return;
        }
        this.mRequestMethod = requestMethod;
    }

    public void setHeaders(Map<String, String> headers) {
        if (headers == null) {
            return;
        }
        mHeaders = headers;
    }

    @Override
    public String toString() {
        return "RequestParam{" +
                "mUrl='" + mUrl + '\'' +
                ", mConnectTimeout=" + mConnectTimeout +
                ", mCharset='" + mCharset + '\'' +
                ", mRangeStartPos=" + mRangeStartPos +
                ", mRangeEndPos=" + mRangeEndPos +
                ", mETag='" + mETag + '\'' +
                ", mLastModified='" + mLastModified + '\'' +
                ", mRequestMethod='" + mRequestMethod + '\'' +
                ", mHeaders=" + mHeaders +
                '}';
    }

}
