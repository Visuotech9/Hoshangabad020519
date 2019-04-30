package com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.NetworkConnection;
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
    String booth_name,male,female,boothcode,total,AC,station_id,election_id;
    TextView tv_name,tv_code,tv_message;
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
        AC=intent.getStringExtra("AC");
        station_id=intent.getStringExtra("STATION_ID");
        election_id=intent.getStringExtra("ELECTION_ID");


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
        tv_message = rowView.findViewById(R.id.tv_message);


        tv_name.setText(booth_name);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    boothcode=et_password.getText().toString();
                    callApilogin();


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

                    callApiSubmit();

                }
            }
        });

        container.addView(rowView, container.getChildCount());

    }

    private void callApiSubmit() {

        if (NetworkConnection.checkNetworkStatus(getApplicationContext())==true) {
            api_addDetails();
        }else {
            sucessDialog(getResources().getString(R.string.Internet_connection),context);
        }

    }

    private void callApilogin() {

        if (NetworkConnection.checkNetworkStatus(getApplicationContext())==true) {
            api_login();
        }else {
            sucessDialog2(getResources().getString(R.string.Internet_connection),context);
        }

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

//                    Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
                    et_female.setText("");
                    et_male.setText("");
                    tv_message.setVisibility(View.VISIBLE);

//                    if (message.equals("Login Successfull")){
//                        lay_add.setVisibility(View.VISIBLE);
//                        lay_login.setVisibility(View.GONE);
//                    }

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
        RequestBody AC_ = RequestBody.create(MediaType.parse("text/plain"), AC);
        RequestBody election_id_ = RequestBody.create(MediaType.parse("text/plain"), election_id);
        RequestBody station_id_ = RequestBody.create(MediaType.parse("text/plain"), station_id);

        baseRequest.callAPIBookedItems(1,"http://collectorexpress.in/",male_,female_,total_,deviceId_,booth_name_,
                AC_,election_id_,station_id_);

    }

    public void sucessDialog(String message,Context context) {
//        textView.setText(getResources().getString(R.string.txt_hello));

        final Dialog mDialog=new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.notification_dailog);
        mDialog.setCanceledOnTouchOutside(true);

        Button btn_ok;
        TextView tv_retry;
        TextView tv_notification;
        btn_ok= mDialog.findViewById(R.id.btn_ok);
        tv_retry= mDialog.findViewById(R.id.tv_retry);
        tv_notification= mDialog.findViewById(R.id.tv_notification);
        tv_notification.setText(message);
        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
                callApiSubmit();

            }
        });
        mDialog.show();


    }


    public void sucessDialog2(String message,Context context) {
//        textView.setText(getResources().getString(R.string.txt_hello));

        final Dialog mDialog=new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);  //without extar space of title
        mDialog.setContentView(R.layout.notification_dailog);
        mDialog.setCanceledOnTouchOutside(true);

        Button btn_ok;
        TextView tv_retry;
        TextView tv_notification;
        btn_ok= mDialog.findViewById(R.id.btn_ok);
        tv_retry= mDialog.findViewById(R.id.tv_retry);
        tv_notification= mDialog.findViewById(R.id.tv_notification);
        tv_notification.setText(message);
        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.cancel();
                callApilogin();


            }
        });
        mDialog.show();


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
