package com.example.home.demoretrofit;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.home.demoretrofit.Adapter.MyAdapter;
import com.example.home.demoretrofit.ApiInterFace.ApiInterface;
import com.example.home.demoretrofit.ApiInterFace.Apiclient;
import com.example.home.demoretrofit.Model.DestinationResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    private ArrayList<DestinationResponse> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list=(RecyclerView)findViewById(R.id.list);


       list.setLayoutManager(new LinearLayoutManager(this));



        /**
         * Calling JSON
         */

        ApiInterface apiService =
                Apiclient.getClient().create(ApiInterface.class);

        Call<DestinationResponse> call = apiService.getDestination("1","");
        call.enqueue(new Callback<DestinationResponse>() {
            @Override
            public void onResponse(Call<DestinationResponse> call, Response<DestinationResponse> response) {
//                list.setAdapter(MyAdapter.class,);

                Log.e("response","response"+response);

                Log.e("status",response.body().getStatus());
//                Log.e("result",response.body().getPayload().toString());
//
//                Log.e("result12",response.body().getPayload().getDestinations().toString());
                List<DestinationResponse.PayloadBean.DestinationsBean> list1= response.body().getPayload().getDestinations();



                Log.e("resultdestination",list1.get(0).getDestination_id());


                list.setAdapter(new MyAdapter(MainActivity.this,list1));



            }

            @Override
            public void onFailure(Call<DestinationResponse> call, Throwable t) {

            }
        });
    }
}
