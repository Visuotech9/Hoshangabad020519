package com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.visuotech.hoshangabad_election.Fragment.Frag_list;
import com.visuotech.hoshangabad_election.Fragment.Frag_queue;
import com.visuotech.hoshangabad_election.Fragment.Frag_map;
import com.visuotech.hoshangabad_election.Location.GPSTracker;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Act_Polling_list extends AppCompatActivity {
    RecyclerView rv;
    LinearLayoutManager linearLayoutManager;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    Context context;
    ProgressBar progressbar;
    ArrayList<Integer> array_image = new ArrayList<Integer>();
    ArrayList<String> array_name = new ArrayList<String>();
    String booth_name,lat,log,AC;
    private String lat_current = "";
    private String lon_current = "";
    String address;
    private Boolean flag = false;
    private List<Address> addresses;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Activity activity;
    DrawerLayout drawer;
    NavigationView navigationView;
    Dialog mDialog;
    Frag_list frag_list;
    Frag_queue frag_queue;
    Frag_map frag_map;
    public boolean datafinish = false;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

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
        frag_queue = new Frag_queue();
         frag_list = new Frag_list();

        permission();
        grabLocation();
        Bundle bundle = new Bundle();
        bundle.putString("NAME",booth_name);
        bundle.putString("LATITUDE",lat);
        bundle.putString("LONGITUDE",log);
        bundle.putString("CURRENT_LATITUDE",lat_current);
        bundle.putString("CURRENT_LONGITUDE",lon_current);
        bundle.putString("ADDRESS",address);
        bundle.putString("CITY",AC);
        frag_list.setArguments(bundle);
        frag_map.setArguments(bundle);
        frag_queue.setArguments(bundle);

//        Bundle bundle2 = new Bundle();
//        bundle.putString("NAME",booth_name);
//        bundle.putString("LATITUDE",lat);
//        bundle.putString("LONGITUDE",log);
//        frag_map.setArguments(bundle2);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.setSmoothScrollingEnabled(true);
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
        new AlertDialog.Builder(Act_Polling_list.this)
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(frag_list, "List");
        adapter.addFragment(frag_map, "Map");
        adapter.addFragment(frag_queue, "Queue status");
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

    public void grabLocation() {
        // TODO Auto-generated method stub
        GPSTracker gpsTracker = new GPSTracker(Act_Polling_list.this);

        if (gpsTracker.canGetLocation()) {
            lat_current = String.valueOf(gpsTracker.getLatitude());
            lon_current = String.valueOf(gpsTracker.getLongitude());
            if (lat.equals("0.0") && log.equals("0.0")) {
                gpsTracker.showSettingsAlert();
            } else {
                try {
                    Geocoder geocoder = new Geocoder(Act_Polling_list.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(Double.valueOf(lat), Double.valueOf(log), 1);
                     address = addresses.get(0).getAddressLine(0);
                    // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    String add=city + " , " + state + " , " + country;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            }
        } else {
            gpsTracker.showSettingsAlert();
        }
    }


}
