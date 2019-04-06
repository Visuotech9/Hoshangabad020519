package com.visuotech.hoshangabad_election.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
import com.visuotech.hoshangabad_election.Activities.MapActivity;
import com.visuotech.hoshangabad_election.CustomInfoWindowGoogleMap;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.directionhelpers.FetchURL;
import com.visuotech.hoshangabad_election.directionhelpers.TaskLoadedCallback;
import com.visuotech.hoshangabad_election.retrofit.ApiInterface;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.Utility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class Frag_map extends Fragment implements OnMapReadyCallback {
    public GoogleMap map;
    private GoogleMap mMap;
    public double longitude1=22.7533;
    public double latitude1=75.8937;
    public double longitude2;
    public double latitude2;
    public String add_booth;
    public String add;
    private static final String PREF_NAME = "logininf";
    private BaseRequest baseRequest;

    public String user_id, date,date_map;
    ImageView iv_back, iv_map, iv_list;
    //------------------------------
    private int TOTAL_PAGES;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    private static final int PAGE_START = 1;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private boolean isLoading;
    ApiInterface apiInterface;
    private static final String TAG = "USERLOCATION";
    private List<Address> addresses;
    private String lat_current = "";
    private String lon_current = "";
    private String lat_booth = "";
    private String lon_booth = "";
    private String add_current = "";
    ArrayList<String> prgmName;
    LatLng location1;
    double radius = 100; // 100 meters
    private String mParam1;
    SharedPreferences shared;
    ArrayList<String> arrPackage;
    String booth_name;
    double lati_booth,long_booth;
    double lat_curr,lon_curr;

    SupportMapFragment mapFragment;
    public CustomInfoWindowGoogleMap customInfoWindow;
    ArrayList<LatLng> markerPoints;
    private MarkerOptions place1, place2;
    Button getDirection;
    private Polyline currentPolyline;


    public Frag_map() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            booth_name = getArguments().getString("NAME");
            lat_booth = (getArguments().getString("LATITUDE"));
            lon_booth = (getArguments().getString("LONGITUDE"));
            add_current = getArguments().getString("ADDRESS");
        }
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_frag_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        lat_curr=Double.parseDouble(lat_booth);
        lon_curr=Double.parseDouble(lon_booth);
//        lati_booth=Double.valueOf(lat_booth);
//        long_booth=Double.valueOf(lon_booth);

        Button btn_dirn=rootView.findViewById(R.id.btn_getLocation);
        btn_dirn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat_curr+","+lon_curr);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });

//        try {
//            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//            List<Address> addresses   = geocoder.getFromLocation(lat_curr, lon_curr, 1);
//            add_booth= addresses.get(0).getAddressLine(0);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        customInfoWindow = new CustomInfoWindowGoogleMap(getContext());

        if (mapFragment== null){
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return rootView;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.parseDouble(lat_booth), Double.parseDouble(lon_booth));
//        mMap.addMarker(new MarkerOptions().position(sydney).title(address));
        createMarker(Double.parseDouble(lat_booth), Double.parseDouble(lon_booth), booth_name, add_current);

//        if (add_current.equals(null)){
//            createMarker(Double.parseDouble(lat_booth), Double.parseDouble(lon_booth), booth_name, "");
//        }else{
//            createMarker(Double.parseDouble(lat_booth), Double.parseDouble(lon_booth), booth_name, add_current);
//            }

        mMap.setInfoWindowAdapter(customInfoWindow);
        customInfoWindow.getInfoWindow(createMarker(Double.parseDouble(lat_booth), Double.parseDouble(lon_booth),booth_name,add_booth));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(Double.parseDouble(lat_booth), Double.parseDouble(lon_booth)))
                .zoom(18)
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

    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(positions);
        Polyline polyline = map.addPolyline(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(22.7533,75.8937))
//                .target(new LatLng(22.7533, positions.get(1).longitude))
                .zoom(17)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

//    @Override
//    public void onTaskDone(Object... values) {
//        if (currentPolyline != null)
//            currentPolyline.remove();
//        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
//    }


}
