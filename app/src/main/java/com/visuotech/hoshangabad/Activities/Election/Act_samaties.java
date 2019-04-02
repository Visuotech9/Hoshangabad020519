package com.visuotech.hoshangabad.Activities.Election;

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

import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.Model.Designation_Details;
import com.visuotech.hoshangabad.Model.Samities;
import com.visuotech.hoshangabad.NetworkConnection;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Act_samaties extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    LinearLayout container,lay,lay1,lay2;
    TextView tv_text,tv_text1,tv_text2;
    Spinner spinner_samities,spinner_level,spinner_assembly;
    String id,designation,booth_name,mobile,name,level,lat,log,assembly;
    Button btn_cancel,btn_submit;
    int designation_no;
    ArrayList<Samities> samiti_list1;
    ArrayList<Designation_Details> desi_details_list1;
    ArrayList<String>  samiti_list= new ArrayList<String>();
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    LinearLayout lin_spl_layout;
    String[] level_list = { "District","Assembly"};
    String[] assembly_list = { "136- Seoni malwa","137- Hoshangabad","138- Sohagpur","139- Pipariya"};
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
        getSupportActionBar().setTitle("Committees");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);


        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_samaties, null);

        permission();
         lin_spl_layout=rowView.findViewById(R.id.lin_spl_layout);
        spinner_samities = rowView.findViewById(R.id.spinner_samities);
        spinner_assembly = rowView.findViewById(R.id.spinner_assembly);
        tv_text = rowView.findViewById(R.id.tv_text);
        spinner_level = rowView.findViewById(R.id.spinner_level);
        tv_text1 = rowView.findViewById(R.id.tv_text1);
        tv_text2 = rowView.findViewById(R.id.tv_text2);
        btn_submit = rowView.findViewById(R.id.btn_submit);

        lay = rowView.findViewById(R.id.lay);
        lay1 = rowView.findViewById(R.id.lay1);
        lay2 = rowView.findViewById(R.id.lay2);

        tv_text2.setText("Select Committee:");
        tv_text.setText("Select Level:");
        tv_text1.setText("Select Assembly:");

        container.addView(rowView, container.getChildCount());
        spinner_samities.setOnItemSelectedListener(this);
        spinner_level.setOnItemSelectedListener(this);
        spinner_assembly.setOnItemSelectedListener(this);


        ArrayAdapter adapter_level = new ArrayAdapter(context,android.R.layout.simple_spinner_item,level_list);
        adapter_level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_level.setAdapter(adapter_level);

        ArrayAdapter adapter_assembly = new ArrayAdapter(context,android.R.layout.simple_spinner_item,assembly_list);
        adapter_assembly.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_assembly.setAdapter(adapter_assembly);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (samiti_list1!=null){
                    Intent i = new Intent(Act_samaties.this, Act_sam_mem_list.class);
                    i.putExtra("Id",id);
                    i.putExtra("Name",name);
//                    i.putExtra("LONGITUDE",log);
                    startActivity(i);
                    finish();

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
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add("Write storage");
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE))
            permissionsNeeded.add("Read storage");
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("Call phone");
        if (!addPermission(permissionsList, Manifest.permission.SEND_SMS))
            permissionsNeeded.add("Send sms");




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
        new AlertDialog.Builder(Act_samaties.this)
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
                perms.put(Manifest.permission.SEND_SMS, PackageManager.PERMISSION_GRANTED);
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

            case R.id.spinner_level :
                //Your Action Here.
                level=level_list[i];

                if (level.equals("Assembly")){
                    lay1.setVisibility(View.VISIBLE);
                    tv_text1.setVisibility(View.VISIBLE);
                    samiti_list.clear();

                    if (NetworkConnection.checkNetworkStatus(context) == true) {
                        ApigetSamlist2(level);
                    } else {
                        Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    }


                }else if (level.equals("District")){
                    lay1.setVisibility(View.GONE);
                    tv_text1.setVisibility(View.GONE);
                    samiti_list.clear();

                    if (NetworkConnection.checkNetworkStatus(context) == true) {
                        ApigetSamlist(level);
                    } else {
                        Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    }

                }

                break;

            case R.id.spinner_assembly :
                //Your Action Here.
                assembly=assembly_list[i];
                samiti_list.clear();

                if (NetworkConnection.checkNetworkStatus(context) == true) {
                    ApigetSamlist2(level);
                } else {
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                }
                break;

            case R.id.spinner_samities :
                //Your Action Here.
                id=samiti_list1.get(i).getSamitiId();
                name=samiti_list1.get(i).getSamitiName();
                break;



        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void ApigetSamlist2(String level){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    samiti_list1=baseRequest.getDataList(jsonArray,Samities.class);

                    for (int i=0;i<samiti_list1.size();i++){
                        samiti_list.add(samiti_list1.get(i).getSamitiName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    ArrayAdapter adapter_samitie = new ArrayAdapter(context,android.R.layout.simple_spinner_item,samiti_list);
                    adapter_samitie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_samities.setAdapter(adapter_samitie);




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
        String remainingUrl2="/Election/Api2.php?apicall=samiti_list&samiti_level="+level +"&samiti_assembly="+assembly;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }


    private void ApigetSamlist(String level){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    samiti_list1=baseRequest.getDataList(jsonArray,Samities.class);

                    for (int i=0;i<samiti_list1.size();i++){
                        samiti_list.add(samiti_list1.get(i).getSamitiName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    ArrayAdapter adapter_samitie = new ArrayAdapter(context,android.R.layout.simple_spinner_item,samiti_list);
                    adapter_samitie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_samities.setAdapter(adapter_samitie);




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
        String remainingUrl2="/Election/Api2.php?apicall=samiti_list&samiti_level="+level;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_samaties.this, Act_election.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_samaties.this, Act_election.class);
        startActivity(i);
        finish();
    }


}
