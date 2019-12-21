package com.example.seyaha;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddTourActivity extends AppCompatActivity {

    private static final String TAG = "AddTourActivity";
    RecyclerView recyclerView;
    List<Place> mPlaces;
    Tour mTour;
    List<Place> chosen_place;
    TextInputEditText titleAR, titleEN;
    GridLayoutManager gridLayoutManager;
    boolean flag = false;
    int[] counter;

    private Toolbar mToolbar;
    private TextView mTextView;

    //Firebase
    private FirebaseFirestore mFirebaseFirestore;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        prepareView();
        prepareActionBar();

        FirestoreQueries.getPlaces(new FirestoreQueries.FirestorePlaceCallback() {

            @Override
            public void onCallback(List<Place> places) {
                mPlaces = places;
                System.out.println(places.get(0).activities.get(0).nameEN);
                AdminPlaceAdapter adminPlaceAdapter = new AdminPlaceAdapter(AddTourActivity.this, mPlaces);
                adminPlaceAdapter.setHasStableIds(true);
                recyclerView.setAdapter(adminPlaceAdapter);
            }
        });
    }
    public void prepareActionBar()
    {
        // add back arrow to toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mTextView.setText(getString(R.string.create_tour));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
    public void prepareView()
    {
        mToolbar = findViewById(R.id.add_tour_toolbar);
        mTextView = mToolbar.findViewById(R.id.toolbar_title);

        titleAR = findViewById(R.id.titleA);
        titleEN = findViewById(R.id.titleE);
        recyclerView = findViewById(R.id.fav_categories_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        chosen_place = new ArrayList<Place>();
        mPlaces = new ArrayList<Place>();

        FirestoreQueries.getPlaces(new FirestoreQueries.FirestorePlaceCallback() {

            @Override
            public void onCallback(List<Place> places) {
                mPlaces = places;
                System.out.println(places.get(0).activities.get(0).nameEN);
                AdminPlaceAdapter adminPlaceAdapter = new AdminPlaceAdapter(AddTourActivity.this, mPlaces);
                adminPlaceAdapter.setHasStableIds(true);
                recyclerView.setAdapter(adminPlaceAdapter);
            }
        });
        chosen_place = new ArrayList<Place>();
        mPlaces = new ArrayList<Place>();

    }

    public void addTour(View v) {
        if (AdminPlaceAdapter.chosen_places.size() >= 3) {
            chosen_place = AdminPlaceAdapter.chosen_places;
            if (Text_checker()) {
                mFirebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference newTourRef = mFirebaseFirestore.collection("tours").document();
                List<Comment> comments = new ArrayList<Comment>();
                mTour = new Tour(makeCategoryArabic(chosen_place), makeCategoryEnglish(chosen_place), comments, 0, images(chosen_place), 0, chosen_place, 0.0, titleAR.getText().toString().trim(), titleEN.getText().toString().trim(), newTourRef.getId(), makeKeywords(chosen_place));
                newTourRef.set(mTour).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                            Log.d(TAG, "onComplete: " + " Created a new Tour!");
                        else
                            Log.d(TAG, "Failed!!");
                    }
                });
                flag = true;
            }
            if (flag) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }


        } else {
            Toast.makeText(this, getString(R.string.toast_places), Toast.LENGTH_LONG).show();

        }
    }

    private ArrayList<String> makeCategoryEnglish(List<Place> mPlaces) {
        ArrayList<String> en = new ArrayList<>();
        for (int i = 0; i < mPlaces.size(); i++) {
            if (!en.contains(mPlaces.get(i).categoryEN))
                en.add(mPlaces.get(i).categoryEN);
        }
        return en;
    }

    private ArrayList<String> makeCategoryArabic(List<Place> mPlaces) {
        ArrayList<String> ar = new ArrayList<>();
        for (int i = 0; i < mPlaces.size(); i++) {
            if (!ar.contains(mPlaces.get(i).categoryAR))
                ar.add(mPlaces.get(i).categoryAR);
        }
        return ar;
    }

    private String makeKeywords(List<Place> mPlaces) {
        String tourKeywords = "";
        for (int i = 0; i < mPlaces.size(); i++) {
            String placeKeywords = mPlaces.get(i).keywords.trim();
            String[] placeKeywordsArr = placeKeywords.split(" ");
            for (int j = 0; j < placeKeywordsArr.length; j++) {
                if (!tourKeywords.contains(placeKeywordsArr[j]))
                    tourKeywords += placeKeywordsArr[j] + " ";
            }
        }

        return tourKeywords;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

}
