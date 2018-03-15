package cn.faury.android.demo.downloader;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 *
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
        }
    }

}
