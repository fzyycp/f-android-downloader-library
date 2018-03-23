package cn.faury.android.demo.downloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import cn.faury.android.library.common.core.FCommonGlobalConfigure;
import cn.faury.android.library.common.helper.Logger;
import cn.faury.android.library.common.util.StorageUtils;
import cn.faury.android.library.downloader.DownloadFileInfo;
import cn.faury.android.library.downloader.DownloadStatusConfiguration;
import cn.faury.android.library.downloader.FileDownloadConfiguration;
import cn.faury.android.library.downloader.FileDownloader;
import cn.faury.android.library.downloader.listener.OnFileDownloadStatusListener;

public class MainActivity extends AppCompatActivity {

    public static final String singleUrl = "http://test.wassx.cn/ssk-ssqr-mobile-v2/qrcode/download?codeNumber=C201803051029440319553";
    //    public static final String singleUrl = "http://test.wassx.cn/ssk-ssqr-web/mobile/download/goodsContent?goodsId=68759&userId=27636";
    public static final String singleUrl_5MB = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2015/04/02/C20150402205352644702/2017121117263524949.mp4";
    public static final String singleUrl_58MB = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2014/08/08/C2014080814214367718/2017101914142237425.mp4";
    public static final String singleUrl_4MB = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2014/08/08/C2014080814214367718/2017101914235242842.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();

        final TextView output = findViewById(R.id.download_output);
        final Button single = findViewById(R.id.download_single_btn);
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                single.setEnabled(false);
                FileDownloader.start(singleUrl);
                FileDownloader.start(singleUrl_5MB);
                FileDownloader.start(singleUrl_58MB);
                FileDownloader.start(singleUrl_4MB);
            }
        });
        final Button stop = findViewById(R.id.download_stop_btn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileDownloader.pauseAll();
            }
        });
        FileDownloader.registerDownloadStatusListener(new OnFileDownloadStatusListener() {
            public static final String TAG = "FileDownloader";

            @Override
            public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG, "onFileDownloadStatusWaiting");
            }

            @Override
            public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG, "onFileDownloadStatusPreparing");
            }

            @Override
            public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG, "onFileDownloadStatusPrepared");
            }

            @Override
            public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {
                output.append(String.format("\n%s:%s:%s",downloadFileInfo.getFileName(),downloadSpeed,remainingTime));
                Logger.d(TAG, "onFileDownloadStatusDownloading:downloadSpeed=" + downloadSpeed + ",remainingTime=" + remainingTime);
            }

            @Override
            public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG, "onFileDownloadStatusPaused");
            }

            @Override
            public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
                output.append(String.format("\r\n文件名：%s，下载完成！",downloadFileInfo.getFileName()));
                Logger.d(TAG, "onFileDownloadStatusCompleted");
                Logger.e(TAG, "onFileDownloadStatusCompleted:" + downloadFileInfo.toString());
            }

            @Override
            public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
                Logger.d(TAG, "onFileDownloadStatusFailed:url" + url, failReason);
            }
        }, new DownloadStatusConfiguration.Builder()
                .build());
    }

    private void initConfig() {
        Logger.setLevel(Logger.LEVEL.DEBUG);
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);
        builder.configDebugMode(true)
                .configFileDownloadDir(StorageUtils.getStoragePackageDir(this) + File.separator + FCommonGlobalConfigure.DIR_DOWNLOAD)
        ;
        FileDownloader.init(builder.build());
    }


}
