package com.visuotech.hoshangabad_election;

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

        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }
}
