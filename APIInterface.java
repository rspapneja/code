package com.example.fanaticaltechnology.retrofitdemohighmountains;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by FanaticalTechnology on 11/22/2017.
 */
public interface APIInterface {
    @FormUrlEncoded
    @POST("getnearbyrestaurants")
    Call<PostDataClass> getDestinationsList(@Field("user_id") String user_id);
}
