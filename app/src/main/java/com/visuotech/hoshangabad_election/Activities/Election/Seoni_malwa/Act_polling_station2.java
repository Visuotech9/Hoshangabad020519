package com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
//import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.visuotech.hoshangabad_election.Adapter.SimpleArrayListAdapter;
import com.visuotech.hoshangabad_election.Adapter.SimpleListAdapter;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Block;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.Model.Sector;
import com.visuotech.hoshangabad_election.Model.Tehsil;
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

import gr.escsoft.michaelprimez.searchablespinner.interfaces.IStatusListener;
import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;


public class Act_polling_station2 extends AppCompatActivity{
    LinearLayout container;
    Spinner spinner_tehsil,spinner_block,spinner_sector;
    SearchableSpinner spinner_station;
    SearchableSpinner spinner_station2;
    String station,station_id,station_id2,election_id,election_id2, station2, tehsil, block, sector, desig, lat, log,lat2, log2;
    Button btn_submit,btn_submit2;
    int designation_no;
    ArrayList<PollingBooth> booth_list1=new ArrayList<>();
    ArrayList<String> booth_list = new ArrayList<String>();

    ArrayList<Block> blocks_list1=new ArrayList<>();
    ArrayList<String> blocks_list = new ArrayList<String>();

    ArrayList<Sector> sectors_list1=new ArrayList<>();
    ArrayList<String> sectors_list = new ArrayList<String>();

    ArrayList<PollingBooth> booth_list2=new ArrayList<>();
    ArrayList<String> booth_list22 = new ArrayList<String>();

    ArrayList<Tehsil> tehsils_list1=new ArrayList<>();
    ArrayList<String> tehsils_list = new ArrayList<String>();


    Context context;
    String AC;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    String[] designation_list = {"P0", "P1", "BLO", "GRS", "SACHIV", "THANA", "Sector Officer", "Local Contact Person-1", "Local Contact Person-2", "--Select gender--"};
    public boolean datafinish = false;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    ArrayAdapter adapter_block,adapter_tehsil,adapter_sector;
    ArrayAdapter<CharSequence> adapter_booth,adapter_booth2;
    LinearLayout lin_spl_layout;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProgressBar progressBar;


    private SearchableSpinner mSearchableSpinner;
    private SearchableSpinner mSearchableSpinner1;
    private SearchableSpinner mSearchableSpinner2;
    private SearchableSpinner mSearchableSpinner3;
    private SearchableSpinner mSearchableSpinner4;
    private SimpleListAdapter mSimpleListAdapter;
    private SimpleArrayListAdapter mSimpleArrayListAdapter;
    private final ArrayList<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        //-------------------------toolbar------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Polling Station");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);

        final Intent intent=getIntent();
        AC=intent.getStringExtra("CITY");

        container = (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.test_station_layout, null);

        permission();

//        spinner_station = rowView.findViewById(R.id.spinner_station);
//        spinner_station2 = rowView.findViewById(R.id.spinner_station2);
//        spinner_tehsil = rowView.findViewById(R.id.spinner_tehsil);
//        spinner_block = rowView.findViewById(R.id.spinner_block);
//        spinner_sector = rowView.findViewById(R.id.spinner_sector);
        mSearchableSpinner =  findViewById(R.id.SearchableSpinner);
        mSearchableSpinner1 =  findViewById(R.id.SearchableSpinner1);
        mSearchableSpinner2 =  findViewById(R.id.SearchableSpinner2);
        mSearchableSpinner3 =  findViewById(R.id.SearchableSpinner3);
        mSearchableSpinner4 =  findViewById(R.id.SearchableSpinner4);

        btn_submit2 = rowView.findViewById(R.id.btn_submit2);
        btn_submit = rowView.findViewById(R.id.btn_submit);
//        progressBar=rowView.findViewById(R.id.progressBar);

        container.addView(rowView, container.getChildCount());
//        mSearchableSpinner.setOnItemSelectedListener(this);
//        mSearchableSpinner1.setOnItemSelectedListener(this);
//        mSearchableSpinner2.setOnItemSelectedListener(this);
//        mSearchableSpinner3.setOnItemSelectedListener(this);
//        mSearchableSpinner4.setOnItemSelectedListener(this);


//        spinner_designation.setSelection(listsize);




        lin_spl_layout=rowView.findViewById(R.id.lin_spl_layout);
        if (NetworkConnection.checkNetworkStatus(context) == true) {
            Apigetboothlist();
            ApigettehsilList();


        } else {
            lin_spl_layout.setVisibility(View.VISIBLE);
//            progressBar.setVisibility(View.GONE);
            Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
            String Json;
            Json = sessionParam.getJson(AC+"station",context);
            try {
                if (Json!=null) {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray = jsonObject.optJSONArray("user");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        PollingBooth notificationss = new PollingBooth();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String election_id = jsonObject1.optString("election_id");
                        String ele_boothno = jsonObject1.optString("ele_boothno");
                        String ele_ac = jsonObject1.optString("ele_ac");
                        String ele_block = jsonObject1.optString("ele_block");
                        String ele_tehsil = jsonObject1.optString("ele_tehsil");
                        String ele_gpanchayat = jsonObject1.optString("ele_gpanchayat");
                        String ele_gram = jsonObject1.optString("ele_gram");
                        String ele_latitude = jsonObject1.optString("ele_latitude");
                        String ele_longitude = jsonObject1.optString("ele_longitude");
                        String ele_booth_name = jsonObject1.optString("ele_booth_name");
                        String ele_blo_name = jsonObject1.optString("ele_blo_name");
                        String ele_blo_mobile = jsonObject1.optString("ele_blo_mobile");
                        String ele_nearest_thana = jsonObject1.optString("ele_nearest_thana");
                        String ele_thana_phone = jsonObject1.optString("ele_thana_phone");
                        String ele_thana_mobile = jsonObject1.optString("ele_thana_mobile");
                        String ele_sector_no = jsonObject1.optString("ele_sector_no");

                        String ele_sector_officer = jsonObject1.optString("ele_sector_officer");
                        String ele_sector_mobile = jsonObject1.optString("ele_sector_mobile");
                        String ele_local1_name = jsonObject1.optString("ele_local1_name");
                        String ele_local1_mobile = jsonObject1.optString("ele_local1_mobile");
                        String ele_local2_name = jsonObject1.optString("ele_local2_name");
                        String ele_local2_mobile = jsonObject1.optString("ele_local2_mobile");
                        String ele_sachiv = jsonObject1.optString("ele_sachiv");
                        String ele_sachiv_mobile = jsonObject1.optString("ele_sachiv_mobile");
                        String ele_grs = jsonObject1.optString("ele_grs");
                        String ele_grs_mobile = jsonObject1.optString("ele_grs_mobile");
                        String ele_aww = jsonObject1.optString("ele_aww");
                        String ele_aww_mobile = jsonObject1.optString("ele_aww_mobile");
                        String ele_p_zero = jsonObject1.optString("ele_p_zero");
                        String ele_p_zero_mobile = jsonObject1.optString("ele_p_zero_mobile");
                        String ele_p_one = jsonObject1.optString("ele_p_one");
                        String ele_p_one_mobile = jsonObject1.optString("ele_p_one_mobile");



                        notificationss.setElectionId(election_id);
                        notificationss.setEleBoothno(ele_boothno);
                        notificationss.setEleAc(ele_ac);
                        notificationss.setEleBlock(ele_block);
                        notificationss.setEleTehsil(ele_tehsil);
                        notificationss.setEleGpanchayat(ele_gpanchayat);
                        notificationss.setEleGram(ele_gram);
                        notificationss.setEleLatitude(ele_latitude);
                        notificationss.setEleLongitude(ele_longitude);
                        notificationss.setEleBoothName(ele_booth_name);
                        notificationss.setEleBloName(ele_blo_name);
                        notificationss.setEleBloMobile(ele_blo_mobile);
                        notificationss.setEleNearestThana(ele_nearest_thana);
                        notificationss.setEleThanaPhone(ele_thana_phone);
                        notificationss.setEleThanaMobile(ele_thana_mobile);
                        notificationss.setEleSectorNo(ele_sector_no);
                        notificationss.setEleSectorOfficer(ele_sector_officer);
                        notificationss.setEleSectorMobile(ele_sector_mobile);
                        notificationss.setEleLocal1Name(ele_local1_name);
                        notificationss.setEleLocal1Mobile(ele_local1_mobile);
                        notificationss.setEleLocal2Name(ele_local2_name);
                        notificationss.setEleLocal2Mobile(ele_local2_mobile);
                        notificationss.setEleSachiv(ele_sachiv);
                        notificationss.setEleSachivMobile(ele_sachiv_mobile);
                        notificationss.setEleGrs(ele_grs);
                        notificationss.setEleGrsMobile(ele_grs_mobile);
                        notificationss.setEleAww(ele_aww);
                        notificationss.setEleAwwMobile(ele_aww_mobile);
                        notificationss.setElePZero(ele_p_zero);
                        notificationss.setElePZeroMobile(ele_p_zero_mobile);
                        notificationss.setElePOne(ele_p_one);
                        notificationss.setElePOneMobile(ele_p_one_mobile);

                        booth_list1.add(notificationss);

                    }

                    for (int i=0;i<booth_list1.size();i++){

                        booth_list.add(booth_list1.get(i).getEleBoothno()+"- "+booth_list1.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);

//                    adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);

                }else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



            String Json1;
            Json1 = sessionParam.getJson(AC+"tehsil",context);
            try {
                if (Json!=null) {
                    JSONObject jsonObject = new JSONObject(Json1);
                    JSONArray jsonArray = jsonObject.optJSONArray("user");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Tehsil notificationss = new Tehsil();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String tehsil_name = jsonObject1.optString("tehsil_name");

                        notificationss.setTehsil_name(tehsil_name);


                        tehsils_list1.add(notificationss);

                    }

                    for (int i=0;i<tehsils_list1.size();i++){
                        int j=i+1;
                        tehsils_list.add(j+"- "+tehsils_list1.get(i).getTehsil_name());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);

//                    adapter_tehsil = new ArrayAdapter(context,android.R.layout.simple_spinner_item,tehsils_list);
//                    adapter_tehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_tehsil.setAdapter(adapter_tehsil);

                }else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (booth_list1 != null) {
                    Intent i = new Intent(Act_polling_station2.this, Act_Polling_list.class);

                    i.putExtra("NAME", station);
                    i.putExtra("key", station+station_id);
                    i.putExtra("CITY", AC);
                    i.putExtra("STATION_ID", station_id);
                    i.putExtra("ELECTION_ID", election_id);
                    i.putExtra("LATITUDE",lat);
                    i.putExtra("LONGITUDE",log);
                    i.putExtra("ARRAY",booth_list1);

//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("ARRAY",booth_list1);
//                    bundle.putString("NAME", station);
//                    bundle.putString("key", station+station_id);
//                    bundle.putString("CITY", AC);
//                    bundle.putString("STATION_ID", station_id);
//                    bundle.putString("ELECTION_ID", election_id);
//                    bundle.putString("LATITUDE",lat);
//                    bundle.putString("LONGITUDE",log);
//                    i.putExtras(bundle);
                    startActivity(i);


                }
            }
        });

        btn_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (booth_list2 != null) {
                    Intent i = new Intent(Act_polling_station2.this, Act_Polling_list.class);

                    i.putExtra("NAME", station2);
                    i.putExtra("key", station2+station_id2);
                    i.putExtra("CITY", AC);
                    i.putExtra("STATION_ID", station_id2);
                    i.putExtra("ELECTION_ID", election_id2);
                    i.putExtra("LATITUDE",lat2);
                    i.putExtra("LONGITUDE",log2);
                    i.putExtra("ARRAY",booth_list2);
                    startActivity(i);


                }
            }
        });






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
        new AlertDialog.Builder(Act_polling_station2.this)
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
                        &&perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        &&perms.get(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){

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

        mSearchableSpinner =  findViewById(R.id.SearchableSpinner);
        mSearchableSpinner1 =  findViewById(R.id.SearchableSpinner1);
        mSearchableSpinner2 =  findViewById(R.id.SearchableSpinner2);
        mSearchableSpinner3 =  findViewById(R.id.SearchableSpinner3);
        mSearchableSpinner4 =  findViewById(R.id.SearchableSpinner4);

    }



    private void callApigetbooth(String key) {

        String Json;
        Json = sessionParam.getJson(key,context);
        try {



            if (Json!=null) {
                JSONObject jsonObject = new JSONObject(Json);
                JSONArray jsonArray = jsonObject.optJSONArray("user");
                for (int i = 0; i < jsonArray.length(); i++) {
                    PollingBooth notificationss = new PollingBooth();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String election_id = jsonObject1.optString("election_id");
                    String ele_boothno = jsonObject1.optString("ele_boothno");
                    String ele_ac = jsonObject1.optString("ele_ac");
                    String ele_block = jsonObject1.optString("ele_block");
                    String ele_tehsil = jsonObject1.optString("ele_tehsil");
                    String ele_gpanchayat = jsonObject1.optString("ele_gpanchayat");
                    String ele_gram = jsonObject1.optString("ele_gram");
                    String ele_latitude = jsonObject1.optString("ele_latitude");
                    String ele_longitude = jsonObject1.optString("ele_longitude");
                    String ele_booth_name = jsonObject1.optString("ele_booth_name");
                    String ele_blo_name = jsonObject1.optString("ele_blo_name");
                    String ele_blo_mobile = jsonObject1.optString("ele_blo_mobile");
                    String ele_nearest_thana = jsonObject1.optString("ele_nearest_thana");
                    String ele_thana_phone = jsonObject1.optString("ele_thana_phone");
                    String ele_thana_mobile = jsonObject1.optString("ele_thana_mobile");
                    String ele_sector_no = jsonObject1.optString("ele_sector_no");

                    String ele_sector_officer = jsonObject1.optString("ele_sector_officer");
                    String ele_sector_mobile = jsonObject1.optString("ele_sector_mobile");
                    String ele_local1_name = jsonObject1.optString("ele_local1_name");
                    String ele_local1_mobile = jsonObject1.optString("ele_local1_mobile");
                    String ele_local2_name = jsonObject1.optString("ele_local2_name");
                    String ele_local2_mobile = jsonObject1.optString("ele_local2_mobile");
                    String ele_sachiv = jsonObject1.optString("ele_sachiv");
                    String ele_sachiv_mobile = jsonObject1.optString("ele_sachiv_mobile");
                    String ele_grs = jsonObject1.optString("ele_grs");
                    String ele_grs_mobile = jsonObject1.optString("ele_grs_mobile");
                    String ele_aww = jsonObject1.optString("ele_aww");
                    String ele_aww_mobile = jsonObject1.optString("ele_aww_mobile");
                    String ele_p_zero = jsonObject1.optString("ele_p_zero");
                    String ele_p_zero_mobile = jsonObject1.optString("ele_p_zero_mobile");
                    String ele_p_one = jsonObject1.optString("ele_p_one");
                    String ele_p_one_mobile = jsonObject1.optString("ele_p_one_mobile");



                    notificationss.setElectionId(election_id);
                    notificationss.setEleBoothno(ele_boothno);
                    notificationss.setEleAc(ele_ac);
                    notificationss.setEleBlock(ele_block);
                    notificationss.setEleTehsil(ele_tehsil);
                    notificationss.setEleGpanchayat(ele_gpanchayat);
                    notificationss.setEleGram(ele_gram);
                    notificationss.setEleLatitude(ele_latitude);
                    notificationss.setEleLongitude(ele_longitude);
                    notificationss.setEleBoothName(ele_booth_name);
                    notificationss.setEleBloName(ele_blo_name);
                    notificationss.setEleBloMobile(ele_blo_mobile);
                    notificationss.setEleNearestThana(ele_nearest_thana);
                    notificationss.setEleThanaPhone(ele_thana_phone);
                    notificationss.setEleThanaMobile(ele_thana_mobile);
                    notificationss.setEleSectorNo(ele_sector_no);
                    notificationss.setEleSectorOfficer(ele_sector_officer);
                    notificationss.setEleSectorMobile(ele_sector_mobile);
                    notificationss.setEleLocal1Name(ele_local1_name);
                    notificationss.setEleLocal1Mobile(ele_local1_mobile);
                    notificationss.setEleLocal2Name(ele_local2_name);
                    notificationss.setEleLocal2Mobile(ele_local2_mobile);
                    notificationss.setEleSachiv(ele_sachiv);
                    notificationss.setEleSachivMobile(ele_sachiv_mobile);
                    notificationss.setEleGrs(ele_grs);
                    notificationss.setEleGrsMobile(ele_grs_mobile);
                    notificationss.setEleAww(ele_aww);
                    notificationss.setEleAwwMobile(ele_aww_mobile);
                    notificationss.setElePZero(ele_p_zero);
                    notificationss.setElePZeroMobile(ele_p_zero_mobile);
                    notificationss.setElePOne(ele_p_one);
                    notificationss.setElePOneMobile(ele_p_one_mobile);

                    booth_list2.add(notificationss);

                }
                for (int i=0;i<booth_list2.size();i++){
                    int j=i+1;
                    booth_list22.add(booth_list2.get(i).getEleBoothno()+"- "+booth_list2.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                }


                mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), booth_list22);
                mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), booth_list22);
                mSearchableSpinner4 = findViewById(R.id.SearchableSpinner4);
                mSearchableSpinner4.setAdapter(mSimpleListAdapter);

//                mSearchableSpinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });





//                adapter_booth2 = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list22);
//                adapter_booth2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner_station2.setAdapter(adapter_booth2);

            }else {
                Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callApigetsector(String key) {
        String Json;
        Json = sessionParam.getJson(key,context);
        try {
            if (Json!=null) {
                JSONObject jsonObject = new JSONObject(Json);
                JSONArray jsonArray = jsonObject.optJSONArray("user");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Sector notificationss = new Sector();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String block_name = jsonObject1.optString("sector_name");
                    notificationss.setSector_name(block_name);
                    sectors_list1.add(notificationss);

                }
                for (int i=0;i<sectors_list1.size();i++){
                    int j=i+1;
                    sectors_list.add(j+"- "+sectors_list1.get(i).getSector_name());
                }

                mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), sectors_list);
                mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), sectors_list);
                mSearchableSpinner3 = findViewById(R.id.SearchableSpinner3);
                init();
                mSearchableSpinner3.setAdapter(mSimpleListAdapter);
//                mSearchableSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

//                adapter_sector = new ArrayAdapter(context,android.R.layout.simple_spinner_item,sectors_list);
//                adapter_sector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner_sector.setAdapter(adapter_sector);

            }else {
                Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void callApigetblock(String key) {
        String Json;
        Json = sessionParam.getJson(key,context);
        try {
            if (Json!=null) {
                JSONObject jsonObject = new JSONObject(Json);
                JSONArray jsonArray = jsonObject.optJSONArray("user");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Block notificationss = new Block();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String block_name = jsonObject1.optString("block_name");
                    notificationss.setBlock_name(block_name);
                    blocks_list1.add(notificationss);

                }

                for (int i=0;i<blocks_list1.size();i++){
                    int j=i+1;
                    blocks_list.add(j+"- "+blocks_list1.get(i).getBlock_name());
                }

                mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), blocks_list);
                mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), blocks_list);
                mSearchableSpinner2 = findViewById(R.id.SearchableSpinner);
                init();
                mSearchableSpinner2 = findViewById(R.id.SearchableSpinner2);
                mSearchableSpinner2.setAdapter(mSimpleListAdapter);
//                mSearchableSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> adapterView) {
//
//                    }
//                });

//                adapter_block = new ArrayAdapter(context,android.R.layout.simple_spinner_item,blocks_list);
//                adapter_block.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                spinner_block.setAdapter(adapter_block);

            }else {
                Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Apigetboothlist(){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    lin_spl_layout.setVisibility(View.VISIBLE);
                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),AC+"station",context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list1=baseRequest.getDataList(jsonArray,PollingBooth.class);

                    for (int i=0;i<booth_list1.size();i++){
                        int j=i+1;
                        booth_list.add(booth_list1.get(i).getEleBoothName());
                    }

                    mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), booth_list);
                    mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), booth_list);
                    mSearchableSpinner = findViewById(R.id.SearchableSpinner);
                    init();
                    mSearchableSpinner.setAdapter(mSimpleListAdapter);
                    mSearchableSpinner.setOnItemSelectedListener(mOnItemSelectedListener);
//                    mSearchableSpinner3.setStatusListener(new IStatusListener() {
//                        @Override
//                        public void spinnerIsOpening() {
//                            mSearchableSpinner.hideEdit();
//                            mSearchableSpinner4.hideEdit();
//                            mSearchableSpinner2.hideEdit();
//                            mSearchableSpinner3.hideEdit();
//                        }
//
//                        @Override
//                        public void spinnerIsClosing() {
//
//                        }
//                    });

//                    mSearchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                            station=booth_list1.get(i).getEleBoothName();
//                            station_id=booth_list1.get(i).getEleBoothno();
//                            election_id=booth_list1.get(i).getElectionId();
//                            lat=booth_list1.get(i).getEleLatitude();
//                            log=booth_list1.get(i).getEleLongitude();
//
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });





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
        String remainingUrl2="/Election/Api2.php?apicall=polling_booth"+"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    public static void clickOutSideSpinner(View view)
    {
        // Configure touch listener for all views except edittext,Button and Spinner
        if (!(view instanceof EditText)
                &&!(view instanceof Button)
                &&!(view instanceof Spinner))
        {
            view.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    //here you close your dialog spinner

                    return false;
                }
            });
        }

        //runs through all the children views .
        if (view instanceof ViewGroup)
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                View innerView = ((ViewGroup) view).getChildAt(i);
//                closeSlidingDrawerOnTouch(innerView);
            }
        }
    }


    private void Apigetboothlist2(final String key){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),key,context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list2=baseRequest.getDataList(jsonArray,PollingBooth.class);
                    for (int i=0;i<booth_list2.size();i++){
                        int j=i+1;
                        booth_list22.add(booth_list2.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }

                    mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), booth_list22);
                    mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), booth_list22);
                    init();

                    mSearchableSpinner4 = findViewById(R.id.SearchableSpinner4);
                    mSearchableSpinner4.setAdapter(mSimpleListAdapter);
                    mSearchableSpinner4.setOnItemSelectedListener(mOnItemSelectedListener4);
//                    mSearchableSpinner4.setStatusListener(new IStatusListener() {
//                        @Override
//                        public void spinnerIsOpening() {
//                            mSearchableSpinner.hideEdit();
//                            mSearchableSpinner1.hideEdit();
//                            mSearchableSpinner2.hideEdit();
//                            mSearchableSpinner3.hideEdit();
//                        }
//
//                        @Override
//                        public void spinnerIsClosing() {
//
//                        }
//                    });


//                    mSearchableSpinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            station2=booth_list2.get(i).getEleBoothName();
//                            station_id2=booth_list2.get(i).getEleBoothno();
//                            election_id2=booth_list2.get(i).getElectionId();
//                            lat2=booth_list2.get(i).getEleLatitude();
//                            log2=booth_list2.get(i).getEleLongitude();
//                            Toast.makeText(Act_polling_station2.this,station2, Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });


//                     adapter_booth2 = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list22);
//                     adapter_booth2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                     spinner_station2.setAdapter(adapter_booth2);



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
        String remainingUrl2="/Election/Api2.php?apicall=get_polling"+"&tehsil_id="+tehsil+"&block_id="+block+"&ac="+AC+"&sector_id="+ this.sector;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void ApigettehsilList(){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),AC+"tehsil",context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    tehsils_list1=baseRequest.getDataList(jsonArray,Tehsil.class);

                    for (int i=0;i<tehsils_list1.size();i++){
                        int j=i+1;
                        tehsils_list.add(tehsils_list1.get(i).getTehsil_name());
                        ApigetBlockList1(tehsils_list1.get(i).getTehsil_name(),tehsils_list1.get(i).getTehsil_name());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }

//
//                    adapter_tehsil = new ArrayAdapter(context,android.R.layout.simple_spinner_item,tehsils_list);
//                    adapter_tehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_tehsil.setAdapter(adapter_tehsil);
//
//


                    mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), tehsils_list);
                    mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), tehsils_list);
                    init();

                    mSearchableSpinner1 = findViewById(R.id.SearchableSpinner1);
                    mSearchableSpinner1.setAdapter(mSimpleListAdapter);
                    mSearchableSpinner1.setOnItemSelectedListener(mOnItemSelectedListener1);

//                    mSearchableSpinner1.setStatusListener(new IStatusListener() {
//                        @Override
//                        public void spinnerIsOpening() {
//                            mSearchableSpinner.hideEdit();
//                            mSearchableSpinner4.hideEdit();
//                            mSearchableSpinner2.hideEdit();
//                            mSearchableSpinner3.hideEdit();
//                        }
//
//                        @Override
//                        public void spinnerIsClosing() {
//
//                        }
//                    });



//                    mSearchableSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                            tehsil=tehsils_list1.get(i).getTehsil_name();
//                            blocks_list.clear();
//                            blocks_list1.clear();
////                callApigetblock(tehsil);
//                            Toast.makeText(Act_polling_station2.this,tehsil, Toast.LENGTH_SHORT).show();
//
//                            if (NetworkConnection.checkNetworkStatus(context) == true) {
//                                ApigetBlockList(tehsil);
//                            }else {
//                                Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
//                                callApigetblock(tehsil);
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });



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
        String remainingUrl2="/Election/Api2.php?apicall=tehsil_list"+"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void ApigetBlockList(final String key){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),key,context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    blocks_list1=baseRequest.getDataList(jsonArray,Block.class);

                    for (int i=0;i<blocks_list1.size();i++){
                        int j=i+1;
                        blocks_list.add(blocks_list1.get(i).getBlock_name());
                    }

                    mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), blocks_list);
                    mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), blocks_list);
                    init();

                    mSearchableSpinner2 = findViewById(R.id.SearchableSpinner2);
                    mSearchableSpinner2.setAdapter(mSimpleListAdapter);
                    mSearchableSpinner2.setOnItemSelectedListener(mOnItemSelectedListener2);

//                    mSearchableSpinner2.setStatusListener(new IStatusListener() {
//                        @Override
//                        public void spinnerIsOpening() {
//                            mSearchableSpinner.hideEdit();
//                            mSearchableSpinner4.hideEdit();
//                            mSearchableSpinner1.hideEdit();
//                            mSearchableSpinner3.hideEdit();
//                        }
//
//                        @Override
//                        public void spinnerIsClosing() {
//
//                        }
//                    });


//                    mSearchableSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            block=blocks_list1.get(i).getBlock_name();
//                            sectors_list.clear();
//                            sectors_list1.clear();
//                            String p= String.valueOf(i);
//                            String keyy=block+tehsil+AC;
////                callApigetsector(keyy);
//                            Toast.makeText(Act_polling_station2.this,block, Toast.LENGTH_SHORT).show();
//
//                            if (NetworkConnection.checkNetworkStatus(context) == true) {
//                                ApigetSectorList(keyy);
//                            }else {
//                                Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
//                                callApigetsector(keyy);
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });

//                    adapter_block = new ArrayAdapter(context,android.R.layout.simple_spinner_item,blocks_list);
//                    adapter_block.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_block.setAdapter(adapter_block);


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
        String remainingUrl2="/Election/Api2.php?apicall=block_list"+"&tehsil_id="+tehsil +"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void ApigetBlockList1(final String key, final String tehsil){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),key,context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    blocks_list1=baseRequest.getDataList(jsonArray,Block.class);



                    for (int i=0;i<blocks_list1.size();i++){
                        int j=i+1;
                        blocks_list.add(j+"- "+blocks_list1.get(i).getBlock_name());
                        ApigetSectorList1(blocks_list1.get(i).getBlock_name()+tehsil+AC,tehsil,blocks_list1.get(i).getBlock_name());
                    }

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
        String remainingUrl2="/Election/Api2.php?apicall=block_list"+"&tehsil_id="+tehsil +"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }


    private void ApigetSectorList(final String key){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),key,context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    sectors_list1=baseRequest.getDataList(jsonArray,Sector.class);

                    for (int i=0;i<sectors_list1.size();i++){
                        int j=i+1;
                        sectors_list.add(j+"- "+sectors_list1.get(i).getSector_name());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }

                    mSimpleListAdapter = new SimpleListAdapter(getApplicationContext(), sectors_list);
                    mSimpleArrayListAdapter = new SimpleArrayListAdapter(getApplicationContext(), sectors_list);
                    init();

                    mSearchableSpinner3 = findViewById(R.id.SearchableSpinner3);
                    mSearchableSpinner3.setAdapter(mSimpleListAdapter);
                    mSearchableSpinner3.setOnItemSelectedListener(mOnItemSelectedListener3);

//                    mSearchableSpinner3.setStatusListener(new IStatusListener() {
//                        @Override
//                        public void spinnerIsOpening() {
////                            mSearchableSpinner.hideEdit();
////                            mSearchableSpinner1.hideEdit();
////                            mSearchableSpinner2.hideEdit();
//                            mSearchableSpinner4.hideEdit();
//                        }
//
//                        @Override
//                        public void spinnerIsClosing() {
//
//                        }
//                    });


//                    mSearchableSpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            sector=sectors_list1.get(i).getSector_name();
//                            booth_list22.clear();
//                            booth_list2.clear();
////                callApigetbooth(sector+block);
//                            Toast.makeText(Act_polling_station2.this,sector, Toast.LENGTH_SHORT).show();
//
//
//                            if (NetworkConnection.checkNetworkStatus(context) == true) {
//                                Apigetboothlist2(sector+block);
//                            }else {
//                                Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
//                                callApigetbooth(sector+block);
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });


//                    adapter_sector = new ArrayAdapter(context,android.R.layout.simple_spinner_item,sectors_list);
//                    adapter_sector.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_sector.setAdapter(adapter_sector);


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
        String remainingUrl2="/Election/Api2.php?apicall=sector_list"+"&ac="+AC+"&tehsil_id="+tehsil+"&block_id="+block;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void ApigetSectorList1(final String key,final String tehsil, final String block){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),key,context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    sectors_list1=baseRequest.getDataList(jsonArray,Sector.class);

                    for (int i=0;i<sectors_list1.size();i++){
                        int j=i+1;
                        sectors_list.add(j+"- "+sectors_list1.get(i).getSector_name());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                        Apigetboothlist3(sectors_list1.get(i).getSector_name()+block,tehsil,block,sectors_list1.get(i).getSector_name());
                    }

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
        String remainingUrl2="/Election/Api2.php?apicall=sector_list"+"&ac="+AC+"&tehsil_id="+tehsil+"&block_id="+block;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void Apigetboothlist3(final String key,String tehsil,String block,String sector){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {

                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),key,context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list2=baseRequest.getDataList(jsonArray,PollingBooth.class);
                    for (int i=0;i<booth_list2.size();i++){
                        int j=i+1;
                        booth_list22.add(booth_list2.get(i).getEleBoothno()+"- "+booth_list2.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }

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
        String remainingUrl2="/Election/Api2.php?apicall=get_polling"+"&tehsil_id="+tehsil+"&block_id="+block+"&ac="+AC+"&sector_id="+ sector;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (!mSearchableSpinner.isInsideSearchEditText(event)) {
//            mSearchableSpinner.hideEdit();
//        }
//        if (!mSearchableSpinner1.isInsideSearchEditText(event)) {
//            mSearchableSpinner1.hideEdit();
//        }
//        if (!mSearchableSpinner2.isInsideSearchEditText(event)) {
//            mSearchableSpinner2.hideEdit();
//        }
//        if (!mSearchableSpinner3.isInsideSearchEditText(event)) {
//            mSearchableSpinner3.hideEdit();
//        }
//        if (!mSearchableSpinner4.isInsideSearchEditText(event)) {
//            mSearchableSpinner4.hideEdit();
//        }
//
//        return super.onTouchEvent(event);
//    }

    private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int i, long id) {

            if (i==0){
                station=booth_list1.get(0).getEleBoothName();
            }else{
                station=booth_list1.get(i-1).getEleBoothName();
                station_id=booth_list1.get(i-1).getEleBoothno();
                election_id=booth_list1.get(i-1).getElectionId();
                lat=booth_list1.get(i-1).getEleLatitude();
                log=booth_list1.get(i-1).getEleLongitude();
            }



        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(Act_polling_station2.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnItemSelectedListener1 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int i, long id) {
            if (i==0){
                tehsil=tehsils_list1.get(0).getTehsil_name();
            }else {
                tehsil=tehsils_list1.get(i-1).getTehsil_name();
                blocks_list.clear();
                blocks_list1.clear();
                Toast.makeText(Act_polling_station2.this,tehsil, Toast.LENGTH_SHORT).show();

                if (NetworkConnection.checkNetworkStatus(context) == true) {
                    ApigetBlockList(tehsil);
                }else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    callApigetblock(tehsil);
                }
            }


        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(Act_polling_station2.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnItemSelectedListener2 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int i, long id) {

            if (i==0){
                block=blocks_list1.get(0).getBlock_name();
            }else {
                block=blocks_list1.get(i-1).getBlock_name();
                sectors_list.clear();
                sectors_list1.clear();
                String keyy=block+tehsil+AC;
                Toast.makeText(Act_polling_station2.this,block, Toast.LENGTH_SHORT).show();

                if (NetworkConnection.checkNetworkStatus(context) == true) {
                    ApigetSectorList(keyy);
                }else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    callApigetsector(keyy);
                }
            }


        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(Act_polling_station2.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnItemSelectedListener3 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int i, long id) {

            if (i==0){
                sector=sectors_list1.get(0).getSector_name();
            }else {
                sector=sectors_list1.get(i-1).getSector_name();
                booth_list22.clear();
                booth_list2.clear();
                Toast.makeText(Act_polling_station2.this,sector, Toast.LENGTH_SHORT).show();


                if (NetworkConnection.checkNetworkStatus(context) == true) {
                    Apigetboothlist2(sector+block);
                }else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    callApigetbooth(sector+block);
                }

            }

        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(Act_polling_station2.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };

    private OnItemSelectedListener mOnItemSelectedListener4 = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(View view, int i, long id) {

            if (i==0){
                station2=booth_list2.get(0).getEleBoothName();
            }else {
                station2=booth_list2.get(i-1).getEleBoothName();
                station_id2=booth_list2.get(i-1).getEleBoothno();
                election_id2=booth_list2.get(i-1).getElectionId();
                lat2=booth_list2.get(i-1).getEleLatitude();
                log2=booth_list2.get(i-1).getEleLongitude();
                Toast.makeText(Act_polling_station2.this,station2, Toast.LENGTH_SHORT).show();
            }


        }

        @Override
        public void onNothingSelected() {
            Toast.makeText(Act_polling_station2.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
        }
    };







    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
//        Intent i = new Intent(Act_polling_station.this, Act_election.class);
//        startActivity(i);
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(Act_polling_station.this, Act_election.class);
//        startActivity(i);
        finish();
    }


}
