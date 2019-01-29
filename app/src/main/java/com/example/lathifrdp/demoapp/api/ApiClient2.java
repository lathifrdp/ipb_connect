package com.example.lathifrdp.demoapp.api;

import com.example.lathifrdp.demoapp.helper.BaseModel;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient2 {
    public static final String URL = "https://api.ipb.ac.id/v1/";
    //public static final String URL = "http://182.23.70.28:3501/";
    //public static final String URL = "http://192.168.43.31:3501/";
    //public static final String URL = "http://172.17.66.42:3501/";
    //public static final String URL = "http://api.ipbconnect.cs.ipb.ac.id/";
    //public static final String URL = new BaseModel().getUrl();

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
