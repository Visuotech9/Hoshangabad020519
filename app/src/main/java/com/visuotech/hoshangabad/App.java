package com.visuotech.hoshangabad;

import android.app.Application;
import android.os.SystemClock;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Don't do this! This is just so cold launches take some time

        if (NetworkConnection.checkNetworkStatus(getApplicationContext()) == true) {
            SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
        } else {
            Toast.makeText(getApplicationContext(),"Check internet connection",Toast.LENGTH_SHORT).show();
        }

    }
}
