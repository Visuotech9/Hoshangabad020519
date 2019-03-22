package com.visuotech.hoshangabad.Activities.Election.Seoni_malwa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.visuotech.hoshangabad.Adapter.Ad_Vidhansabha;
import com.visuotech.hoshangabad.MarshMallowPermission;
import com.visuotech.hoshangabad.Model.Vidhanasabha_list;
import com.visuotech.hoshangabad.R;
import com.visuotech.hoshangabad.SessionParam;
import com.visuotech.hoshangabad.retrofit.BaseRequest;
import com.visuotech.hoshangabad.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Act_vidhansabha_details_list extends AppCompatActivity {
    LinearLayout container;
    LinearLayoutManager linearLayoutManager;
    RecyclerView rv;
    ArrayList<Vidhanasabha_list> vidhanasabha_list1;
    ArrayList<String>vidhanasabha_list=new ArrayList<>();
    Ad_Vidhansabha adapter;
    String AC;
    String booth_name;
    EditText inputSearch;
    String designation,samitee_name;

    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_election);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor((Color.parseColor("#FFFFFF")));
        getSupportActionBar().setTitle("Member list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context=this;

        container = (LinearLayout)findViewById(R.id.container);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_act_vidhansabha_details, null);

        rv = (RecyclerView) rowView.findViewById(R.id.rv_list);
        inputSearch = (EditText) rowView.findViewById(R.id.inputSearch);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());

        container.addView(rowView, container.getChildCount());


        Intent intent=getIntent();
        designation=intent.getStringExtra("designation");
        AC=intent.getStringExtra("CITY");



//        Apigetboothlist();

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
                filter(editable.toString());
            }
        });
        ApigetVidhan_detail_list();

    }

    private void ApigetVidhan_detail_list(){
        baseRequest = new BaseRequest(context);
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    JSONArray jsonArray=jsonObject.optJSONArray("user");

                    vidhanasabha_list1=baseRequest.getDataList(jsonArray,Vidhanasabha_list.class);

//                    for (int i=0;i<sam_mem_list1.size();i++){
//                        booth_list.add(sam_mem_list1.get(i).getEleBoothName());
////                       department_id.add(department_list1.get(i).getDepartment_id());
//                    }

                    adapter=new Ad_Vidhansabha(context,vidhanasabha_list1);
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
        String remainingUrl2="/Election/Api2.php?apicall=vidhansabha_list"+"&ac="+AC+"&designation="+designation;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        String[] designation_list2;
        ArrayList<Vidhanasabha_list>vidhanasabha_list2=new ArrayList<>();

        //looping through existing elements
        for (int i=0;i<vidhanasabha_list1.size();i++) {
            if (vidhanasabha_list1.get(i).getCom_name().toLowerCase().contains(text.toLowerCase())) {
                Vidhanasabha_list vd = new Vidhanasabha_list();
                vd.setCom_name(vidhanasabha_list1.get(i).getCom_name());
                vd.setCom_mobile(vidhanasabha_list1.get(i).getCom_mobile());
                vd.setCom_responsibility(vidhanasabha_list1.get(i).getCom_responsibility());
                vd.setCom_post(vidhanasabha_list1.get(i).getCom_post());
                vd.setCom_email(vidhanasabha_list1.get(i).getCom_email());
                vidhanasabha_list2.add(vd);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(vidhanasabha_list2);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent i = new Intent(Act_vidhansabha_details_list.this, Act_vidhansabha.class);
//        startActivity(i);
        finish();
        return true;

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent i = new Intent(Act_vidhansabha_details_list.this, Act_vidhansabha.class);
//        startActivity(i);
        finish();
    }


}
