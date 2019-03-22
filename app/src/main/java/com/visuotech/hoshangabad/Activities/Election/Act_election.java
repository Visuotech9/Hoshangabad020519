package com.visuotech.hoshangabad.Activities.Election;

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
import android.widget.LinearLayout;

import com.visuotech.hoshangabad.Activities.Act_home;
import com.visuotech.hoshangabad.Activities.Election.Pipariya.Act_pipariya;
import com.visuotech.hoshangabad.Activities.Election.Seoni_malwa.Act_samaties;
import com.visuotech.hoshangabad.Activities.Election.Seoni_malwa.Act_seoni_malwa;
import com.visuotech.hoshangabad.Activities.Election.Hoshangabad.Act_hoshangabad;
import com.visuotech.hoshangabad.Activities.Election.Sohagpur.Act_sohagpur;
import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;

public class Act_election extends AppCompatActivity {
    LinearLayout lay1,lay2,lay3,lay4,lay5,lay6,lay8;
    LinearLayout container;
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
        getSupportActionBar().setTitle("Election");
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        context = this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        marshMallowPermission = new MarshMallowPermission(activity);


        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.contain_main_election, null);

        lay1=rowView.findViewById(R.id.lay1);
        lay2=rowView.findViewById(R.id.lay2);
        lay3=rowView.findViewById(R.id.lay3);
        lay4=rowView.findViewById(R.id.lay4);
        lay8=rowView.findViewById(R.id.lay8);

        container.addView(rowView, container.getChildCount());


        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_election.this, Act_seoni_malwa.class);
                startActivity(i);
                finish();

            }
        });
        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_election.this, Act_hoshangabad.class);
                startActivity(i);
                finish();
            }
        });

        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_election.this, Act_sohagpur.class);
                startActivity(i);
                finish();

            }
        });

        lay4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_election.this, Act_pipariya.class);
                startActivity(i);
                finish();
            }
        });
        lay8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_election.this, Act_samaties.class);
                startActivity(i);
                finish();
            }
        });



    }
    public void onAddField() {



    }
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_election.this, Act_home.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_election.this, Act_home.class);
        startActivity(i);
        finish();
    }

}
