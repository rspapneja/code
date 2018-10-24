package com.example.ajit.imageupload.apiBase;


import com.example.ajit.imageupload.model.SucessModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by PC-DESTOP on 9/25/2017.
 */

public interface ApiInterface {

    @Multipart
    @POST(ApiConstants.SIGNUP)
    Call<SucessModel> signup(@Part("attachment_name\"; filename=\"image.jpg\" ") RequestBody file, @Part("user_id") RequestBody user_id, @Part("token") RequestBody token, @Part("patient_id") RequestBody patient_id, @Part("doctor_id") RequestBody doctor_id
            , @Part("consultation_date") RequestBody consultation_date, @Part("start_time") RequestBody start_time, @Part("end_time") RequestBody end_time, @Part("message") RequestBody message, @Part("isPaid") RequestBody isPaid, @Part("amount") RequestBody amount, @Part("payment_method") RequestBody payment_method,
                             @Part("selected_communication_medium") RequestBody selected_communication_medium);



}


