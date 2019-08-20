package com.example.seyaha;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddTourActivity extends AppCompatActivity
{
    List<Place> mPlaces;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);

        mPlaces = new ArrayList<Place>();

        FirestoreQueries.getPlaces(new FirestoreQueries.FirestorePlaceCallback() {

            @Override
            public void onCallback(List<Place> places) {
                mPlaces = places;
                Log.d("boss", mPlaces.get(0).toString());
                Log.d("boss", mPlaces.get(1).toString());
                Log.d("boss", mPlaces.get(2).toString());
                Log.d("boss", mPlaces.get(3).toString());
                Log.d("boss", mPlaces.get(4).toString());
            }
        });


    }
}
