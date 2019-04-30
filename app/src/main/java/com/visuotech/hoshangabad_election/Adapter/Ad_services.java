package com.visuotech.hoshangabad_election.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.visuotech.hoshangabad_election.Activities.MapActivity;
import com.visuotech.hoshangabad_election.Model.Deaprtments_members;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.Model.Samitee_members;
import com.visuotech.hoshangabad_election.Model.ServiceList;
import com.visuotech.hoshangabad_election.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_services extends RecyclerView.Adapter<Ad_services.MyViewHolder> {
    ArrayList<ServiceList> list;
    Context context;
    String dept_name;


    public Ad_services(Context context, ArrayList<ServiceList> list) {
        this.list = list;
        this.context=context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_services,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {


        holder.tv_name.setText(list.get(i).getOther_services_name());
        holder.tv_mobile.setText(list.get(i).getOther_services_mobile());
        String htmlString="<u> View on map </u>";
        holder.tv_address.setText(Html.fromHtml(htmlString));
        holder.tv_lat.setText(list.get(i).getOther_services_lat());
        holder.tv_long.setText(list.get(i).getOther_services_long());

        if((i % 2 != 0)){
            holder.lin_layout.setBackgroundColor(Color.parseColor("#efefef"));
        }else{
            holder.lin_layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        holder.tv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.tv_lat.getText().toString().equals("") && holder.tv_long.getText().toString().equals("")){
//                    Toast.makeText(context,"Location is not available for this service",Toast.LENGTH_SHORT).show();
                    Snackbar.make(holder.lin_layout, "Location is not available for this service", Snackbar.LENGTH_LONG).show();
                }
                if (holder.tv_lat.getText().toString().equals("-") && holder.tv_long.getText().toString().equals("-")){
//                    Toast.makeText(context,"Location is not available for this service",Toast.LENGTH_SHORT).show();
                    Snackbar.make(holder.lin_layout, "Location is not available for this service", Snackbar.LENGTH_LONG).show();
                } else{
                    Intent intent=new Intent(context, MapActivity.class);
                    intent.putExtra("LAT",holder.tv_lat.getText().toString());
                    intent.putExtra("LOG",holder.tv_long.getText().toString());
                    intent.putExtra("NAME",holder.tv_name.getText().toString());
                    context.startActivity(intent);
                }

            }
        });
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_name,tv_mobile,tv_type,tv_address,tv_lat,tv_long;
        LinearLayout lin_layout;
        CircleImageView iv_profile_image;
        LinearLayout lay_message,lay_call;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_address =  itemView.findViewById(R.id.tv_address);
            tv_mobile =  itemView.findViewById(R.id.tv_mobile);
            lay_message =  itemView.findViewById(R.id.lay_message);
            lay_call =  itemView.findViewById(R.id.lay_call);
            tv_lat =  itemView.findViewById(R.id.tv_lat);
            tv_long =  itemView.findViewById(R.id.tv_long);
            lin_layout=itemView.findViewById(R.id.lin_layout);






        }
    }
    public void filterList(ArrayList<ServiceList> polling_list2) {
        this.list = polling_list2;
        notifyDataSetChanged();
    }}
