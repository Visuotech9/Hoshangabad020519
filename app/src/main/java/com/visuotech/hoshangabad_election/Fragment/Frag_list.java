package com.visuotech.hoshangabad_election.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.Adapter.Ad_designation_details;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Designation_Details;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.NetworkConnection;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;
import com.visuotech.hoshangabad_election.retrofit.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Frag_list extends Fragment implements AdapterView.OnItemSelectedListener{
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    Spinner spinner_designation;
    ArrayList<PollingBooth>booth_list1;
    ArrayList<Designation_Details>designation_details_list;
    ArrayList<String>booth_list=new ArrayList<>();
    Ad_designation_details adapter;
    String AC,name,mobile;
    String booth_name,designation,designation_count;
    EditText inputSearch;
    Activity activity;
    SwipeRefreshLayout mSwipeRefreshLayout;
    Context context;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    LinearLayout lin_spl_layout,lay_design;
    TextView tv_name,tv_designation,tv_mobile;
    LinearLayout lay_message,lay_call;
    FrameLayout lay_recy;
    String[] designation_lists = { "All","P0","P1","BLO","GRS","SACHIV","THANA","Sector Officer","Local Contact Person-1","Local Contact Person-2"};
    String[] designation_list = {"P0","P1","BLO","GRS","SACHIV","THANA","Sector Officer","Local Contact Person-1","Local Contact Person-2"};

    ArrayAdapter adapter_desig;
    public Frag_list() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            booth_name = getArguments().getString("NAME");
            AC = getArguments().getString("CITY");

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_frag_list, container, false);
//        activity=this;
        rv = (RecyclerView) view.findViewById(R.id.rv_list);
        lin_spl_layout=view.findViewById(R.id.lin_spl_layout);
        lay_recy=view.findViewById(R.id.lay_recy);
        lay_design=view.findViewById(R.id.lay_design);
        spinner_designation=view.findViewById(R.id.spinner_designation);
        mSwipeRefreshLayout=view.findViewById(R.id.activity_main_swipe_refresh_layout);
        tv_name=view.findViewById(R.id.tv_name);
        tv_designation =  view.findViewById(R.id.tv_designation);
        tv_mobile =  view.findViewById(R.id.tv_mobile);
        lay_message =  view.findViewById(R.id.lay_message);
        lay_call =  view.findViewById(R.id.lay_call);
        spinner_designation.setOnItemSelectedListener(this);
//        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
//        Utility.hideKeyBoard(activity);


        adapter_desig = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,designation_lists);
        adapter_desig.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_designation.setAdapter(adapter_desig);


        if (NetworkConnection.checkNetworkStatus(getContext()) == true) {
            Apigetboothlist();
        } else {
            Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnection.checkNetworkStatus(getContext())==true){
                    Apigetboothlist();
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();       }


            }
        });

        lay_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POSITION>>>>", tv_mobile.getText().toString());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" +tv_mobile.getText().toString()));
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                getContext().startActivity(intent);
            }

        });
        lay_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POSITION>>>>", tv_mobile.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode(tv_mobile.getText().toString())));
                getContext().startActivity(intent);
            }

        });



//        inputSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //after the change calling the method and passing the search input
//                filter(editable.toString());
//            }
//        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId()){
            case R.id.spinner_designation :
                //Your Another Action Here.
                designation=designation_lists[i];

                if (designation.equals("All")){
                    lay_recy.setVisibility(View.VISIBLE);
                    lay_design.setVisibility(View.GONE);
                    Apigetboothlist();
                }else{
                    designation_count= String.valueOf(i);
                    Apigetdesignation(designation_count);
                }


//                Toast.makeText(getApplicationContext(),gender_list[i] , Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void Apigetboothlist(){
        baseRequest = new BaseRequest(getContext());
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    booth_list1=baseRequest.getDataList(jsonArray,PollingBooth.class);

                    for (int i=0;i<booth_list1.size();i++){
                        booth_list.add(booth_list1.get(i).getEleBoothName());
//                       department_id.add(department_list1.get(i).getDepartment_id());
                    }
                    List<String> design_list = Arrays.asList(designation_list);
                    adapter=new Ad_designation_details(getContext(),booth_list1,design_list);
                    rv.setAdapter(adapter);



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
        String remainingUrl2="/Election/Api2.php?apicall=polling_booth"+"&ac="+AC+"&booth_name="+booth_name;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void Apigetdesignation(final String designation_count){
        baseRequest = new BaseRequest(getContext());
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    designation_details_list=baseRequest.getDataList(jsonArray,Designation_Details.class);
                    lay_recy.setVisibility(View.GONE);
                    lay_design.setVisibility(View.VISIBLE);

                    name=designation_details_list.get(0).getDesignation_name();
                    mobile=designation_details_list.get(0).getDesignation_mobile();

                    tv_name.setText(name);
                    tv_mobile.setText(mobile);
                    tv_designation.setText(designation);




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
        String remainingUrl2="/Election/Api2.php?apicall=polling_booth"+"&ac="+AC+"&booth_name="+booth_name+"&designation="+designation_count;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }



    private void filter(String text) {
        //new array list that will hold the filtered data

        ArrayList<PollingBooth>booth_list2=new ArrayList<>();
        ArrayList<String>desig_list2=new ArrayList<>();

        //looping through existing elements
        for (int i=0;i<designation_list.length;i++){
            if (designation_list[i].toLowerCase().contains(text.toLowerCase())) {
                PollingBooth pollingBooth = new PollingBooth();

                if (i==0){
                    pollingBooth.setElePZero(booth_list1.get(0).getElePZero());
                    pollingBooth.setElePZeroMobile(booth_list1.get(0).getElePZeroMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==1){
                    pollingBooth.setElePOne(booth_list1.get(0).getElePOne());
                    pollingBooth.setElePOneMobile(booth_list1.get(0).getElePOneMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==2){
                    pollingBooth.setEleBloName(booth_list1.get(0).getEleBloName());
                    pollingBooth.setEleBloMobile(booth_list1.get(0).getEleBloMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==3){
                    pollingBooth.setEleGrs(booth_list1.get(0).getEleGrs());
                    pollingBooth.setEleGrsMobile(booth_list1.get(0).getEleGrsMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==4){
                    pollingBooth.setEleSachiv(booth_list1.get(0).getEleSachiv());
                    pollingBooth.setEleSachivMobile(booth_list1.get(0).getEleSachivMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==5){
                    pollingBooth.setEleNearestThana(booth_list1.get(0).getEleNearestThana());
                    pollingBooth.setEleThanaMobile(booth_list1.get(0).getEleThanaMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==6){
                    pollingBooth.setEleSectorOfficer(booth_list1.get(0).getEleSectorOfficer());
                    pollingBooth.setEleSectorMobile(booth_list1.get(0).getEleSectorMobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==7){
                    pollingBooth.setEleLocal1Name(booth_list1.get(0).getEleLocal1Name());
                    pollingBooth.setEleLocal1Mobile(booth_list1.get(0).getEleLocal1Mobile());
                    booth_list2.add(pollingBooth);
                }
                if (i==8){
                    pollingBooth.setEleLocal2Name(booth_list1.get(0).getEleLocal2Name());
                    pollingBooth.setEleLocal2Mobile(booth_list1.get(0).getEleLocal2Mobile());
                    booth_list2.add(pollingBooth);
                }

                desig_list2.add(designation_list[i]);

            }
//            if (designation_list[i].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[i].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[i].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[4].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[5].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[6].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[7].toLowerCase().contains(text.toLowerCase())) {
//
//            }
//            if (designation_list[8].toLowerCase().contains(text.toLowerCase())){
//
//            }

        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(booth_list2,desig_list2);
    }



}
