package com.visuotech.hoshangabad_election.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.NetworkConnection;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.visuotech.hoshangabad_election.MarshMallowPermission.READ_PHONE_STATE;

public class Act_splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    Context context;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_splash);

         context = this;


    }

    private void startTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//
                Intent intent = new Intent(Act_splash.this, Act_home.class);
                startActivity(intent);

            }

        }, SPLASH_TIME_OUT);

    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }
}
