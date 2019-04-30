package com.visuotech.hoshangabad_election.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.visuotech.hoshangabad_election.Activities.Election.Act_services_list;
import com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa.Act_Polling_list;
import com.visuotech.hoshangabad_election.CustomInfoWindowGoogleMap;
import com.visuotech.hoshangabad_election.Location.GPSTracker;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.directionhelpers.FetchURL;
import com.visuotech.hoshangabad_election.directionhelpers.TaskLoadedCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vishal on 10/20/2018.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    LinearLayout container;
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;
    Context context;
    public CustomInfoWindowGoogleMap customInfoWindow;

    private String lat_serviec = "";
    private String lon_service = "";
    private String booth_name = "";

    private String lat_current = "";
    private String lon_current = "";

    double lat,lon;
    String add_booth;
    private List<Address> addresses;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        container = (LinearLayout) findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_maps, null);
        container.addView(rowView, container.getChildCount());
        context=this;
        Intent intent=getIntent();
        lat_serviec= String.valueOf(intent.getStringExtra("LAT"));
        lon_service= String.valueOf(intent.getStringExtra("LOG"));
        booth_name=intent.getStringExtra("NAME");

//        grabLocation();

        customInfoWindow = new CustomInfoWindowGoogleMap(context);

        getDirection = rowView.findViewById(R.id.btnGetDirection);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapNearBy);

        getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+Double.parseDouble(lat_serviec)+","+Double.parseDouble(lon_service));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });


        try {
            Geocoder geocoder = new Geocoder(MapActivity.this, Locale.getDefault());
            List<Address> addresses   = geocoder.getFromLocation(Double.parseDouble(lat_serviec),Double.parseDouble(lon_service), 1);
            add_booth= addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }


        place1 = new MarkerOptions().position(new LatLng(Double.parseDouble(lat_serviec), Double.parseDouble(lon_service))).title(booth_name).snippet(add_booth);





        mapFragment.getMapAsync(this);
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setZoomGesturesEnabled(true);
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(22.7533, 75.8937);
//        if (add_booth.isEmpty()){
//            createMarker(22.7533,75.8937,booth_name,"");
//        }else{
//            createMarker(22.7533, 75.8937,booth_name,add_booth);
//            }
//
//        mMap.setInfoWindowAdapter(customInfoWindow);
//        customInfoWindow.getInfoWindow(createMarker(22.7533, 75.8937,booth_name,add_booth));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//
//        CameraPosition camPos = new CameraPosition.Builder()
//                .target(new LatLng(22.7533, 75.8937))
//                .zoom(18)
////                .tilt(70)
//                .build();
//        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
//        googleMap.animateCamera(camUpd3);
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);

        LatLng sydney = new LatLng(22.7533, 75.8937);

        mMap.setInfoWindowAdapter(customInfoWindow);
        customInfoWindow.getInfoWindow(mMap.addMarker(place1));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(22.7533, 75.8937))
                .zoom(15)
//                .tilt(70)
                .build();
        CameraUpdate camUpd3 = CameraUpdateFactory.newCameraPosition(camPos);
        googleMap.animateCamera(camUpd3);
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));


//                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent i = new Intent(MapActivity.this, Act_services_list.class);
//        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(MapActivity.this, Act_services_list.class);
//        startActivity(i);
        finish();
    }




}
