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
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
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
import com.visuotech.hoshangabad_election.Model.District_officers;
import com.visuotech.hoshangabad_election.Model.PollingBooth;
import com.visuotech.hoshangabad_election.Model.Samitee_members;
import com.visuotech.hoshangabad_election.Model.Vidhanasabha_list;
import com.visuotech.hoshangabad_election.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;


public class Ad_district_officers extends RecyclerView.Adapter<Ad_district_officers.MyViewHolder> {
    ArrayList<District_officers> list;
    Context context;
    String samitee_name;


    public Ad_district_officers(Context context, ArrayList<District_officers> list) {
        this.list = list;
        this.context=context;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_district_officers,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int i) {


        holder.tv_name.setText(list.get(i).getOfficer_name());
        holder.tv_designation.setText(list.get(i).getOfficer_post());

        Spannable s = (Spannable) Html.fromHtml("<a href='tel:"+list.get(i).getOfficer_mobile()+"'>"+list.get(i).getOfficer_mobile()+"</a>");
        for (URLSpan u: s.getSpans(0, s.length(), URLSpan.class)) {
            s.setSpan(new UnderlineSpan() {
                public void updateDrawState(TextPaint tp) {
                    tp.setUnderlineText(false);
                }
            }, s.getSpanStart(u), s.getSpanEnd(u), 0);
        }
        holder.tv_mobile.setText(s);


        Spannable s2 = (Spannable) Html.fromHtml("<a href='tel:"+list.get(i).getOfficer_phone()+"'>"+list.get(i).getOfficer_phone()+"</a>");
        for (URLSpan u: s2.getSpans(0, s2.length(), URLSpan.class)) {
            s2.setSpan(new UnderlineSpan() {
                public void updateDrawState(TextPaint tp) {
                    tp.setUnderlineText(false);
                }
            }, s2.getSpanStart(u), s2.getSpanEnd(u), 0);
        }
        holder.tv_phone.setText(s2);

        holder.tv_phone.setMovementMethod(LinkMovementMethod.getInstance());
        holder.tv_mobile.setMovementMethod(LinkMovementMethod.getInstance());
        String htmlString="<u>"+list.get(i).getOfficer_email()+"</u>";
        holder.tv_email.setText(Html.fromHtml(htmlString));

        if((i % 2 != 0)){
            holder.lin_layout.setBackgroundColor(Color.parseColor("#efefef"));
        }else{
            holder.lin_layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (list.get(i).getOfficer_email().equals("")||list.get(i).getOfficer_email().equals("-")){
            holder.tv_email.setVisibility(View.GONE);
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
        holder.tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("POSITION>>>>", holder.tv_mobile.getText().toString());
                try{
                    Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + holder.tv_email.getText().toString()));
                    context.startActivity(intent);
                }catch(ActivityNotFoundException e){
                    //TODO smth
                }
            }

        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tv_name,tv_designation,tv_mobile,tv_email,tv_resp,tv_phone;
        LinearLayout lin_layout;
        CircleImageView iv_profile_image;
        LinearLayout lay_call,lay_message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_designation =  itemView.findViewById(R.id.tv_designation);
            tv_mobile =  itemView.findViewById(R.id.tv_mobile);
            tv_email =  itemView.findViewById(R.id.tv_email);
            lay_call =  itemView.findViewById(R.id.lay_call);
            tv_phone =  itemView.findViewById(R.id.tv_phone);
            lay_message =  itemView.findViewById(R.id.lay_message);
            lin_layout=itemView.findViewById(R.id.lin_layout);

        }
    }
    public void filterList(ArrayList<District_officers> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
