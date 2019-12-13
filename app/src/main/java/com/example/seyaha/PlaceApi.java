package com.example.seyaha;

import com.google.gson.JsonObject;

import java.net.URL;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PlaceApi {

    @GET("maps/api/place/textsearch/json")
    Call<JsonObject> getPlace(@Query("query") String place,
                              @Query("location") String location,
                              @Query("key") String key);


    @GET("maps/api/place/details/json")
    Call<JsonObject> getDetails(@Query("place_id") String placeId,
                                @Query("fields") String fields,
                                @Query("key") String key);


}
