package com.visuotech.hoshangabad_election.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.visuotech.hoshangabad_election.Activities.Election.Seoni_malwa.Act_add_details;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Queue;
import com.visuotech.hoshangabad_election.NetworkConnection;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Frag_queue extends Fragment {
    Frag_queue context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    ArrayList<Queue> queues=new ArrayList<>();
    String booth_name,time,count,male_count,female_count,keyy,AC,station_id,election_id;
    float male,female;
    int male1=2,female1=5;

    TextView tv_time,tv_name,tv_count,tv_female,tv_male;
     LinearLayout lin_spl_layout,lay_total;
    Handler handler;
    Runnable refresh;
    Button btn_login;
    SwipeRefreshLayout mSwipeRefreshLayout;
    PieChart pieChart;

    public Frag_queue() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            booth_name = getArguments().getString("NAME");
            station_id = getArguments().getString("STATION_ID");
            AC = getArguments().getString("CITY");
            election_id = getArguments().getString("ELECTION_ID");


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_location, container, false);

        sessionParam = new SessionParam(getContext());
        context=this;

        tv_name=view.findViewById(R.id.tv_name);
        tv_count=view.findViewById(R.id.tv_count);
        btn_login=view.findViewById(R.id.btn_login);
        tv_time=view.findViewById(R.id.tv_time);
//        tv_male=view.findViewById(R.id.tv_male);
        lin_spl_layout=view.findViewById(R.id.lin_spl_layout);
        lay_total=view.findViewById(R.id.lay_total);
        pieChart=view.findViewById(R.id.piechart_1);
        mSwipeRefreshLayout=view.findViewById(R.id.activity_main_swipe_refresh_layout);
        keyy=booth_name+"2";

//----------------auto refress ---------------------------
        if (NetworkConnection.checkNetworkStatus(getContext())==true){
            Apigetsam_mem_list(keyy);
        }else{
//            Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
            String Json;
            Json = sessionParam.getJson(keyy,getContext());
            try {
                if (Json!=null){
                    JSONObject jsonObject = new JSONObject(Json);
                    String message=jsonObject.optString("message");
                    if (message.equals("No Records Found")){
                        tv_count.setText("No Records Found");
                        tv_time.setText("No Records Found");
                        lay_total.setVisibility(View.GONE);
                        tv_name.setText(booth_name);
//                        tv_female.setText(":- "+"No Records Found");
//                        tv_male.setText(":- "+"No Records Found");
//                        Toast.makeText(getContext(),"No Records Found",Toast.LENGTH_SHORT).show();
                    }else{
                        JSONArray jsonArray=jsonObject.optJSONArray("user");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Queue samitee_members = new Queue();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String queue_count = jsonObject1.optString("queue_count");
                            String creation_date = jsonObject1.optString("creation_date");
                            String male_count = jsonObject1.optString("male_count");
                            String female_count = jsonObject1.optString("female_count");

                            samitee_members.setQueue_count(queue_count);
                            samitee_members.setCreation_date(creation_date);
                            samitee_members.setMale_count(male_count);
                            samitee_members.setFemale_count(female_count);


                            queues.add(samitee_members);

                        }

                        time=queues.get(0).getCreation_date();
                        count=queues.get(0).getQueue_count();
                        male_count=queues.get(0).getMale_count();
                        female_count=queues.get(0).getFemale_count();

                        tv_count.setText(count+" "+"persons"+" (approx)");
                        tv_time.setText(time);
                        tv_name.setText(booth_name);
//                        tv_female.setText(":- "+female_count+" "+"Female"+"(approx)");
//                        tv_male.setText(":- "+male_count+" "+"Male"+"(approx)");

                        male= Float.parseFloat(male_count);
                        female= Float.parseFloat(female_count);

//                        male1= Integer.parseInt((male_count));
//                        female1= Integer.parseInt((female_count));
                        setPieChart(male,female);

                    }
                }else{
//                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

         handler = new Handler();
         refresh = new Runnable() {
            public void run() {

                if (NetworkConnection.checkNetworkStatus(getContext())==true){
                    Apigetsam_mem_list(keyy);
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                handler.postDelayed(refresh, 30000);

            }
        };
        handler.post(refresh);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Act_add_details.class);
                intent.putExtra("NAME_booth",booth_name);
                intent.putExtra("ELECTION_ID",election_id);
                intent.putExtra("STATION_ID",station_id);
                intent.putExtra("AC",AC);
                startActivity(intent);
                getActivity().finish();
//                Toast.makeText(getContext(),"CLICK",Toast.LENGTH_SHORT).show();


            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkConnection.checkNetworkStatus(getContext())==true){
                    Apigetsam_mem_list(keyy);
                    mSwipeRefreshLayout.setRefreshing(false);
                }else{
//                    Snackbar.make(lin_spl_layout, "No internet connection", Snackbar.LENGTH_LONG).show();
                    mSwipeRefreshLayout.setRefreshing(false);
                }


            }
        });






        return  view;
    }

    private void Apigetsam_mem_list(final String keyyy){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    sessionParam.saveJson(Json.toString(),keyyy,getContext());
                    String message=jsonObject.optString("message");
                    if (message.equals("No Records Found")){
                        tv_count.setText("No Records Found");
                        tv_time.setText("No Records Found");
                        lay_total.setVisibility(View.GONE);
                        tv_name.setText(booth_name);
//                        tv_female.setText(":- "+"No Records Found");
//                        tv_male.setText(":- "+"No Records Found");
//                        Toast.makeText(getContext(),"No Records Found",Toast.LENGTH_SHORT).show();
                    }else{
                        JSONArray jsonArray=jsonObject.optJSONArray("user");
                        queues=baseRequest.getDataList(jsonArray,Queue.class);
                        time=queues.get(0).getCreation_date();
                        count=queues.get(0).getQueue_count();
                        male_count=queues.get(0).getMale_count();
                        female_count=queues.get(0).getFemale_count();

                        tv_count.setText(count+" "+"persons"+" (approx)");
                        tv_time.setText(time);
                        tv_name.setText(booth_name);
//                        tv_female.setText(":- "+female_count+" "+"Female"+"(approx)");
//                        tv_male.setText(":- "+male_count+" "+"Male"+"(approx)");

                        male= Float.parseFloat(male_count);
                        female= Float.parseFloat(female_count);

//                        male1= Integer.parseInt((male_count));
//                        female1= Integer.parseInt((female_count));
                        setPieChart(male,female);

                    }
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
        String remainingUrl2="/Election/Api2.php?apicall=queue_list&election_id="+election_id;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }

    public void setPieChart(float male, float female) {
        this.pieChart = pieChart;
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(male,"Male"));
        yValues.add(new PieEntry(female,"Female"));


        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
//        dataSet.setValueFormatter(new MyValueFormatter());
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.YELLOW);

        pieChart.setData(pieData);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setYOffset(0f);
        //PieChart Ends Here
    }



}
