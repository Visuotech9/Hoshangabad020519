package com.visuotech.hoshangabad.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.visuotech.hoshangabad.Adapter.Ad_designation_details;
import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.Model.PollingBooth;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;
import com.visuotech.hoshangabad.retrofit.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Frag_list extends Fragment {
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ArrayList<PollingBooth>booth_list1;
    ArrayList<String>booth_list=new ArrayList<>();
    Ad_designation_details adapter;
    String AC;
    String booth_name;
    EditText inputSearch;
    Activity activity;

    Context context;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    String[] designation_list = { "P0","P1","BLO","GRS","SACHIV","THANA","Sector Officer","Local Contact Person-1","Local Contact Person-2"};

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
        inputSearch = (EditText) view.findViewById(R.id.inputSearch);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
//        Utility.hideKeyBoard(activity);
        Apigetboothlist();


        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
//                filter(editable.toString());
            }
        });

        return view;
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

                    adapter=new Ad_designation_details(getContext(),booth_list1,designation_list);
                    rv.setAdapter(adapter);

//                    ArrayAdapter adapter_booth = new ArrayAdapter(context,android.R.layout.simple_spinner_item,booth_list);
//                    adapter_booth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    spinner_station.setAdapter(adapter_booth);
//



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

    private void filter(String text) {
        //new array list that will hold the filtered data
        String[] designation_list2;
        ArrayList<PollingBooth>booth_list2=new ArrayList<>();

        //looping through existing elements
        if (booth_list1.get(0).getElePZero().toLowerCase().contains(text.toLowerCase())){
            PollingBooth pollingBooth=new PollingBooth();
            pollingBooth.setElePZero(booth_list1.get(0).getElePZero());
            pollingBooth.setElePZeroMobile(booth_list1.get(0).getElePZeroMobile());
            booth_list2.add(pollingBooth);
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(booth_list2);
    }



}
