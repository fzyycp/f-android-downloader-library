package cn.faury.android.demo.downloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.File;

import cn.faury.android.library.common.core.FCommonGlobalConfigure;
import cn.faury.android.library.common.helper.Logger;
import cn.faury.android.library.common.util.StorageUtils;
import cn.faury.android.library.downloader.DownloadFileInfo;
import cn.faury.android.library.downloader.FileDownloadConfiguration;
import cn.faury.android.library.downloader.FileDownloader;
import cn.faury.android.library.downloader.listener.OnFileDownloadStatusListener;

public class MainActivity extends AppCompatActivity {

    public static final String singleUrl1 = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2015/04/02/C20150402205352644702/2017121117263524949.mp4";
    public static final String singleUrl2 = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2014/08/08/C2014080814214367718/2017101914142237425.mp4";
    public static final String singleUrl3 = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2014/08/08/C2014080814214367718/2017101914235242842.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();

        Button single = findViewById(R.id.download_single_btn);
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileDownloader.start(singleUrl1);
                FileDownloader.start(singleUrl2);
                FileDownloader.start(singleUrl3);
            }
        });
    }

    private void initConfig() {
        Logger.setLevel(Logger.LEVEL.DEBUG);
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);
        builder.configDebugMode(true)
                .configFileDownloadDir(StorageUtils.getStoragePackageDir(this) + File.separator + FCommonGlobalConfigure.DIR_DOWNLOAD)
                ;
        FileDownloader.init(builder.build());
        FileDownloader.registerDownloadStatusListener(new OnFileDownloadStatusListener() {
            public static final String TAG = "FileDownloader";

            @Override
            public void onFileDownloadStatusWaiting(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG,"onFileDownloadStatusWaiting");
            }

            @Override
            public void onFileDownloadStatusPreparing(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG,"onFileDownloadStatusPreparing");
            }

            @Override
            public void onFileDownloadStatusPrepared(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG,"onFileDownloadStatusPrepared");
            }

            @Override
            public void onFileDownloadStatusDownloading(DownloadFileInfo downloadFileInfo, float downloadSpeed, long remainingTime) {
                Logger.d(TAG,"onFileDownloadStatusDownloading:downloadSpeed="+downloadSpeed+",remainingTime="+remainingTime);
            }

            @Override
            public void onFileDownloadStatusPaused(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG,"onFileDownloadStatusPaused");
            }

            @Override
            public void onFileDownloadStatusCompleted(DownloadFileInfo downloadFileInfo) {
                Logger.d(TAG,"onFileDownloadStatusCompleted");
            }

            @Override
            public void onFileDownloadStatusFailed(String url, DownloadFileInfo downloadFileInfo, FileDownloadStatusFailReason failReason) {
                Logger.d(TAG,"onFileDownloadStatusFailed:url"+url,failReason);
            }
        });
    }


}
