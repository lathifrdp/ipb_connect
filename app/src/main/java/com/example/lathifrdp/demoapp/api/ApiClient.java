package com.example.lathifrdp.demoapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String URL = "http://192.168.1.9:3501/";
    //public static final String URL = "http://182.23.70.28:3501/";
    //public static final String URL = "http://192.168.43.31:3501/";
    //public static final String URL = "http://172.17.66.42:3501/";

    public static Retrofit RETROFIT = null;

    public static Retrofit getClient(){
        if(RETROFIT==null){
//            OkHttpClient client = new OkHttpClient.Builder()
//                    .addInterceptor(new LoggingInterceptor())
//                    .build();
            RETROFIT = new Retrofit.Builder()
                    .baseUrl(URL)
                    //.client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }
}
