package com.visuotech.hoshangabad_election.Adapter;



import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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
import com.visuotech.hoshangabad_election.Activities.Election.Act_district_officers;
import com.visuotech.hoshangabad_election.Model.District_officers;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.Model.Responsibility;
import com.visuotech.hoshangabad_election.Model.Samitee_members;
import com.visuotech.hoshangabad_election.Model.Vidhanasabha_list;
import com.visuotech.hoshangabad_election.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_responsibility extends RecyclerView.Adapter<Ad_responsibility.MyViewHolder> {
    ArrayList<Responsibility> list;
    Context context;
    String samitee_name;


    public Ad_responsibility(Context context, ArrayList<Responsibility> list) {
        this.list = list;
        this.context=context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_responsibility,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {

        holder.tv_resp.setText(list.get(i).getNodal_responsibility());
        holder.tv_resp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, Act_district_officers.class);
                intent.putExtra("key",holder.tv_resp.getText().toString());
                intent.putExtra("NAME",holder.tv_resp.getText().toString());
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_resp;
        LinearLayout lin_layout;
        CircleImageView iv_profile_image;
        LinearLayout lay_call,lay_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_resp =  itemView.findViewById(R.id.tv_resp);
            lay_call =  itemView.findViewById(R.id.lay_call);
            lay_message =  itemView.findViewById(R.id.lay_message);
            lin_layout=itemView.findViewById(R.id.lin_layout);






        }
    }
    public void filterList(ArrayList<Responsibility> list) {
        this.list = list;
        notifyDataSetChanged();
    }}
