package cn.faury.android.library.downloader.core.task;

import cn.faury.android.library.downloader.common.Stoppable;
import cn.faury.android.library.downloader.listener.OnStopFileDownloadTaskListener;
import cn.faury.android.library.downloader.listener.OnTaskRunFinishListener;

/**
 * DownloadTask interface
 */
public interface DownloadTask extends Runnable, Stoppable {

    /**
     * get download url of the task
     *
     * @return download url
     */
    String getUrl();

    /**
     * set StopFileDownloadTaskListener
     *
     * @param onStopFileDownloadTaskListener OnStopFileDownloadTaskListener
     */
     void setOnStopFileDownloadTaskListener(OnStopFileDownloadTaskListener onStopFileDownloadTaskListener);

    /**
     * set TaskRunFinishListener
     *
     * @param onTaskRunFinishListener
     */
    void setOnTaskRunFinishListener(OnTaskRunFinishListener onTaskRunFinishListener);
}
