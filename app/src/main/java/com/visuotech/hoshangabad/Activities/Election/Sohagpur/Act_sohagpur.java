package com.visuotech.hoshangabad.Activities.Election.Sohagpur;

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

import com.visuotech.hoshangabad.Activities.Election.Act_election;
import com.visuotech.hoshangabad.Activities.Election.Hoshangabad.Act_hoshangabad;
import com.visuotech.hoshangabad.Activities.Election.Seoni_malwa.Act_polling_station;
import com.visuotech.hoshangabad.Activities.Election.Seoni_malwa.Act_vidhansabha;
import com.visuotech.hoshangabad.R;

public class Act_sohagpur extends AppCompatActivity {
    LinearLayout lay1,lay2,lay3,lay4,lay5,lay6;
    LinearLayout container;
    String AC="138- Sohagpur";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("138- Sohagpur");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.contain_main_seonimalwa, null);

        lay1=rowView.findViewById(R.id.lay1);
        lay2=rowView.findViewById(R.id.lay2);
        lay3=rowView.findViewById(R.id.lay3);

        container.addView(rowView, container.getChildCount());

        lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_sohagpur.this, Act_polling_station.class);
                i.putExtra("CITY",AC);
                startActivity(i);

            }
        });
        lay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_sohagpur.this, Act_vidhansabha.class);
                i.putExtra("CITY",AC);
                startActivity(i);

            }
        });

        lay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Act_sohagpur.this, Act_vidhansabha.class);
                startActivity(i);

            }
        });
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Act_sohagpur.this, Act_election.class);
        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Act_sohagpur.this, Act_election.class);
        startActivity(i);
        finish();
    }
}
