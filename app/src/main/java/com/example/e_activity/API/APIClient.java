package com.example.e_activity.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    //    10.0.2.2
//    172.17.100.2z

    public static final String BASE_URL = "http://192.168.1.10/rest-server-activity/";//http://10.0.2.2/rest-server-activity/
    private static Retrofit retrofit;
    public static Retrofit getClient() {
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
