package com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.visuotech.hoshangabad_election.Activities.Election.Act_election;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Block;
import com.visuotech.hoshangabad_election.Model.Designation_Details;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.Model.Tehsil;
import com.visuotech.hoshangabad_election.NetworkConnection;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;
import com.visuotech.hoshangabad_election.retrofit.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_polling_station extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LinearLayout container;
    Spinner spinner_tehsil,spinner_block,spinner_station2;
    SearchableSpinner spinner_station;
    String station, station2, tehsil, block, name, desig, lat, log,lat2, log2;
    Button btn_submit,btn_submit2;
    int designation_no;

    ArrayList<PollingBooth> booth_list1;
    ArrayList<String> booth_list = new ArrayList<String>();

    ArrayList<Block> blocks_list1;
    ArrayList<String> blocks_list = new ArrayList<String>();

    ArrayList<PollingBooth> booth_list2;
    ArrayList<String> booth_list22 = new ArrayList<String>();

    ArrayList<Tehsil> tehsils_list1;
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
    ArrayAdapter adapter_block,adapter_tehsil,adapter_booth2;
    ArrayAdapter<CharSequence> adapter_booth;

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

        Intent intent=getIntent();
        AC=intent.getStringExtra("CITY");

        container = (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_polling_station, null);

        permission();

        spinner_station = rowView.findViewById(R.id.spinner_station);
        spinner_station2 = rowView.findViewById(R.id.spinner_station2);
        spinner_tehsil = rowView.findViewById(R.id.spinner_tehsil);
        spinner_block = rowView.findViewById(R.id.spinner_block);
        btn_submit2 = rowView.findViewById(R.id.btn_submit2);
        btn_submit = rowView.findViewById(R.id.btn_submit);

        container.addView(rowView, container.getChildCount());
        spinner_station.setOnItemSelectedListener(this);
        spinner_station2.setOnItemSelectedListener(this);
        spinner_tehsil.setOnItemSelectedListener(this);
        spinner_block.setOnItemSelectedListener(this);


//        spinner_designation.setSelection(listsize);


         LinearLayout lin_spl_layout=rowView.findViewById(R.id.lin_spl_layout);
        if (NetworkConnection.checkNetworkStatus(context) == true) {
            Apigetboothlist();
            ApigettehsilList();
        } else {
            Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (booth_list1 != null) {
                    Intent i = new Intent(Act_polling_station.this, Act_Polling_list.class);
                    i.putExtra("NAME", station);
                    i.putExtra("CITY", AC);
                    i.putExtra("LATITUDE",lat);
                    i.putExtra("LONGITUDE",log);
                    startActivity(i);


                }
            }
        });

        btn_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (booth_list2 != null) {
                    Intent i = new Intent(Act_polling_station.this, Act_Polling_list.class);
                    i.putExtra("NAME", station2);
                    i.putExtra("CITY", AC);
                    i.putExtra("LATITUDE",lat);
                    i.putExtra("LONGITUDE",log);
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
        new AlertDialog.Builder(Act_polling_station.this)
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



    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_station :
                //Your Action Here.
                station=booth_list1.get(i).getEleBoothName();
                lat=booth_list1.get(i).getEleLatitude();
                log=booth_list1.get(i).getEleLongitude();
                break;

            case R.id.spinner_tehsil :
                //Your Action Here.
                tehsil=tehsils_list1.get(i).getTehsil_name();
                blocks_list.clear();
                ApigetBlockList();
                break;

            case R.id.spinner_block :
                //Your Action Here.
                block=blocks_list1.get(i).getBlock_name();
                booth_list22.clear();
                Apigetboothlist2();
                break;

            case R.id.spinner_station2 :
                //Your Action Here.
                station2=booth_list2.get(i).getEleBoothName();
                lat2=booth_list2.get(i).getEleLatitude();
                log2=booth_list2.get(i).getEleLongitude();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    private void Apigetboothlist(){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list1=baseRequest.getDataList(jsonArray,PollingBooth.class);

                    for (int i=0;i<booth_list1.size();i++){
                        int j=i+1;
                        booth_list.add(j+"- "+booth_list1.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);

                    adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_station.setAdapter(adapter_booth);




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

    private void Apigetboothlist2(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list2=baseRequest.getDataList(jsonArray,PollingBooth.class);
                    for (int i=0;i<booth_list2.size();i++){
                        int j=i+1;
                        booth_list22.add(j+"- "+booth_list2.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                     adapter_booth2 = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list22);
                    adapter_booth2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_station2.setAdapter(adapter_booth2);

//                    ArrayAdapter<CharSequence> adapter_booth2 = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,booth_list22);
//                    adapter_booth2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station2.setAdapter(adapter_booth2);




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
        String remainingUrl2="/Election/Api2.php?apicall=get_polling"+"&tehsil_id="+tehsil+"&block_id="+block+"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }


    private void ApigettehsilList(){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    tehsils_list1=baseRequest.getDataList(jsonArray,Tehsil.class);

                    for (int i=0;i<tehsils_list1.size();i++){
                        int j=i+1;
                        tehsils_list.add(j+"- "+tehsils_list1.get(i).getTehsil_name());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);

                     adapter_tehsil = new ArrayAdapter(context,android.R.layout.simple_spinner_item,tehsils_list);
                    adapter_tehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_tehsil.setAdapter(adapter_tehsil);




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

    private void ApigetBlockList(){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    blocks_list1=baseRequest.getDataList(jsonArray,Block.class);

                    for (int i=0;i<blocks_list1.size();i++){
                        int j=i+1;
                        blocks_list.add(j+"- "+blocks_list1.get(i).getBlock_name());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);

                    adapter_block = new ArrayAdapter(context,android.R.layout.simple_spinner_item,blocks_list);
                    adapter_block.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_block.setAdapter(adapter_block);

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
        String remainingUrl2="/Election/Api2.php?apicall=block_list"+"&tehsil_id="+tehsil+"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }



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
