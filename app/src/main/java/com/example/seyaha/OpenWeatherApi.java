package com.example.seyaha;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("data/2.5/weather")
    Call<JsonObject> getTemp(@Query("lat") Double lat, @Query("lon") Double lon, @Query("APPID") String key);
}