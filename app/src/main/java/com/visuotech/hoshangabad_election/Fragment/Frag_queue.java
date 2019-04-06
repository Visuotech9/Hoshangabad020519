package com.visuotech.hoshangabad_election.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.visuotech.hoshangabad_election.Adapter.Ad_notifications;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.Notificationss;
import com.visuotech.hoshangabad_election.Model.Queue;
import com.visuotech.hoshangabad_election.R;
import com.visuotech.hoshangabad_election.SessionParam;
import com.visuotech.hoshangabad_election.retrofit.BaseRequest;
import com.visuotech.hoshangabad_election.retrofit.RequestReciever;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Frag_queue extends Fragment {
    Context context;
    Activity activity;
    SessionParam sessionParam;
    MarshMallowPermission marshMallowPermission;
    private BaseRequest baseRequest;
    ArrayList<Queue> queues;
    String booth_name,time,count,male_count,female_count;

    TextView tv_time,tv_name,tv_count,tv_female,tv_male;
     LinearLayout lin_spl_layout;
    Handler handler;
    Runnable refresh;
    public Frag_queue() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            booth_name = getArguments().getString("NAME");


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frag_location, container, false);

        tv_name=view.findViewById(R.id.tv_name);
        tv_count=view.findViewById(R.id.tv_count);
        tv_time=view.findViewById(R.id.tv_time);
        tv_male=view.findViewById(R.id.tv_male);
        lin_spl_layout=view.findViewById(R.id.lin_spl_layout);
        tv_female=view.findViewById(R.id.tv_female);


//----------------auto refress ---------------------------
         handler = new Handler();
         refresh = new Runnable() {
            public void run() {
                Apigetsam_mem_list();
                handler.postDelayed(refresh, 5000);

            }
        };
        handler.post(refresh);


        return  view;
    }

    private void Apigetsam_mem_list(){
        baseRequest = new BaseRequest();
        baseRequest.setBaseRequestListner(new RequestReciever() {
            @Override
            public void onSuccess(int requestCode, String Json, Object object) {
                try {
                    JSONObject jsonObject = new JSONObject(Json);
                    String message=jsonObject.optString("message");
                    if (message.equals("No Records Found")){
                        tv_count.setText(":- "+"No Records Found");
                        tv_time.setText(":- "+"No Records Found");
                        tv_name.setText(":- "+booth_name);
                        tv_female.setText(":- "+"No Records Found");
                        tv_male.setText(":- "+"No Records Found");
//                        Toast.makeText(getContext(),"No Records Found",Toast.LENGTH_SHORT).show();
                    }else{
                        JSONArray jsonArray=jsonObject.optJSONArray("user");
                        queues=baseRequest.getDataList(jsonArray,Queue.class);
                        time=queues.get(0).getCreation_date();
                        count=queues.get(0).getQueue_count();
                        male_count=queues.get(0).getMale_count();
                        female_count=queues.get(0).getFemale_count();

                        tv_count.setText(":- "+count+" "+"person"+"(approx)");
                        tv_time.setText(":- "+time);
                        tv_name.setText(":- "+booth_name);
                        tv_female.setText(":- "+female_count+" "+"Female"+"(approx)");
                        tv_male.setText(":- "+male_count+" "+"Male"+"(approx)");
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
        String remainingUrl2="/Election/Api2.php?apicall=queue_list"+"&polling_name="+booth_name;
        baseRequest.callAPIGETData(1, remainingUrl2);
    }



}
