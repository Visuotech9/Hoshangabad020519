package com.visuotech.hoshangabad.Activities;

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

import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.NetworkConnection;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.visuotech.hoshangabad.MarshMallowPermission.READ_PHONE_STATE;

public class Act_splash extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    SessionParam sessionParam;
    Context context;
    MarshMallowPermission marshMallowPermission;
    public String id, device_id;
    Activity activity;
    private BaseRequest baseRequest;
    String splash  ="Yes";
    String other_device_active,user_typee,organization_id,user_id,login_status,payment_status,account_status;
    LinearLayout lin_spl_layout;

    Handler handler;
    Runnable refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_act_splash);



        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);
        context = this;
        user_typee= sessionParam.user_type;
        user_id= sessionParam.userId;
        organization_id=sessionParam.org_id;
        startTimer();
//        lin_spl_layout=findViewById(R.id.lin_spl_layout);
//        permissionPhone();


    }
//    private void permissionPhone(){
//        if (!marshMallowPermission.checkPermissionForPhoneState()) {
//            marshMallowPermission.requestPermissionForPhoneState();
//        } else {
//            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            }
//            device_id = TelephonyMgr.getDeviceId();
//
//            Log.d("API LEVEL>>>>>", String.valueOf(Build.VERSION.SDK_INT));
//            Log.d("API LEVEL LOLISPOP>>>>>", String.valueOf(Build.VERSION_CODES.N));
//
////            if (NetworkConnection.checkNetworkStatus(context) == true) {
////                api_loginStatus();
////            } else {
//////                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
////            }
//            startTimer();
//        }
//
//    }


    private void startTimer() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(Act_splash.this,Act_home.class);
                startActivity(intent);
            }

        }, SPLASH_TIME_OUT);

    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case READ_PHONE_STATE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    permissionPhone();
//                }else{
//                    alertDialog();
//                }
//                break;
//        }
//    }
//
//    public void api_loginStatus() {
//        baseRequest = new BaseRequest();
//        baseRequest.setBaseRequestListner(new RequestReciever() {
//            @Override
//            public void onSuccess(int requestCode, String Json, Object object) {
//
//              Intent intent=new Intent(Act_splash.this, Act_home.class);
//              startActivity(intent);
//
//            }
//
//            @Override
//            public void onFailure(int requestCode, String errorCode, String message) {
////                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onNetworkFailure(int requestCode, String message) {
////                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        RequestBody device_id_ = RequestBody.create(MediaType.parse("text/plain"), device_id);
//
//
//        baseRequest.callAPILoginStatus(1,"http://collectorexpress.in/",device_id_);
//
//    }
//    private void alertDialog() {
//        new AlertDialog.Builder(this)
//                .setIcon(R.drawable.ic_warning)
//                .setTitle("Attention")
//                .setCancelable(false)
//                .setMessage("You need grant READ_PHONE_STATE permission for device id.")
//                .setPositiveButton("Continue", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        permissionPhone();
//                    }
//                })
//                .show();
//    }
    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }


}
/*

 */