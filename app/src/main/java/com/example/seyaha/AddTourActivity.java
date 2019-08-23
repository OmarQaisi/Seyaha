package com.example.seyaha;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AddTourActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Place> mPlaces;
    Tour mTour;
    List<Place> chosen_place;
    TextInputEditText titleAR, titleEN;
    Button publishButtton;
    RelativeLayout mRelativeLayout;
    SoftKeyboard softKeyboard;
    GridLayoutManager gridLayoutManager;
    boolean flag=false;

    private Toolbar mToolbar;
    private TextView mTextView;

    //Firebase
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference tours;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mTextView = mToolbar.findViewById(R.id.toolbar_title);
        mTextView.setText("Create Tour");

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleAR = findViewById(R.id.titleA);
        titleEN = findViewById(R.id.titleE);
        publishButtton = findViewById(R.id.add_tour_btn);

        /*mRelativeLayout =  findViewById(R.id.add_tour_relativeLayout);
        InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);

        //Instantiate and pass a callback
        softKeyboard = new SoftKeyboard(mRelativeLayout, mInputMethodManager);

        softKeyboard.setSoftKeyboardCallback(new SoftKeyboard.SoftKeyboardChanged()
        {
            @Override
            public void onSoftKeyboardHide()
            {
                publishButtton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSoftKeyboardShow()
            {
                publishButtton.setVisibility(View.INVISIBLE);
            }
        });*/

        recyclerView = findViewById(R.id.fav_categories_rv);
        gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        chosen_place = new ArrayList<Place>();

        mPlaces = new ArrayList<Place>();

        FirestoreQueries.getPlaces(new FirestoreQueries.FirestorePlaceCallback() {

            @Override
            public void onCallback(List<Place> places) {
                mPlaces = places;

                AdminPlaceAdapter adminPlaceAdapter = new AdminPlaceAdapter(AddTourActivity.this, mPlaces);
                recyclerView.setAdapter(adminPlaceAdapter);
            }
        });
    }

    public void addTour(View v) {
        if (AdminPlaceAdapter.chosen_places.size() >= 3) {
            chosen_place = AdminPlaceAdapter.chosen_places;
            Log.d("boss", AdminPlaceAdapter.chosen_places.size()+"");
            if (Text_checker()){
                List<Comment> comments = new ArrayList<Comment>();
                mTour = new Tour(makeCategoryArabic(chosen_place), makeCategoryEnglish(chosen_place), comments, 0, images(chosen_place), 0, chosen_place, 0.0, titleAR.getText().toString().trim(), titleEN.getText().toString().trim());

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
       // softKeyboard.unRegisterSoftKeyboardCallback();
    }

}
