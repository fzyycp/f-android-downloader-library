package cn.faury.android.library.downloader.listener;

import cn.faury.android.library.downloader.error.FailReason;
import cn.faury.android.library.downloader.error.FileDownloadStatusFailReason;

/**
 */
public interface OnStopFileDownloadTaskListener {

    /**
     * StopFileDownloadTaskSucceed
     *
     * @param url file url
     */
    void onStopFileDownloadTaskSucceed(String url);

    /**
     * StopFileDownloadTaskFailed
     *
     * @param url        file url
     * @param failReason fail reason
     */
    void onStopFileDownloadTaskFailed(String url, StopDownloadFileTaskFailReason failReason);

    /**
     * StopDownloadFileTaskFailReason
     */
    public static class StopDownloadFileTaskFailReason extends FileDownloadStatusFailReason {

        /**
         * the task has been stopped
         */
        public static final String TYPE_TASK_HAS_BEEN_STOPPED = StopDownloadFileTaskFailReason.class.getName() + 
                "_TYPE_TASK_HAS_BEEN_STOPPED";

        public StopDownloadFileTaskFailReason(String url, String detailMessage, String type) {
            super(url, detailMessage, type);
        }

        @Override
        protected void onInitTypeWithFailReason(FailReason failReason) {
            super.onInitTypeWithFailReason(failReason);

            if (failReason == null) {
                return;
            }

            // other FailReason exceptions that need cast to StopDownloadFileTaskFailReason

            // cast FileDownloadStatusFailReason
            if (failReason instanceof FileDownloadStatusFailReason) {
                FileDownloadStatusFailReason fileDownloadStatusFailReason = (FileDownloadStatusFailReason) failReason;
                setType(fileDownloadStatusFailReason.getType());// init type
            }
        }
    }
}
