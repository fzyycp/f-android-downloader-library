package cn.faury.android.library.downloader.common;

/**
 * 可停止接口
 */
public interface Stoppable {

    /**
     * stop
     */
    void stop();

    /**
     * whether is stopped
     *
     * @return true means stopped
     */
    boolean isStopped();

}
