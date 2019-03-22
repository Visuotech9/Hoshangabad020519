package com.visuotech.hoshangabad.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.visuotech.hoshangabad.CustomInfoWindowGoogleMap;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.retrofit.ApiInterface;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Frag_map extends Fragment implements OnMapReadyCallback{
    public GoogleMap map;
    private GoogleMap mMap;
    public double longitude1=22.7533;
    public double latitude1=75.8937;
    public double longitude2;
    public double latitude2;
    public String address;
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
    ArrayList<String> prgmName;
    LatLng location1;
    double radius = 100; // 100 meters
    private String mParam1;
    SharedPreferences shared;
    ArrayList<String> arrPackage;
    String booth_name,lat,log;
    SupportMapFragment mapFragment;
    public CustomInfoWindowGoogleMap customInfoWindow;
    public Frag_map() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            booth_name = getArguments().getString("NAME");
            lat = getArguments().getString("LATITUDE");
            log = getArguments().getString("LONGITUDE");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_frag_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
//         address= Utility.getAddressFromLatlong(getContext(),latitude1,longitude1);
        customInfoWindow = new CustomInfoWindowGoogleMap(getContext());

        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses   = geocoder.getFromLocation(22.7533, 75.8937, 1);
            address= addresses.get(0).getAddressLine(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mapFragment== null){
            FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();
            mapFragment=SupportMapFragment.newInstance();
            ft.replace(R.id.map,mapFragment).commit();
        }
//        mapFragment.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(GoogleMap mMap) {
//                map = mMap;
//                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                map.getUiSettings().setCompassEnabled(true);
//                map.getUiSettings().setZoomGesturesEnabled(true);

//
////                for(int i = 0 ; i < list.size() ; i++) {
////                    latitude1= Double.parseDouble(list.get(i).getLatitude());
////                    longitude1= Double.parseDouble(list.get(i).getLongitude());
////                    address= (list.get(i).getAddress());
////                    date_map= (list.get(i).getDate());
////                }
//                createMarker(Double.parseDouble(lat), Double.parseDouble(log), address, date_map);
////                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(list.get(0).getLatitude()), Double.parseDouble(list.get(0).getLongitude()))));
////                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat), Double.parseDouble(log)), 17.0f));
//                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude1, longitude1), 17.0f));
//
//
//            }
//
//        });


        mapFragment.getMapAsync(this);
        return rootView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(22.7533, 75.8937);
//        mMap.addMarker(new MarkerOptions().position(sydney).title(address));
        if (address.isEmpty()){
            createMarker(22.7533, 75.8937,booth_name,"");
        }else{
            createMarker(22.7533, 75.8937,booth_name,address);
        }

        mMap.setInfoWindowAdapter(customInfoWindow);
        customInfoWindow.getInfoWindow(createMarker(22.7533, 75.8937,booth_name,address));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CameraPosition camPos = new CameraPosition.Builder()
                .target(new LatLng(22.7533, 75.8937))
                .zoom(18)
                .tilt(70)
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



}
