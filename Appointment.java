package com.example.shivam.patientapp.bookAnAppointment;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shivam.patientapp.common.Constant;
import com.example.shivam.patientapp.ListingBean;
import com.example.shivam.patientapp.preferences.SharedPreferenceManager;
import com.example.shivam.patientapp.R;
import com.example.shivam.patientapp.webclass.CommonClickListener;
import com.example.shivam.patientapp.doctor_schudle.L;
import com.example.shivam.patientapp.doctor_schudle.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CODELEE 1 on 4/5/2016.
 */
@SuppressWarnings("all")
public class Appointment extends AppCompatActivity implements View.OnClickListener, CommonClickListener {

    VolleySingleton mVolleySingleton;
    @Bind(R.id.listView)
    ListView mListView;


    VolleySingleton volleySingleton;
    private int cancelled_value;
    private static final String TAG = Appointment.class.getSimpleName();
    private String mAppointmentType = "";
    private List<ListingBean> mAppointmentBeanList = new ArrayList<>();
    private AppointmentAdapter mAppointmentAdapter;
    private boolean loadMore;
    private int mPageNumber=1;
    private String left_seconds;
    private SharedPreferenceManager sharedPreferenceManager;
    private Context context = this;
    private ImageView search_item;
    private EditText search;
    private Button name, specality;
    private String type = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_layout);

        sharedPreferenceManager = new SharedPreferenceManager(context);

        ButterKnife.bind(this);
        volleySingleton = VolleySingleton.getInstance();

        getMyAppointments();

        mAppointmentAdapter = new AppointmentAdapter(context, mAppointmentBeanList);
        Log.e("list is", "" + mAppointmentBeanList);

        mListView.setAdapter(mAppointmentAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int lLastVisiblePosition = mListView.getLastVisiblePosition();
                if (((totalItemCount - 1) == lLastVisiblePosition) && loadMore) {
                    getMyAppointments();

                }
            }
        });
    }
    @Override
    public void onCommonClick(View view, int position) {

    }
    @Override
    public void onClick(View v) {

    }
    public void getMyAppointments() {
        L.showProgresssBar(context);
        StringRequest myReq = new StringRequest(Request.Method.POST,
                Constant.WEB_URL + Constant.DOCTOR_LISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        L.hideProgressBar(context);

                        Log.e("reponse","R"+response);
                        try {
                            loadMore=false;
                                 JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean("success") && jsonObject.getString("status_code").equals("200")) {


                                    if (jsonObject.has("result")) {


                                        loadMore=true;
                                        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("data");

                                        for (int i = 0; i < jsonArray.length(); i++) {

                                            JSONObject resultParsing = jsonArray.getJSONObject(i);
                                            ListingBean listingBean = new ListingBean();
                                            listingBean.setDoctor_name(resultParsing.getString("doctor_name"));
                                            sharedPreferenceManager.setValues(SharedPreferenceManager.DOCTOR_NAME, resultParsing.getString("doctor_name"));
                                            listingBean.setAppointment_duration(resultParsing.getString("appointment_duration"));
                                            listingBean.setAppointment_cost(resultParsing.getString("appointment_cost"));
                                            listingBean.setRating(resultParsing.getString("rating"));
                                            listingBean.setUser_id(resultParsing.getString("user_id"));

                                            listingBean.setProfile_image(resultParsing.getString("profile_image"));
                                            listingBean.setSpecialities(resultParsing.getString("specialities"));
                                            mAppointmentBeanList.add(listingBean);
                                        }
                                        if (mAppointmentBeanList != null)
                                            mAppointmentAdapter.notifyDataSetChanged();
                                    }
                                    else
                                    {
                                        loadMore=false;
                                        mPageNumber=0;
                                    }
                                }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mAppointmentAdapter.notifyDataSetChanged();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        L.showToast(getApplicationContext(), "error");
                        L.hideProgressBar(context);

                        NetworkResponse response = volleyError.networkResponse;
                        if (response != null && response.data != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                if (!jsonObject.getBoolean("success") && (jsonObject.getInt("status_code") == 422)) {

                                } else if (jsonObject.getBoolean("success") && (jsonObject.getInt("status_code") == 422)) {
                                    JSONObject obj = jsonObject.getJSONObject("message");
                                    L.showToast(getApplicationContext(), jsonObject.getString("message"));
                                }
                                   if (!jsonObject.getBoolean("success") && (jsonObject.getInt("status_code") == 401)) {

                                    L.showToast(getApplicationContext(), jsonObject.getString("message"));
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id", sharedPreferenceManager.getValue(SharedPreferenceManager.USER_ID));
                params.put("token", sharedPreferenceManager.getValue(SharedPreferenceManager.TOKEN));
                if (loadMore) {
                    mPageNumber++;
                    params.put("page", mPageNumber + "");
                }
                Log.e("params are", "" + params);
                return params;
            }
        };
        //add request to volley queue
        volleySingleton.addToRequestQueue(myReq, TAG);
    }
}

