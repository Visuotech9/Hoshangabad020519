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
import com.visuotech.hoshangabad.Model.Samities;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_samaties extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    LinearLayout container;
    Spinner spinner_samities;
    String id,designation,booth_name,mobile,name,desig,lat,log;
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
    String[] designation_list = { "P0","P1","BLO","GRS","SACHIV","THANA","Sector Officer","Local Contact Person-1","Local Contact Person-2"};

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


        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_samaties, null);

        spinner_samities = rowView.findViewById(R.id.spinner_samities);
        btn_cancel = rowView.findViewById(R.id.btn_cancel);
        btn_submit = rowView.findViewById(R.id.btn_submit);

        container.addView(rowView, container.getChildCount());
        spinner_samities.setOnItemSelectedListener(this);

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
        ApigetSamlist();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_samities :
                //Your Action Here.
                id=samiti_list1.get(i).getSamitiId();
                name=samiti_list1.get(i).getSamitiName();
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
        String remainingUrl2="/Election/Api2.php?apicall=samiti_list";
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
