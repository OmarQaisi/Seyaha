package com.example.seyaha;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class TourActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TourActivity";
    private DrawerLayout mDrawerLayout;

    ArrayList<Tour> tours = new ArrayList<Tour>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        TextView tv = findViewById(R.id.toolbar_title);
        tv.setText("Tours");

        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        toggle.syncState();

        initalizeTours();
        TourAdapter mAdapter = new TourAdapter(tours);
        mRecyclerView = findViewById(R.id.rv);
        mManger = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManger);
        mRecyclerView.setAdapter(mAdapter);

    }

    protected void initalizeTours() {
        String[] tour1_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
        "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
        "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        Tour tour1 = new Tour(tour1_imgs,4.6,98,"3-Day", "Categories: Historical and Religion");

        String[] tour2_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
                "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
                "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        Tour tour2 = new Tour(tour2_imgs,4.7,125,"5-Day", "Categories: Adventure and Historical");

        String[] tour3_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
                "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
                "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        Tour tour3 = new Tour(tour3_imgs,2.7,12,"4-Day", "Categories: Historical, Adventure and Treatment.");

        tours.add(tour1);
        tours.add(tour2);
        tours.add(tour3);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                Toast.makeText(this, "Comming Soon..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_upcoming_event:
                Toast.makeText(this, "Comming Soon..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_help_feedback:
                Toast.makeText(this, "Comming Soon..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "Comming Soon..", Toast.LENGTH_SHORT).show();
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
