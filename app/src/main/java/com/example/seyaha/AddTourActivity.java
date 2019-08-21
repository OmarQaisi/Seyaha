package com.example.seyaha;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddTourActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Place> mPlaces;
    Tour mTour;
    List<Place> chosen_place;
    EditText titleAR, titleEN;
    GridLayoutManager gridLayoutManager;
    boolean flag=false;
    //Firebase
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference tours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        titleAR = findViewById(R.id.titleA);
        titleEN = findViewById(R.id.titleE);
        recyclerView = findViewById(R.id.fav_categories_rv);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        chosen_place = new ArrayList<Place>();

        mPlaces = new ArrayList<Place>();

        FirestoreQueries.getPlaces(new FirestoreQueries.FirestorePlaceCallback() {

            @Override
            public void onCallback(List<Place> places) {
                mPlaces = places;

                adminPlaceAdapter adminPlaceAdapter = new adminPlaceAdapter(AddTourActivity.this, mPlaces);
                recyclerView.setAdapter(adminPlaceAdapter);
            }
        });
    }

    public void addTour(View v) {
        if (adminPlaceAdapter.chosen_places.size() >= 3) {
            chosen_place = adminPlaceAdapter.chosen_places;
            Log.d("boss", adminPlaceAdapter.chosen_places.size()+"");
            if (Text_checker()){
                List<Comment> comments = new ArrayList<Comment>();
                mTour = new Tour(makeCategoryArabic(chosen_place), makeCategoryEnglish(chosen_place), comments, 0, images(chosen_place), chosen_place, 0.0, titleAR.getText().toString().trim(), titleEN.getText().toString().trim());

                mFirebaseFirestore = FirebaseFirestore.getInstance();
                tours = mFirebaseFirestore.collection("tours");
                tours.add(mTour);

                flag = true;

            }
            if(flag){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        } else {
            Toast.makeText(this, "please choose atleast 3 places to create a Tour.", Toast.LENGTH_LONG).show();

        }
    }

    private ArrayList<String> makeCategoryEnglish(List<Place> mPlaces) {
        ArrayList<String> en = new ArrayList<>();
        for (int i = 0; i < mPlaces.size(); i++) {
            if(!en.contains(mPlaces.get(i).categoryEN))
                en.add(mPlaces.get(i).categoryEN);
        }
        return en;
    }

    private ArrayList<String> makeCategoryArabic(List<Place> mPlaces) {
        ArrayList<String> ar = new ArrayList<>();
        for (int i = 0; i < mPlaces.size(); i++) {
            if(!ar.contains(mPlaces.get(i).categoryAR))
                ar.add(mPlaces.get(i).categoryAR);
        }
        return ar;
    }

    private ArrayList<String> images(List<Place> mPlaces) {
        ArrayList<String> URLs = new ArrayList<String>();
        for (int i = 0; i < mPlaces.size(); i++) {
            URLs.add(mPlaces.get(i).imageURL);
        }

        return URLs;
    }

    private boolean Text_checker() {
        if (titleEN.getText().toString().isEmpty() || titleEN.getText().toString().equalsIgnoreCase("")) {
            titleEN.setError("please add this feild");
            return false;
        } else if (titleAR.getText().toString().isEmpty() || titleAR.getText().toString().equalsIgnoreCase("")) {
            titleAR.setError("please add this feild");
            return false;
        }
        return true;
    }

    public void resetPlaces(View view) {
        //adminPlaceAdapter.chosen_places.clear();

        //for (int i=0; i<adminPlaceAdapter.counter.length;i++){
            //adminPlaceAdapter.counter[i] = 0;
        //}
    }
}
