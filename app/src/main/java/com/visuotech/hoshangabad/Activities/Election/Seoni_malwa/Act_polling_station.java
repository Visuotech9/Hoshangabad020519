package com.visuotech.hoshangabad.Activities.Election.Seoni_malwa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.visuotech.hoshangabad.Activities.Election.Act_election;
import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.Model.Designation_Details;
import com.visuotech.hoshangabad.Model.PollingBooth;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_polling_station extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    LinearLayout container;
    Spinner spinner_station, spinner_designation;
    String station, designation, booth_name, mobile, name, desig, lat, log;
    Button btn_submit;
    int designation_no;
    ArrayList<PollingBooth> booth_list1;
    ArrayList<Designation_Details> desi_details_list1;
    ArrayList<String> booth_list = new ArrayList<String>();
    Context context;
    String AC;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    String[] designation_list = {"P0", "P1", "BLO", "GRS", "SACHIV", "THANA", "Sector Officer", "Local Contact Person-1", "Local Contact Person-2", "--Select gender--"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        //-------------------------toolbar------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Stations");
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

        spinner_station = rowView.findViewById(R.id.spinner_station);

        btn_submit = rowView.findViewById(R.id.btn_submit);

        container.addView(rowView, container.getChildCount());
        spinner_station.setOnItemSelectedListener(this);


//        spinner_designation.setSelection(listsize);
        Apigetboothlist();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (booth_list1 != null) {
                    Intent i = new Intent(Act_polling_station.this, Act_Polling_list.class);
                    i.putExtra("NAME", station);
                    i.putExtra("CITY", AC);
//                    i.putExtra("LATITUDE",lat);
//                    i.putExtra("LONGITUDE",log);
                    startActivity(i);


                }
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_station :
                //Your Action Here.
                station=booth_list1.get(i).getEleBoothName();
//                lat=booth_list1.get(i).getEleLatitude();
//                log=booth_list1.get(i).getEleLongitude();


//                Toast.makeText(getApplicationContext(),course, Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    private void ApigetDetailList() {
//        baseRequest = new BaseRequest();
//        baseRequest.setBaseRequestListner(new RequestReciever() {
//            @Override
//            public void onSuccess(int requestCode, String Json, Object object) {
//                try {
//                    JSONObject jsonObject = new JSONObject(Json);
//                    JSONArray jsonArray=jsonObject.optJSONArray("user");
//
//                    desi_details_list1=baseRequest.getDataList(jsonArray,Designation_Details.class);
//                    mobile=desi_details_list1.get(0).getEle_local1_mobile();
//                    name=desi_details_list1.get(0).getEle_local1_name();
//                    booth_name=desi_details_list1.get(0).getEle_booth_name();
//                    desig=desi_details_list1.get(0).getDesignation();
//
//                    Intent i = new Intent(Act_polling_station.this, Act_Polling_list.class);
//                    i.putExtra("NAME",booth_name);
//                    startActivity(i);
//                    finish();

//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(int requestCode, String errorCode, String message) {
//
//            }
//            @Override
//            public void onNetworkFailure(int requestCode, String message) {
//
//            }
//        });
//        String remainingUrl2="/Election/Api2.php?apicall=polling_booth"+"&ac="+AC+"&booth_name="+station+"&designation="+designation_no;
//        baseRequest.callAPIGETData(1, remainingUrl2);
//    }


    private void Apigetboothlist(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list1=baseRequest.getDataList(jsonArray,PollingBooth.class);

                    for (int i=0;i<booth_list1.size();i++){
                        booth_list.add(booth_list1.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
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
