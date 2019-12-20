package com.example.seyaha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PlacesActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    static String latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        // toolbar config
        Toolbar mToolbar = findViewById(R.id.places_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        TextView mTextView = findViewById(R.id.toolbar_title);
        mTextView.setText(R.string.places_toolbar_title);
        mToolbar.setScrollbarFadingEnabled(true);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // bottom navigation config
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // set listener to change between Hotels and Restaurant Fragments.
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        // set default fragment to show.
        getSupportFragmentManager().beginTransaction().replace(R.id.places_fragment_container, new HotelsFragment()).commit();

        // receive  Lat and Lng from DetailedActivity.
        Intent I = getIntent();
        double lat = I.getDoubleExtra("lat", 0.0);
        double lng = I.getDoubleExtra("lng", 0.0);
        latLng = lat + "," + lng;

        // back to DetailedActivity.
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on back button pressed
                finish();

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment SelectedFragment = null;
                    String SelectedFragmentId = "";
                    switch (menuItem.getItemId()) {
                        case R.id.nav_hotels:
                            SelectedFragment = new HotelsFragment();
                            SelectedFragmentId = "HotelsFrag";
                            break;
                        case R.id.nav_restaurants:
                            SelectedFragment = new RestaurantsFragment();
                            SelectedFragmentId = "RestaurantFrag";
                            break;
                    }

                    assert SelectedFragment != null;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.places_fragment_container, SelectedFragment, SelectedFragmentId).commit();
                    return true;
                }
            };

    // send Lat and Lng to Hotels and Restaurant Fragments.
    public static String getLocation() {
        return latLng;

    }
}

