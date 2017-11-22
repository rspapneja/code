package com.example.fanaticaltechnology.retrofitdemohighmountains;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        API();

    }

    private void API() {

        Call<PostDataClass> call = apiInterface.getDestinationsList("1");
        call.enqueue(new Callback<PostDataClass>() {
            @Override
            public void onResponse(Call<PostDataClass> call, Response<PostDataClass> response) {

               Log.e("reponse","response"+response);
               String  message= response.body().getMessage().toString();
               Log.e("stat","stat"+response.body().getSuccess()+"mesage"+message);

               List<PostDataClass.DataBean> list1= response.body().getData();

               Log.e("resultdestination",list1.get(0).getEmail());

            }
            @Override
            public void onFailure(Call<PostDataClass> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
