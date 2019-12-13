package com.example.seyaha;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HotelsFragment extends Fragment {

    private static final String TAG = "HotelsFragment";
    private static final String PlACE_API_KEY = "AIzaSyC_kd_yZ3OI0CMFoheM4XtlvF1CBqnYjLk";
    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String PHOTO_PATH_URL = "maps/api/place/photo";
    private static final String MAX_WIDTH = "1500";
    private static final String place = "hotel";

    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;

    private PlaceApi placeApi;

    private String location;
    Context context;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // get the context
        context = this.getActivity();

        // inflate the fragment with it's layout.
        View mView = inflater.inflate(R.layout.fragment_hotels, container, false);

        // recycler view config
        recyclerView = mView.findViewById(R.id.hotel_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // retrofit config
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        placeApi = retrofit.create(PlaceApi.class);

        // receive lat and lng from Places Activity
        location = PlacesActivity.getLocation();

        // call Place Api
        getHotels();

        return mView;
    }


    private void getHotels() {

        // implement Place Api call
        Call<JsonObject> call = placeApi.getPlace(place, location, PlACE_API_KEY);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Code: " + response.code());
                    return;
                }

                JsonObject root = response.body();

                List<Hotel> hotels;

                assert root != null;
                hotels = jsonParse(root);

                if (hotels != null && !hotels.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    hotelAdapter = new HotelAdapter(hotels, context);
                    recyclerView.setAdapter(hotelAdapter);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                recyclerView.setVisibility(View.INVISIBLE);
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });
    }


    private List<Hotel> jsonParse(JsonObject root) {

        List<Hotel> hotelData = new ArrayList<>();

        String mThumbnail = "Unknown";
        String mTitle = "Unknown";
        String mHours = "Unknown";
        String mRating = "0.0";
        String placeId = "Not Found";
        String photoReference;
        double lat = 0.0, lng = 0.0;
        boolean open;

        JsonArray results, photos;
        JsonObject result, openingHours, photo, geometry, location;


        results = root.getAsJsonArray("results");
        for (int i = 0; i < results.size(); i++) {

            JsonElement result_element = results.get(i);
            result = result_element.getAsJsonObject();


            if (result.has("name") && !result.get("name").isJsonNull()) {
                JsonElement element_title = result.get("name");
                mTitle = element_title.getAsString();
            }

            if (result.has("opening_hours") && !result.get("opening_hours").isJsonNull()) {
                openingHours = result.getAsJsonObject("opening_hours");
                if (openingHours.has("open_now") && !openingHours.get("open_now").isJsonNull()) {
                    JsonElement element_hours = openingHours.get("open_now");
                    open = element_hours.getAsBoolean();
                    if (open) {
                        mHours = "open now";
                    } else {
                        mHours = "close";
                    }
                }
            }

            if (result.has("rating") && !result.get("rating").isJsonNull()) {
                JsonElement element_rating = result.get("rating");
                Double rate = element_rating.getAsDouble();
                mRating = String.valueOf(rate);
            }

            if (result.has("place_id") && !result.get("place_id").isJsonNull()) {
                JsonElement element_title = result.get("place_id");
                placeId = element_title.getAsString();

            }

            if (result.has("photos") && !result.get("photos").isJsonNull()) {
                photos = result.getAsJsonArray("photos");
                JsonElement photo_element = photos.get(0);
                photo = photo_element.getAsJsonObject();

                if (photo.has("photo_reference") && !photo.get("photo_reference").isJsonNull()) {
                    JsonElement element_reference = photo.get("photo_reference");
                    photoReference = element_reference.getAsString();
                    mThumbnail = BASE_URL + PHOTO_PATH_URL + "?maxwidth=" + MAX_WIDTH + "&photoreference=" + photoReference + "&key=" + PlACE_API_KEY;


                }
            }

            if (result.has("geometry") && !result.get("geometry").isJsonNull()) {
                geometry = result.getAsJsonObject("geometry");
                if (geometry.has("location") && !geometry.get("location").isJsonNull()) {

                    location = geometry.getAsJsonObject("location");
                    JsonElement element_lat = location.get("lat");
                    JsonElement element_lng = location.get("lng");

                    lat = element_lat.getAsDouble();
                    lng = element_lng.getAsDouble();

                }
            }

            hotelData.add(new Hotel(mTitle, mHours, mRating, placeId, mThumbnail, lat, lng));
        }

        return hotelData;
    }

}
