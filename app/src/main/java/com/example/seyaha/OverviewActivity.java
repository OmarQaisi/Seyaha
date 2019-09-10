package com.example.seyaha;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class OverviewActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    List<Place> mPlace;

    private Toolbar mToolbar;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        mToolbar = findViewById(R.id.overview_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mTextView = findViewById(R.id.toolbar_title);
        mTextView.setText(R.string.overview_activity_title);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent i = getIntent();
        mPlace = (List<Place>) i.getSerializableExtra("places");
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng googleMapPlace = new LatLng(0, 0);
        MarkerOptions[] my_own_marker = new MarkerOptions[mPlace.size()];
        for (int i = 0; i < mPlace.size(); i++) {
            googleMapPlace = new LatLng(mPlace.get(i).latitude, mPlace.get(i).longitude);

            if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
                my_own_marker[i] = new MarkerOptions().position(googleMapPlace).title(mPlace.get(i).nameAR);
            } else {
                my_own_marker[i] = new MarkerOptions().position(googleMapPlace).title(mPlace.get(i).nameEN);
            }
            my_own_marker[i].icon((getBitmapDescriptor(R.drawable.ic_pin)));
            mMap.addMarker(my_own_marker[i]);
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MarkerOptions marker : my_own_marker) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width - 100, height - 100, padding);
        mMap.animateCamera(cu);


        // mMap.addMarker(new MarkerOptions().position(sydney).title("third circle"));

    }

    private BitmapDescriptor getBitmapDescriptor(@DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
