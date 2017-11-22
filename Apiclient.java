package com.example.home.demoretrofit.ApiInterFace;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 11/9/2017.
 */

public class Apiclient {

    public static final String BASE_URL = "http://cluecorporations.com/highmountainsindia/api/v1/request/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
        }
        return retrofit;
    }
}
