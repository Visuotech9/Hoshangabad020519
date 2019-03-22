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

import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.Model.Designation_Details;
import com.visuotech.hoshangabad.Model.Vidhansabha_Design;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_vidhansabha extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    LinearLayout container;
    Spinner spinner_station, spinner_designation;
    String station, designation, booth_name, mobile, name, desig, lat, log;
    Button btn_cancel, btn_submit;
    int designation_no;
    ArrayList<Vidhansabha_Design> designation_list1;
    ArrayList<Designation_Details> desi_details_list1;
    ArrayList<String> designation_list = new ArrayList<String>();
    String AC;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        //-------------------------toolbar------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Vidhansabha");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);


        container = (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_vidhansabha, null);

        spinner_designation = rowView.findViewById(R.id.spinner_designation);
        btn_cancel = rowView.findViewById(R.id.btn_cancel);
        btn_submit = rowView.findViewById(R.id.btn_submit);

        container.addView(rowView, container.getChildCount());
        spinner_designation.setOnItemSelectedListener(this);

        Intent intent=getIntent();
        AC=intent.getStringExtra("CITY");

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (designation_list1 != null) {
                    Intent i = new Intent(Act_vidhansabha.this, Act_vidhansabha_details_list.class);
                    i.putExtra("designation", designation);
                    i.putExtra("CITY", AC);
//                    i.putExtra("LATITUDE",lat);
//                    i.putExtra("LONGITUDE",log);
                    startActivity(i);

                }
            }
        });

        ApigetDesiglist();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_designation :
                //Your Another Action Here.
                designation=designation_list1.get(i).getDesignation();

//                Toast.makeText(getApplicationContext(),gender_list[i] , Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void ApigetDesiglist(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    designation_list1=baseRequest.getDataList(jsonArray,Vidhansabha_Design.class);

                    for (int i=0;i<designation_list1.size();i++){
                        designation_list.add(designation_list1.get(i).getDesignation());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    ArrayAdapter adapter_designation = new ArrayAdapter(context,android.R.layout.simple_spinner_item,designation_list);
                    adapter_designation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_designation.setAdapter(adapter_designation);




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
        String remainingUrl2="/Election/Api2.php?apicall=vidhansabha_desg_list"+"&ac="+AC;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
       finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(Act_vidhansabha.this, Act_seoni_malwa.class);
//        startActivity(i);
        finish();
    }

}
