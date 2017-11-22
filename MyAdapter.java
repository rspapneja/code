package com.example.home.demoretrofit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.home.demoretrofit.Model.DestinationResponse;
import com.example.home.demoretrofit.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Callback;

/**
 * Created by home on 11/9/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    FragmentTransaction transaction;
    Context context;
    View view1;

   ViewHolder viewHolder1;

    List<DestinationResponse.PayloadBean.DestinationsBean> list1;
    int Postion ;

    //New
    public MyAdapter(Context context1, List<DestinationResponse.PayloadBean.DestinationsBean> list1)
    {
        context = context1;
        this.list1=list1;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView dest_image;
        TextView title,total_tar;
        RelativeLayout des;
        public ViewHolder(View v) {
            super(v);


            dest_image=(ImageView) v.findViewById(R.id.dest_image);
            title=(TextView) v.findViewById(R.id.title);
            total_tar=(TextView) v.findViewById(R.id.total_tar);
            des=(RelativeLayout)v.findViewById(R.id.des);

        }
    }
    //
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.destination_list, parent, false);
        viewHolder1 = new ViewHolder(view1);
        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        holder.total_tar.setText("Tours("+list1.get(position).getTotal_pack());
        holder.total_tar.setText("acctivity("+list1.get(position).getTotal_activity());
    Glide.with(context).load(list1.get(position).getImage()).into(holder.dest_image);

/*
        if(list.get(position).get("bucket").equals("N"))
        {

            holder.addtobuket_im.setBackgroundResource(R.drawable.addtobuket);
        }
        else {
            holder.addtobuket_im.setBackgroundResource(R.drawable.my_bucket);


        }*/




    }
    @Override
    public int getItemCount() {
        return list1.size();
    }



}