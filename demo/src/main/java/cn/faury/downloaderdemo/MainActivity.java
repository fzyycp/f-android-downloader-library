package cn.faury.downloaderdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.faury.android.library.common.helper.Logger;
import cn.faury.android.library.downloader.FileDownloadConfiguration;
import cn.faury.android.library.downloader.FileDownloader;

public class MainActivity extends AppCompatActivity {

    public static final String singleUrl = "http://www.wassk.cn/Myftp/upload/jlsj/file/ssqr/fckUplodFiles/2015/04/02/C20150402205352644702/2017121117263524949.mp4";

    Button single;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initConfig();

        single = findViewById(R.id.download_single_btn);
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileDownloader.start(singleUrl);
            }
        });
    }

    private void initConfig() {
        Logger.setLevel(Logger.LEVEL.VERBOSE);
        FileDownloadConfiguration.Builder builder = new FileDownloadConfiguration.Builder(this);
        builder.configDebugMode(true)
                .configFileDownloadDir("cn.faury.android/demo/downloader");
        FileDownloader.init(builder.build());
    }


}
