package com.visuotech.hoshangabad_election.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.visuotech.hoshangabad_election.Adapter.Ad_notifications;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Notificationss;
import com.visuotech.hoshangabad_election.NetworkConnection;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_notification extends AppCompatActivity {
    LinearLayout container,lay;
    LinearLayoutManager linearLayoutManager;
    ImageView search_icon;
    RecyclerView rv;
    ArrayList<Notificationss> notifications_list1=new ArrayList<Notificationss>();
    ArrayList<String>notifications_list=new ArrayList<>();
    ArrayList<Notificationss> notifications_list2 = new ArrayList<Notificationss>();
    Ad_notifications adapter;
    String booth_name;
    EditText inputSearch;
    String id,samitee_name;

    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    public boolean datafinish = false;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    Handler handler;
    Runnable refresh;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout lin_spl_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);

        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_sam_mem_list, null);

        permission();

        mSwipeRefreshLayout=rowView.findViewById(R.id.activity_main_swipe_refresh_layout);
        rv = (RecyclerView) rowView.findViewById(R.id.rv_list);
        inputSearch = (EditText) rowView.findViewById(R.id.inputSearch);
        lay =  rowView.findViewById(R.id.lay);
        search_icon =  rowView.findViewById(R.id.search_icon);
        lay.setVisibility(View.GONE);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        container.addView(rowView, container.getChildCount());


//        Intent intent=getIntent();
//        id=intent.getStringExtra("Id");
//        samitee_name=intent.getStringExtra("Name");


//        Apigetboothlist();

/*
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
*/
        lin_spl_layout=rowView.findViewById(R.id.lin_spl_layout);






        if (NetworkConnection.checkNetworkStatus(context)==true){
            Apigetsam_mem_list();
        }else{
            String Json;
            Json = sessionParam.getJson("Notification",context);
            try {
                JSONObject jsonObject = new JSONObject(Json);
                JSONArray jsonArray=jsonObject.optJSONArray("user");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Notificationss notificationss = new Notificationss();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String push_title = jsonObject1.optString("push_title");
                    String push_desc = jsonObject1.optString("push_desc");
                    String creation_date = jsonObject1.optString("creation_date");

                    notificationss.setPush_title(push_title);
                    notificationss.setPush_desc(push_desc);
                    notificationss.setCreation_date(creation_date);

                    notifications_list1.add(notificationss);

                }

                adapter=new Ad_notifications(context,notifications_list1);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


//        handler = new Handler();
//        refresh = new Runnable() {
//            public void run() {
//
//                if (NetworkConnection.checkNetworkStatus(context) == true) {
//                    Apigetsam_mem_list();
//                } else {
//
//                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
//                }
//                handler.postDelayed(refresh, 60000);
//
//            }
//        };
//        handler.post(refresh);
//

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnection.checkNetworkStatus(context)==true){
                    Apigetsam_mem_list();
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
                    mSwipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                }


            }
        });




    }

    public void start(){

    }

    private void permission() {
        datafinish = true;
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
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
        new AlertDialog.Builder(Act_notification.this)
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
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
//                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){

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
            }
            break;
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
    public void init() {



    }

    private void Apigetsam_mem_list(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),"Notification",context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    notifications_list1=baseRequest.getDataList(jsonArray,Notificationss.class);

//                    for (int i=0;i<sam_mem_list1.size();i++){
//                        booth_list.add(sam_mem_list1.get(i).getEleBoothName());
////                       department_id.add(department_list1.get(i).getDepartment_id());
//                    }

                    adapter=new Ad_notifications(context,notifications_list1);
                    rv.setAdapter(adapter);

//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);
//



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {

            }
            @Override
            public void onNetworkFailure(int requestCode, String message) {

            }
        });
        String remainingUrl2="/Election/Api2.php?apicall=push_noti";
        baseRequest.callAPIGETData(1, remainingUrl2);
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        String[] designation_list2;
        ArrayList<Notificationss>members_list2=new ArrayList<>();

        //looping through existing elements
        for (int i=0;i<notifications_list1.size();i++) {
            if (notifications_list1.get(i).getPush_title().toLowerCase().contains(text.toLowerCase())) {
                Notificationss samitee_members = new Notificationss();
                samitee_members.setCreation_date(notifications_list1.get(i).getCreation_date());
                samitee_members.setPush_activedevices(notifications_list1.get(i).getPush_activedevices());
                samitee_members.setPush_delvrddevices(notifications_list1.get(i).getPush_delvrddevices());
                samitee_members.setPush_desc(notifications_list1.get(i).getPush_desc());
                samitee_members.setPush_title(notifications_list1.get(i).getPush_title());
                members_list2.add(samitee_members);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(members_list2);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_notification.this, Act_home.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_notification.this, Act_home.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

}
