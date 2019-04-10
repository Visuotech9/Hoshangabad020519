package com.visuotech.hoshangabad_election.Activities.Election;

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
import android.widget.TextView;

import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Departments;
import com.visuotech.hoshangabad_election.Model.Designation_Details;
import com.visuotech.hoshangabad_election.Model.Samities;
import com.visuotech.hoshangabad_election.Model.ServiceType;
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

public class Act_other_services extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LinearLayout container,lay;
    TextView tv_text,tv_text1;
    Spinner spinner_services,spinner_level;
    String id,designation,booth_name,mobile,name,desig,lat,log;
    Button btn_cancel,btn_submit;
    int designation_no;
    ArrayList<ServiceType> serviceTypes_list1=new ArrayList<ServiceType>();
    ArrayList<String>  serviceTypes_list= new ArrayList<String>();
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    String[] designation_list = { "P0","P1","BLO","GRS","SACHIV","THANA","Sector Officer","Local Contact Person-1","Local Contact Person-2"};
    public boolean datafinish = false;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);
        //-------------------------toolbar------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Other Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);


        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_samaties, null);

        permission();

        spinner_services = rowView.findViewById(R.id.spinner_samities);
        tv_text = rowView.findViewById(R.id.tv_text);
        btn_submit = rowView.findViewById(R.id.btn_submit);

        lay = rowView.findViewById(R.id.lay);
        tv_text1 = rowView.findViewById(R.id.tv_text1);
        lay.setVisibility(View.GONE);
        tv_text1.setVisibility(View.GONE);

        tv_text.setText("Select service type :");

        container.addView(rowView, container.getChildCount());
        spinner_services.setOnItemSelectedListener(this);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (serviceTypes_list1!=null){
                    Intent i = new Intent(Act_other_services.this, Act_services_list.class);
                    i.putExtra("Name",name);
                    i.putExtra("key",name);
                    startActivity(i);
                    finish();

                }
            }
        });


        LinearLayout lin_spl_layout=rowView.findViewById(R.id.lin_spl_layout);
        if (NetworkConnection.checkNetworkStatus(context) == true) {
            ApigetSamlist();
        } else {
            Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
            String Json;
            Json = sessionParam.getJson("services",context);
            try {
                if (Json!=null) {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray = jsonObject.optJSONArray("user");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ServiceType notificationss = new ServiceType();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String type = jsonObject1.optString("type");

                        notificationss.setType(type);

                        serviceTypes_list1.add(notificationss);

                    }

                    for (int i = 0; i < serviceTypes_list1.size(); i++) {
                        serviceTypes_list.add(serviceTypes_list1.get(i).getType());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    ArrayAdapter adapter_service = new ArrayAdapter(context, android.R.layout.simple_spinner_item, serviceTypes_list);
                    adapter_service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_services.setAdapter(adapter_service);

                }else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
        new AlertDialog.Builder(Act_other_services.this)
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_samities :
                //Your Action Here.
                name=serviceTypes_list1.get(i).getType();

//                log=booth_list1.get(i).getEleLongitude();


//                Toast.makeText(getApplicationContext(),course, Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void ApigetSamlist(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),"services",context);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    serviceTypes_list1=baseRequest.getDataList(jsonArray,ServiceType.class);

                    for (int i=0;i<serviceTypes_list1.size();i++){
                        serviceTypes_list.add(serviceTypes_list1.get(i).getType());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    ArrayAdapter adapter_service = new ArrayAdapter(context,android.R.layout.simple_spinner_item,serviceTypes_list);
                    adapter_service.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_services.setAdapter(adapter_service);




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
        String remainingUrl2="/Election/Api2.php?apicall=other_services_type_list";
        baseRequest.callAPIGETData(1, remainingUrl2);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_other_services.this, Act_election.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_other_services.this, Act_election.class);
        startActivity(i);
        finish();
    }
}
