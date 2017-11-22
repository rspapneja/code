package com.example.home.demoretrofit.ApiInterFace;

import com.example.home.demoretrofit.Model.DestinationResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by home on 11/9/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("get_destinations")
    Call<DestinationResponse> getDestination(@Field("userId") String userId, @Field("type") String type);
}
