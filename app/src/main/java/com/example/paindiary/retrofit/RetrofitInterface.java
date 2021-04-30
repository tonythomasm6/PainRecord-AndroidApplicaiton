package com.example.paindiary.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("weather")
    Call<com.example.paindiary.retrofit.SearchResponse> customSearch(@Query("lat") double LAT,
                                                                        @Query("lon") double LON,
                                                                        @Query("appid") String APPID);

}
