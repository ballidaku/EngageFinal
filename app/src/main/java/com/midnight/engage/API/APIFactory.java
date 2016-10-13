package com.midnight.engage.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adi on 6/3/16.
 */
public class APIFactory {


    public static API createAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                //.baseUrl("http://midnight.works/appstore/engage_android/")
                .baseUrl("http://spartedev.com/new/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(API.class);


    }
}