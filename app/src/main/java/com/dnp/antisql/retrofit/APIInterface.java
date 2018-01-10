package com.dnp.antisql.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface APIInterface {


    @GET("status.php")
    Call<StatusList> doGetStatusList();

    @GET("status.php?")
    Call<QueryList> doGetQueryList(@Query("query") int query);

    @GET("ip.php?")
    Call<List<IPList>> getJSONIP();
}
