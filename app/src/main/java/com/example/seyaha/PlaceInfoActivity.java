package com.example.seyaha;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PlaceInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "PLACE INFO ACTIVITY";
    private static final String PlACE_API_KEY = "AIzaSyC_kd_yZ3OI0CMFoheM4XtlvF1CBqnYjLk";
    private static final String BASE_URL = "https://maps.googleapis.com/";
    private static final String FIELDS = "formatted_phone_number,website,formatted_address,geometry/location";

    Toolbar mToolbar;
    TextView mTextView;

    ImageView mPlacePhoto;
    TextView mPlaceTitle;
    TextView mPlaceAddress;
    TextView mPlaceWebsite;
    TextView mPlacePhone;

    ImageButton zoomIn, zoomOut;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    View map_view;

    Retrofit retrofit;
    PlaceApi placeApi;


    private double latitude;
    private double longitude;
    private String placePhoto;
    private String placeName;
    private String placeAddress;
    private String placeWebsite;
    private String placePhone;
    private String placeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        // toolbar config
        mToolbar = findViewById(R.id.place_info_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        // set toolbar title
        mTextView = findViewById(R.id.toolbar_title);
        mTextView.setText(R.string.place_info_toolbar_title);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // views declaration
        mPlacePhoto = findViewById(R.id.place_Image);
        mPlaceTitle = findViewById(R.id.place_title);
        mPlaceAddress = findViewById(R.id.place_location);
        mPlaceWebsite = findViewById(R.id.place_website);
        mPlacePhone = findViewById(R.id.place_phone);
        zoomIn = findViewById(R.id.zoomIn);
        zoomOut = findViewById(R.id.zoomOut);

        //retrofit config
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        placeApi = retrofit.create(PlaceApi.class);

        // Google Map
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        assert mapFragment != null;
        map_view = mapFragment.getView();

        mapFragment.getMapAsync(PlaceInfoActivity.this);

        // receive data from Adapter
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            placeId = extras.getString("place_id");
            placePhoto = extras.getString("place_photo");
            placeName = extras.getString("place_name");
            latitude = extras.getDouble("place_lat");
            longitude = extras.getDouble("place_lng");
        }


        // set the placeImage and placeName
        mPlaceTitle.setText(placeName);
        Glide.with(this)
                .load(placePhoto)
                .placeholder(R.drawable.placeholder_image)
                .into(mPlacePhoto);


        // call Place Api
        getDetails(placeId);

        // back to places activity
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on back button pressed
                finish();

            }
        });

    }

    private void getDetails(String id) {

        if (!id.equals("Not Found")) {
            Call<JsonObject> call = placeApi.getDetails(id, FIELDS, PlACE_API_KEY);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(@NotNull Call<JsonObject> call, @NotNull Response<JsonObject> response) {
                    if (!response.isSuccessful()) {
                        Log.d(TAG, "Code: " + response.code());
                        return;
                    }

                    JsonObject root = response.body();

                    // receive data from the parsing and set it to the views.
                    assert root != null;
                    List<String> data = parseDetails(root);

                    // set placeAddress
                    placeAddress = data.get(0);
                    mPlaceAddress.setText(placeAddress);

                    // set placeWebsite
                    placeWebsite = data.get(1);
                    try {
                        URL url = new URL(placeWebsite);
                        String baseUrl = url.getHost();
                        if (!placeWebsite.equals("Unknown")) {
                            mPlaceWebsite.setText(baseUrl);
                            mPlaceWebsite.setTextColor(getResources().getColor(R.color.place_phone_color));
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    mPlaceWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(placeWebsite));
                            startActivity(browserIntent);
                        }
                    });

                    // set placePhone
                    placePhone = data.get(2);
                    if (!placePhone.equals("Unknown")) {
                        mPlacePhone.setTextColor(getResources().getColor(R.color.place_phone_color));
                    }
                    SpannableString content = new SpannableString(placePhone);
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    mPlacePhone.setText(content);

                    mPlacePhone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + placePhone)));
                        }
                    });

                }

                @Override
                public void onFailure(@NotNull Call<JsonObject> call, @NotNull Throwable t) {
                    Log.d(TAG, "onFailure: " + t.getMessage());

                }
            });

        }
    }


    private List<String> parseDetails(JsonObject root) {

        List<String> details = new ArrayList<>();

        String mAddress = "Unknown";
        String mWebsite = "Unknown";
        String mPhone = "Unknown";

        JsonObject result = root.getAsJsonObject("result");

        // parse the json body
        if (result.has("formatted_address") && !result.get("formatted_address").isJsonNull()) {
            JsonElement element_address = result.get("formatted_address");
            mAddress = element_address.getAsString();
        }

        if (result.has("formatted_phone_number") && !result.get("formatted_phone_number").isJsonNull()) {
            JsonElement element_phone = result.get("formatted_phone_number");
            mPhone = element_phone.getAsString();
        }

        if (result.has("website") && !result.get("website").isJsonNull()) {
            JsonElement element_website = result.get("website");
            mWebsite = element_website.getAsString();
        }

        details.add(mAddress);
        details.add(mWebsite);
        details.add(mPhone);

        return details;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng googleMapPlace = new LatLng(latitude, longitude);
        MarkerOptions my_own_marker = new MarkerOptions().position(googleMapPlace).title(placeName);
        my_own_marker.icon((getBitmapDescriptor(R.drawable.ic_pin)));
        mMap.addMarker(my_own_marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleMapPlace, 16.0f));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        zoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        zoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
    }

    private BitmapDescriptor getBitmapDescriptor(@DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
