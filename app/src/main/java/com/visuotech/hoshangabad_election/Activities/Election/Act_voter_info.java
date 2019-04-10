package com.visuotech.hoshangabad_election.Activities.Election;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.Adapter.Ad_notifications;
import com.visuotech.hoshangabad_election.Adapter.Ad_samitee_member;
import com.visuotech.hoshangabad_election.Adapter.Ad_voters_urls;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Notificationss;
import com.visuotech.hoshangabad_election.Model.Samitee_members;
import com.visuotech.hoshangabad_election.Model.Voter;
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

public class Act_voter_info extends AppCompatActivity {
    LinearLayout container;
    private WebView wv1;
    ProgressBar progressbar;
    boolean desktop=false;
//    String url="https://api.androidhive.info/webview/index.html";
    String url="http://164.100.196.163/searchengine/SearchHN.aspx";
    private float m_downX;

    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ArrayList<Voter> voters_urls_info1=new ArrayList<Voter>();
    ArrayList<String>voters_urls_info=new ArrayList<>();
    Ad_voters_urls adapter;
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
    SwipeRefreshLayout mSwipeRefreshLayout;
    LinearLayout lin_spl_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Voter information");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);

        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_voter_info, null);
        container.addView(rowView, container.getChildCount());

        mSwipeRefreshLayout=rowView.findViewById(R.id.activity_main_swipe_refresh_layout);
        rv = (RecyclerView) rowView.findViewById(R.id.rv_list);
        inputSearch = (EditText) rowView.findViewById(R.id.inputSearch);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        permission();

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

        lin_spl_layout=rowView.findViewById(R.id.lin_spl_layout);
        if (NetworkConnection.checkNetworkStatus(context) == true) {
            ApigetvoterInfo();
        } else {
            Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
            String Json;
            Json = sessionParam.getJson("Voter_info",context);
            try {
                JSONObject jsonObject = new JSONObject(Json);
                JSONArray jsonArray=jsonObject.optJSONArray("user");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Voter notificationss = new Voter();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String voter_name = jsonObject1.optString("voter_name");
                    String voter_url = jsonObject1.optString("voter_url");

                    notificationss.setVoterName(voter_name);
                    notificationss.setVoterUrl(voter_url);

                    voters_urls_info1.add(notificationss);

                }

                adapter=new Ad_voters_urls(context,voters_urls_info1);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnection.checkNetworkStatus(context)==true){
                    ApigetvoterInfo();
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();       }


            }
        });



//        wv1=(WebView)findViewById(R.id.webView);
//        progressbar=(ProgressBar)findViewById(R.id.progressbar);
//        wv1.setWebViewClient(new MyBrowser());
//         progressbar.setVisibility(View.GONE);
////        initWebView();
////        wv1.loadUrl(url);
////
//        wv1.getSettings().setLoadsImagesAutomatically(true);
//        wv1.getSettings().setJavaScriptEnabled(true);
//        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        wv1.getSettings().setJavaScriptEnabled(true);
//        wv1.getSettings().setLoadWithOverviewMode(true);
//        wv1.getSettings().setUseWideViewPort(true);
//
//        wv1.getSettings().setSupportZoom(true);
//        wv1.getSettings().setBuiltInZoomControls(true);
//        wv1.getSettings().setDisplayZoomControls(false);
//
//        wv1.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        wv1.setScrollbarFadingEnabled(false);
//        wv1.loadUrl(url);
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
        new AlertDialog.Builder(Act_voter_info.this)
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

    private void ApigetvoterInfo(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),"Voter_info",context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    voters_urls_info1=baseRequest.getDataList(jsonArray,Voter.class);

//                    for (int i=0;i<sam_mem_list1.size();i++){
//                        booth_list.add(sam_mem_list1.get(i).getEleBoothName());
////                       department_id.add(department_list1.get(i).getDepartment_id());
//                    }

                    adapter=new Ad_voters_urls(context,voters_urls_info1);
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
        String remainingUrl2="/Election/Api2.php?apicall=voter_info";
        baseRequest.callAPIGETData(1, remainingUrl2);
    }



    private void filter(String text) {
        //new array list that will hold the filtered data
        String[] designation_list2;
        ArrayList<Voter>members_list2=new ArrayList<>();

        //looping through existing elements
        for (int i=0;i<voters_urls_info1.size();i++) {
            if (voters_urls_info1.get(i).getVoterName().toLowerCase().contains(text.toLowerCase())) {
                Voter samitee_members = new Voter();
                samitee_members.setVoterName(voters_urls_info1.get(i).getVoterName());
                samitee_members.setVoterUrl(voters_urls_info1.get(i).getVoterUrl());

                members_list2.add(samitee_members);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(members_list2);
    }


//    private void initWebView() {
//        wv1.setWebChromeClient(new MyWebChromeClient(this));
//        wv1.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//                progressbar.setVisibility(View.VISIBLE);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                wv1.loadUrl(url);
//                return true;
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                progressbar.setVisibility(View.GONE);
//                invalidateOptionsMenu();
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                super.onReceivedError(view, request, error);
//                progressbar.setVisibility(View.GONE);
//                invalidateOptionsMenu();
//            }
//        });
//        wv1.clearCache(true);
//        wv1.clearHistory();
//        wv1.getSettings().setJavaScriptEnabled(true);
//        wv1.setHorizontalScrollBarEnabled(false);
//        wv1.setOnTouchListener(new View.OnTouchListener() {
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (event.getPointerCount() > 1) {
//                    //Multi touch detected
//                    return true;
//                }
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN: {
//                        // save the x
//                        m_downX = event.getX();
//                    }
//                    break;
//
//                    case MotionEvent.ACTION_MOVE:
//                    case MotionEvent.ACTION_CANCEL:
//                    case MotionEvent.ACTION_UP: {
//                        // set x so that it doesn't move
//                        event.setLocation(m_downX, event.getY());
//                    }
//                    break;
//                }
//
//                return false;
//            }
//        });
//    }
//
//    private void back() {
//        if (wv1.canGoBack()) {
//            wv1.goBack();
//        }
//    }
//
//    private void forward() {
//        if (wv1.canGoForward()) {
//            wv1.goForward();
//        }
//    }
//
//    private class MyWebChromeClient extends WebChromeClient {
//        Context context;
//
//        public MyWebChromeClient(Context context) {
//            super();
//            this.context = context;
//        }
//
//
//    }
//
//    private class MyBrowser extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//
//            return true;
//        }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_voter_info.this, Act_election.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_voter_info.this, Act_election.class);
        startActivity(i);
        finish();
    }


}
