package com.visuotech.hoshangabad_election.Adapter;



import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.visuotech.hoshangabad_election.Activities.Election.Act_voter_info;
import com.visuotech.hoshangabad_election.Activities.Election.Act_voter_web;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.Model.Samitee_members;
import com.visuotech.hoshangabad_election.Model.Vidhanasabha_list;
import com.visuotech.hoshangabad_election.Model.Voter;
import com.visuotech.hoshangabad_election.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_voters_urls extends RecyclerView.Adapter<Ad_voters_urls.MyViewHolder> {
    ArrayList<Voter> list;
    Context context;
    String samitee_name;


    public Ad_voters_urls(Context context, ArrayList<Voter> list) {
        this.list = list;
        this.context=context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_voters,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {



        holder.tv_title.setText(list.get(i).getVoterName());
        holder.tv_link2.setText(list.get(i).getVoterUrl());

        String htmlString="<u>"+list.get(i).getVoterUrl()+"</u>";
        holder.tv_link.setText(Html.fromHtml(htmlString));
//        holder.tv_email.setText(list.get(i).getCom_email());
//        if (i%2!=0){
//            holder.lin_layout.setBackgroundColor(Color.parseColor("#efefef"));
//        }
        if((i % 2 != 0)){
            holder.lin_layout.setBackgroundColor(Color.parseColor("#efefef"));
        }else{
            holder.lin_layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.tv_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(context, Act_voter_web.class);
              intent.putExtra("LINK",holder.tv_link2.getText().toString());
              intent.putExtra("NAME",holder.tv_title.getText().toString());
              context.startActivity(intent);
            }

        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_title,tv_link,tv_link2;
        LinearLayout lin_layout;
        CircleImageView iv_profile_image;
        LinearLayout lay_call,lay_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_link2=itemView.findViewById(R.id.tv_link2);
            tv_link =  itemView.findViewById(R.id.tv_link);
            lay_call =  itemView.findViewById(R.id.lay_call);
            lay_message =  itemView.findViewById(R.id.lay_message);
            lin_layout=itemView.findViewById(R.id.lin_layout);






        }
    }
    public void filterList(ArrayList<Voter> list) {
        this.list = list;
        notifyDataSetChanged();
    }}
