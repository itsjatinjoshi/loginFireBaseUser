package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://www.metaweather.com/api/location/";

    public static Retrofit getRetrofitInstance(){

        if(retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
