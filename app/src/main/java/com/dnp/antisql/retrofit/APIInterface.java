package com.dnp.antisql.retrofit;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;


public interface APIInterface {


    @GET("status.php")
    Call<StatusList> doGetStatusList();

    @GET("status.php?")
    Call<QueryList> doGetQueryList(@Query("query") int query);

    @GET("ip.php?")
    Call<List<IPList>> getJSONIP();

    @Multipart
    @POST("aksi.php")
    Call<IPRespond> aksi(@Part("id") RequestBody id);

}
