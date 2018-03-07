package cn.faury.android.library.downloader.record;

/**
 *
 */

public interface Recorder {

    /**
     * record status
     *
     * @param url          download url
     * @param status       record status
     * @param increaseSize increased size since last record
     * @throws Exception any fail exception during recording status
     */
    void recordStatus(String url, int status, int increaseSize) throws Exception;

}
