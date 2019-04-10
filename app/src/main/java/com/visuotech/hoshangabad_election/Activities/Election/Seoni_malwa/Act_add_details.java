package com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Act_add_details extends AppCompatActivity {
    LinearLayout container,lay_btn,lay_add,lay_login;
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;

    Button btn_login,btn_submit;
    EditText et_password;
    int maleCount,femaleCount,totalCount;
    String booth_name,male,female,boothcode,total;
    TextView tv_name,tv_code;
    EditText et_male,et_female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        //-------------------------toolbar------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));

        Intent intent=getIntent();
        booth_name=intent.getStringExtra("NAME_booth");


        getSupportActionBar().setTitle("BLO LOGIN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);

        container = (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_add_details, null);

        btn_login = rowView.findViewById(R.id.btn_login);
        btn_submit = rowView.findViewById(R.id.btn_submit);
        et_female = rowView.findViewById(R.id.et_female);
        et_male = rowView.findViewById(R.id.et_male);
        lay_btn = rowView.findViewById(R.id.lay_btn);
        tv_name = rowView.findViewById(R.id.tv_name);
        tv_code = rowView.findViewById(R.id.tv_code);
        lay_login = rowView.findViewById(R.id.lay_login);
        lay_add = rowView.findViewById(R.id.lay_add);
        et_password = rowView.findViewById(R.id.et_password);
        container.addView(rowView, container.getChildCount());
        tv_name.setText(booth_name);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    boothcode=et_password.getText().toString();
                    api_login();

                }
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_male.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter male count", Toast.LENGTH_SHORT).show();
                    return;
                } if (et_female.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter female count", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    male=et_male.getText().toString();
                    female=et_female.getText().toString();

                    maleCount= Integer.parseInt(male);
                    femaleCount= Integer.parseInt(female);
                    totalCount=maleCount+femaleCount;
                    total= String.valueOf(totalCount);

                    api_addDetails();

                }
            }
        });



    }

    private void api_login() {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                sessionParam.loginSession(context);

                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String message=jsonObject.getString("message");
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    if (message.equals("Login Successfull")){
                        lay_add.setVisibility(View.VISIBLE);
                        lay_login.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {


                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        RequestBody booth_name_ = RequestBody.create(MediaType.parse("text/plain"), booth_name);
        RequestBody boothcode_ = RequestBody.create(MediaType.parse("text/plain"), boothcode);

        baseRequest.callAPILogin2(1,"http://collectorexpress.in/",booth_name_,boothcode_);

    }

    private void api_addDetails() {
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                sessionParam.loginSession(context);

                try {
                    JSONObject jsonObject = new JSONObject(object.toString());
                    String message=jsonObject.getString("message");
                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    if (message.equals("Login Successfull")){
                        lay_add.setVisibility(View.VISIBLE);
                        lay_login.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {


                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int requestCode, String errorCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNetworkFailure(int requestCode, String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

            }
        });
        RequestBody male_ = RequestBody.create(MediaType.parse("text/plain"), male);
        RequestBody female_ = RequestBody.create(MediaType.parse("text/plain"), female);
        RequestBody total_ = RequestBody.create(MediaType.parse("text/plain"), total);
        RequestBody deviceId_ = RequestBody.create(MediaType.parse("text/plain"), sessionParam.deviceId);
        RequestBody booth_name_ = RequestBody.create(MediaType.parse("text/plain"), booth_name);

        baseRequest.callAPIBookedItems(1,"http://collectorexpress.in/",male_,female_,total_,deviceId_,booth_name_);

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
