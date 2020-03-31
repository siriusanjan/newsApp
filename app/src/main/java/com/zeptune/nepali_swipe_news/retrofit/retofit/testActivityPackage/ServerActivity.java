package com.zeptune.nepali_swipe_news.retrofit.retofit.testActivityPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.zeptune.nepali_swipe_news.R;
import com.zeptune.nepali_swipe_news.retrofit.retofit.ServiceFactory;
import com.zeptune.nepali_swipe_news.retrofit.retofit.TestDataModel.NewsDataArraylist;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginSocial();
                //Do something after 100ms
            }
        }, 100);

    }

    private void loginSocial() {

        Call<NewsDataArraylist> categoryCall = ServiceFactory.getNewsServices().getAllNews();

        categoryCall.enqueue(new Callback<NewsDataArraylist>() {

            @Override
            public void onResponse(Call<NewsDataArraylist> call, Response<NewsDataArraylist> response) {
                NewsDataArraylist dataModel = response.body();

                Log.d("mydata", "onResponse: " +dataModel.getData().get(2).getImageUrl());
                Log.d("mydata", "onResponse: " +dataModel.getData().get(2).getNewsStatus());
                Log.d("mydata", "onResponse: " +dataModel.getData().get(2).getNewsUrl());
                Log.d("mydata", "onResponse: " +dataModel.getData().get(2).getNewsTitle());

            }

            @Override
            public void onFailure(Call<NewsDataArraylist> call, Throwable t) {
                t.printStackTrace();
                Log.d("failingcause", "onFailure: "+t.getStackTrace().length);
                Log.d("failingcause", "onFailure: "+t.getCause());
                Log.d("failingcause", "onFailure: "+t.getMessage());

            }



        });
    }
}
