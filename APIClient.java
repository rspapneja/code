package com.example.fanaticaltechnology.retrofitdemohighmountains;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by FanaticalTechnology on 11/22/2017.
 */
class APIClient {
private static Retrofit retrofit = null;

static Retrofit getClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

         retrofit = new Retrofit.Builder()
        .baseUrl("http://sunfocussolutions.com/fooddeliveryservice/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();

        return retrofit;
        }
 }

