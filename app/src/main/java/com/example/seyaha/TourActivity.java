package com.example.seyaha;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TourActivity extends AppCompatActivity {

    private static final String TAG = "TourActivity";

    ArrayList<Tour> tours = new ArrayList<Tour>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView tv = findViewById(R.id.toolbar_title);
        tv.setText("Tours");

        initalizeTours();
        TourAdapter mAdapter = new TourAdapter(tours);
        mRecyclerView = findViewById(R.id.rv);
        mManger = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManger);
        mRecyclerView.setAdapter(mAdapter);

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
        //toggle.syncState();

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

    /*
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
}
