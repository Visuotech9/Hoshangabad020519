package com.visuotech.hoshangabad_election.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.Activities.Election.Act_election;
import com.visuotech.hoshangabad_election.HomeWatcher;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.NetworkConnection;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.VersionChecker;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.visuotech.hoshangabad_election.MarshMallowPermission.READ_PHONE_STATE;

public class Act_home extends AppCompatActivity {
    LinearLayout lay1,lay2,lay3,lay4,lay5,lay6,lay13,lay12,lay11,lin_spl_layout;
    public boolean datafinish = false;
    Button btn_follow;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    TextView scrollingText;
    String currentVersion,latestVersion;
    HomeWatcher mhome = new HomeWatcher(Act_home.this);
    public String id, device_id,fcm_token;
//    View lin_spl_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-------------------------toolbar------------------------------------------



        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        fcm_token=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");




        lay1=findViewById(R.id.lay1);
        lay2=findViewById(R.id.lay2);
        lay3=findViewById(R.id.lay3);
        lay4=findViewById(R.id.lay4);
        lay5=findViewById(R.id.lay5);
        lay6=findViewById(R.id.lay6);
        lay11=findViewById(R.id.lay11);
        lay12=findViewById(R.id.lay12);
        lay13=findViewById(R.id.lay13);
        lin_spl_layout=findViewById(R.id.lin_spl_layout);


        Log.e("FCM<>>>>>>>",fcm_token);
        scrollingText = (TextView)findViewById(R.id.scrollingtext);
        scrollingText.setText(Html.fromHtml("निर्वाचन संबंधित जानकारी एवं मतदाता सहायता के लिए 1950 पर संपर्क करें" ));
//        scrollingText.setMovementMethod(LinkMovementMethod.getInstance());

        scrollingText.setSelected(true);

        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Act_home.this, Act_course_list.class);
//                startActivity(i);
//                finish();

            }
        });
        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Act_home.this, Act_department_list.class);
//                startActivity(i);
//                finish();
            }
        });

        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Act_home.this, Act_director_list.class);
//                startActivity(i);
//                finish();

            }
        });

        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(Act_home.this, Act_hod_list.class);
//                startActivity(i);
//                finish();
            }
        });
        lay11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_home.this, Act_election.class);
                startActivity(i);
                finish();
            }
        });
        lay12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_home.this, Act_notification.class);
                startActivity(i);
                finish();
            }
        });
        lay13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_home.this, FollowUsActivity.class);
                startActivity(i);
                finish();
            }
        });

        try {
            VersionChecker versionChecker = new VersionChecker();
            latestVersion = versionChecker.execute().get();
//            Log.d("UPDATE VERSION>>>>>",latestVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =packageManager.getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        currentVersion = packageInfo.versionName;

        if (NetworkConnection.checkNetworkStatus(context)==true){
            if ((compareVersionNames(currentVersion,latestVersion))==-1){
                showUpdateDialog();
            }else{
                permissionPhone();
            }
        }else{
            Toast.makeText(getApplicationContext(),"Check internet connection",Toast.LENGTH_SHORT).show();
        }






//        SpannableString ss = new SpannableString("निर्वाचन संबंधित जानकारी एवं मतदाता सहायता के लिए संपर्क करें 1950");
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View textView) {
//                Toast.makeText(getApplicationContext(),"phone no clicked",Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//                ds.setUnderlineText(false);
//            }
//        };
//        ss.setSpan(clickableSpan, 50, 55, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
////        TextView textView = (TextView) findViewById(R.id.hello);
//        scrollingText = (TextView)findViewById(R.id.scrollingtext);
//        scrollingText.setSelected(true);
//        scrollingText.setText(ss);
//        scrollingText.setMovementMethod(LinkMovementMethod.getInstance());
//        scrollingText.setHighlightColor(Color.TRANSPARENT);

//        setClickableString("1950","निर्वाचन संबंधित जानकारी एवं मतदाता सहायता के लिए संपर्क करें 1950",scrollingText);

    }


    public void setClickableString(String clickableValue, String wholeValue, TextView yourTextView){
        String value = wholeValue;
        SpannableString spannableString = new SpannableString(value);
        int startIndex = value.indexOf(clickableValue);
        int endIndex = startIndex + clickableValue.length();
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false); // <-- this will remove automatic underline in set span
            }

            @Override
            public void onClick(View widget) {
                // do what you want with clickable value
            }
        }, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        yourTextView.setText(spannableString);
        yourTextView.setMovementMethod(LinkMovementMethod.getInstance()); // <-- important, onClick in ClickableSpan won't work without this
    }


    private void permission() {
        datafinish = true;
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add("location");
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE))
            permissionsNeeded.add("Network state");
        if (!addPermission(permissionsList, Manifest.permission.READ_PHONE_STATE))
            permissionsNeeded.add("Location");
        if (!addPermission(permissionsList, Manifest.permission.INTERNET))
            permissionsNeeded.add("Internet");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write storage");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read storage");
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("Call phone");
//        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
//            permissionsNeeded.add("Send sms");




        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
//                showMessageOKCancel(message, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
//                    }
//                });
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }

        init();

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Act_home.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_COARSE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)

                {

                    // All Permissions Granted
                    init();
                }
//                else {
//                    AlertDialog.Builder ad = new AlertDialog.Builder(Act_Login.this);
//                    ad.setMessage("Some Permission is Denied,You could not able to access this App!Please allow for all permission").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            permission();
//                        }
//                    }).setCancelable(false).create().show();
//                    // Permission Denied
//                    //  Toast.makeText(HomeActivity.this, "Some Permission is Denied,You could not able to access some functionalities of app.", Toast.LENGTH_LONG).show();
//                }
                break;
            }
            case READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionPhone();
                } else {
                    alertDialog();
                }

                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }


    private void init() {
        lay1=findViewById(R.id.lay1);
        lay2=findViewById(R.id.lay2);
        lay3=findViewById(R.id.lay3);
        lay4=findViewById(R.id.lay4);
        lay5=findViewById(R.id.lay5);
        lay6=findViewById(R.id.lay6);



    }

    private void permissionPhone(){
        if (!marshMallowPermission.checkPermissionForPhoneState()) {
            marshMallowPermission.requestPermissionForPhoneState();
        } else {
            TelephonyManager TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            }
            device_id = TelephonyMgr.getDeviceId();
            sessionParam.deviceId(context,device_id);
            Log.d("API LEVEL>>>>>", String.valueOf(Build.VERSION.SDK_INT));
            Log.d("API LEVEL LOLISPOP>>>>>", String.valueOf(Build.VERSION_CODES.N));

//            if (NetworkConnection.checkNetworkStatus(context) == true) {
//                api_loginStatus();
//            } else {
////                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
//            }
            if (NetworkConnection.checkNetworkStatus(context) == true) {
                api_loginStatus();
            } else {
                Toast.makeText(getApplicationContext(),"Check internet connection",Toast.LENGTH_SHORT).show();

               }
        }

    }

    public void onBackPressed() {
        new android.app.AlertDialog.Builder(Act_home.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);


                    }

                })
                .setNegativeButton("No", null)
                .show();


    }

    public void showUpdateDialog () {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(Act_home.this);

        alertDialogBuilder.setTitle(Act_home.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage(Act_home.this.getString(R.string.update_message));
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.update_now, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                    Act_Splash.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.visuotech.hoshangabad_election"));
                startActivity(i);
                dialog.cancel();
                permissionPhone();
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                permissionPhone();

            }
        });
        alertDialogBuilder.show();
    }

    public int compareVersionNames(String oldVersionName, String newVersionName) {
        int res = 0;

        String[] oldNumbers = oldVersionName.split("\\.");
        String[] newNumbers = newVersionName.split("\\.");

        // To avoid IndexOutOfBounds
        int maxIndex = Math.min(oldNumbers.length, newNumbers.length);

        for (int i = 0; i < maxIndex; i ++) {
            int oldVersionPart = Integer.valueOf(oldNumbers[i]);
            int newVersionPart = Integer.valueOf(newNumbers[i]);

            if (oldVersionPart < newVersionPart) {
                res = -1;
                break;
            } else if (oldVersionPart > newVersionPart) {
                res = 1;
                break;
            }
        }

        // If versions are the same so far, but they have different length...
        if (res == 0 && oldNumbers.length != newNumbers.length) {
            res = (oldNumbers.length > newNumbers.length)?1:-1;
        }

        return res;
    }


    public void api_loginStatus() {
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                permission();
            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        RequestBody device_id_ = RequestBody.create(MediaType.parse("text/plain"), device_id);
        RequestBody fcm_token_ = RequestBody.create(MediaType.parse("text/plain"), fcm_token);


        baseRequest.callAPILoginStatus(1,"http://collectorexpress.in/",device_id_,fcm_token_);

    }
    private void alertDialog() {
        new android.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Attention")
                .setCancelable(false)
                .setMessage("Compulsory!")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        permissionPhone();
                    }
                })
                .show();
    }

}
