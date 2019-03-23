package com.visuotech.hoshangabad.Activities.Election.Seoni_malwa;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdView;
import com.visuotech.hoshangabad.Fragment.Frag_list;
import com.visuotech.hoshangabad.Fragment.Frag_location;
import com.visuotech.hoshangabad.Fragment.Frag_map;
import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;

import java.util.ArrayList;
import java.util.List;


public class Act_Polling_list extends AppCompatActivity {
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    AdView adView;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    Context context;
    ProgressBar progressbar;
    ArrayList<Integer> array_image = new ArrayList<Integer>();
    ArrayList<String> array_name = new ArrayList<String>();
    String booth_name,lat,log,AC;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Activity activity;
    DrawerLayout drawer;
    NavigationView navigationView;
    Dialog mDialog;
    Frag_list frag_list;
    Frag_location frag_location;
    Frag_map frag_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act__home);


        Intent intent = getIntent();
        booth_name = intent.getStringExtra("NAME");
        lat = intent.getStringExtra("LATITUDE");
        log = intent.getStringExtra("LONGITUDE");
        AC = intent.getStringExtra("CITY");
        Log.d("ID LOCATION", booth_name);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle(booth_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        MobileAds.initialize(this,"ca-app-pub-3940256099942544/1033173712");
//        adView=findViewById(R.id.adView);
//        AdRequest adRequest=new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        context=this;
        activity = this;
        sessionParam = new SessionParam(getApplicationContext());
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

         frag_map = new Frag_map();
         frag_location = new Frag_location();
         frag_list = new Frag_list();



        Bundle bundle = new Bundle();
        bundle.putString("NAME",booth_name);
        bundle.putString("LATITUDE",lat);
        bundle.putString("LONGITUDE",log);
        bundle.putString("CITY",AC);
        frag_list.setArguments(bundle);

        Bundle bundle2 = new Bundle();
        bundle.putString("NAME",booth_name);
        bundle.putString("LATITUDE",lat);
        bundle.putString("LONGITUDE",log);
        frag_map.setArguments(bundle2);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setSmoothScrollingEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(frag_list, "List");
        adapter.addFragment(frag_map, "Map");
        adapter.addFragment(frag_location, "Queue status");
//        adapter.addFragment(new Frag_Send_Gift(), "Send Items");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }
/*
    public void onBackPressed() {
        new AlertDialog.Builder(Act_Polling_list.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                        finish();

                        Intent a = new Intent(Intent.ACTION_MAIN);
                        a.addCategory(Intent.CATEGORY_HOME);
                        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(a);
                    }

                })
                .setNegativeButton("No", null)
                .show();


    }
*/
public boolean onOptionsItemSelected(MenuItem item) {
//    Intent i = new Intent(Act_Polling_list.this, Act_polling_station.class);
//    startActivity(i);
    finish();
    return true;

}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(Act_Polling_list.this, Act_polling_station.class);
//        startActivity(i);
        finish();
    }

}
