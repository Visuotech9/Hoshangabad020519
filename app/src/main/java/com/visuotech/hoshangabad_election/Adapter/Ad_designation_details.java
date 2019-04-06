package com.visuotech.hoshangabad_election.Adapter;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.visuotech.hoshangabad_election.MarshMallowPermission;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_designation_details extends RecyclerView.Adapter<Ad_designation_details.MyViewHolder> {
    ArrayList<PollingBooth> list;
    List<String> designation_list;
    Context context;
    Activity activity;
//    String[] designation_list;
    MarshMallowPermission marshMallowPermission;


    public Ad_designation_details(Context context, ArrayList<PollingBooth> list, List<String> designation_list) {
        this.list = list;
        this.context = context;
        this.activity = activity;
        this.designation_list = designation_list;
        marshMallowPermission = new MarshMallowPermission(context);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_designation, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {


        holder.tv_designation.setText(designation_list.get(i));
        holder.tv_name.setHint(designation_list.get(i)+" "+"Name");

        if (i%2!=0){
            holder.lin_layout.setBackgroundColor(Color.parseColor("#efefef"));
        }

        if (i == 0) {
            holder.tv_name.setText(list.get(0).getElePZero());
            holder.tv_mobile.setText(list.get(0).getElePZeroMobile());
//            holder.tv_mobile.setText(Html.fromHtml("<a href='tel:"+list.get(0).getElePZeroMobile())+"'>"+list.get(0).getElePZeroMobile()+"</a>"));
//            holder.tv_mobile.setMovementMethod(LinkMovementMethod.getInstance());

        }
        if (i == 1) {
            holder.tv_name.setText(list.get(0).getElePOne());
            holder.tv_mobile.setText(list.get(0).getElePOneMobile());

        }
        if (i == 2) {
            holder.tv_name.setText(list.get(0).getEleBloName());
            holder.tv_mobile.setText(list.get(0).getEleBloMobile());

        }
        if (i == 3) {
            holder.tv_name.setText(list.get(0).getEleGrs());
            holder.tv_mobile.setText(list.get(0).getEleGrsMobile());

        }
        if (i == 4) {
            holder.tv_name.setText(list.get(0).getEleSachiv());
            holder.tv_mobile.setText(list.get(0).getEleSachivMobile());
        }
        if (i == 5) {
            holder.tv_name.setText(list.get(0).getEleNearestThana());
            holder.tv_mobile.setText(list.get(0).getEleThanaMobile());
        }
        if (i == 6) {
            holder.tv_name.setText(list.get(0).getEleSectorOfficer());
            holder.tv_mobile.setText(list.get(0).getEleSectorMobile());
        }
        if (i == 7) {
            holder.tv_name.setText(list.get(0).getEleLocal1Name());
            holder.tv_mobile.setText(list.get(0).getEleLocal1Mobile());
        }
        if (i == 8) {
            holder.tv_name.setText(list.get(0).getEleLocal2Name());
            holder.tv_mobile.setText(list.get(0).getEleLocal2Mobile());
        }

        holder.lay_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POSITION>>>>", holder.tv_mobile.getText().toString());
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + holder.tv_mobile.getText().toString()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }

        });
        holder.lay_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POSITION>>>>", holder.tv_mobile.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("smsto:" + Uri.encode(holder.tv_mobile.getText().toString())));
                context.startActivity(intent);
            }

        });




    }
    public void composeSmsMessage(String message, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"+phoneNumber)); // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return designation_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_name,tv_designation,tv_mobile;
        LinearLayout lin_layout;
        LinearLayout lay_message,lay_call;
        CircleImageView iv_profile_image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_designation =  itemView.findViewById(R.id.tv_designation);
            tv_mobile =  itemView.findViewById(R.id.tv_mobile);
            lay_message =  itemView.findViewById(R.id.lay_message);
            lay_call =  itemView.findViewById(R.id.lay_call);
            lin_layout=itemView.findViewById(R.id.lin_layout);

//            holder.iv_call.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
////                Intent intent = new Intent(Intent.ACTION_CALL);
////                intent.setData(Uri.parse("tel:" + "9669261405"));
////                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
////                    // TODO: Consider calling
////                    //    ActivityCompat#requestPermissions
////                    // here to request the missing permissions, and then overriding
////                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                    //                                          int[] grantResults)
////                    // to handle the case where the user grants the permission. See the documentation
////                    // for ActivityCompat#requestPermissions for more details.
////                    return;
////                }
////                context.startActivity(intent);
//
//                }
//            });


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int post=getAdapterPosition();
//                    Log.d("POSITION>>>>", String.valueOf(post));
////                    Toast.makeText(context,String.valueOf(post),Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }
    public void filterList(ArrayList<PollingBooth> list, ArrayList<String> desig_list2) {
        this.list = list;
        this.designation_list = desig_list2;
        notifyDataSetChanged();
    }


}
