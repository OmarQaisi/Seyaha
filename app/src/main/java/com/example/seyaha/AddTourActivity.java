package com.example.seyaha;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddTourActivity extends AppCompatActivity
{

    RecyclerView recyclerView;
    List<Place> mPlaces;
    Tour addTour;
    List<Place>chosen_place;
        EditText titleAR,titleEN;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tour);
        titleAR=findViewById(R.id.titleA);
        titleEN=findViewById(R.id.titleE);
        recyclerView=(RecyclerView)findViewById(R.id.fav_categories_rv);
        gridLayoutManager=new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        chosen_place=new ArrayList <Place>();

        mPlaces = new ArrayList<Place>();

        FirestoreQueries.getPlaces(new FirestoreQueries.FirestorePlaceCallback() {

            @Override
            public void onCallback(List<Place> places) {
                mPlaces = places;

                adminPlaceAdapter adminPlaceAdapter=new adminPlaceAdapter(AddTourActivity.this,mPlaces);
                recyclerView.setAdapter(adminPlaceAdapter);
            }
        });
    }
    public void addTour(View v)
    {
        try
        {
            if(adminPlaceAdapter.chosen_places.size()!=0)
                {
                  chosen_place=adminPlaceAdapter.chosen_places;
                  if(Text_checker())
                  addTour=new Tour("TitleAR","TitleEN",Cat_AR(chosen_place),Cat_EN(chosen_place)
                          ,images(chosen_place),mPlaces,0,0,new ArrayList <Comment>());
                }
            else
            {
                Toast.makeText(this,"please chose a place please",Toast.LENGTH_LONG).show();

            }


    }
        catch (Exception e)
        {
            Toast.makeText(this,"please chose a place please",Toast.LENGTH_LONG).show();

        }
    }
    private String Cat_EN(List<Place>mPlaces)
    {
        String cat_en="Categories: ";

        for(int i=0;i<mPlaces.size();i++)
        {
            cat_en=cat_en+mPlaces.get(i).categoryEN+",";
        }
        cat_en+="0";
        return  cat_en;
    }
    private String Cat_AR(List<Place>mPlaces)
    {
        String cat_ar="Categories: ";

        for(int i=0;i<mPlaces.size();i++)
        {
            cat_ar=cat_ar+mPlaces.get(i).categoryAR+",";
        }
        cat_ar+="0";
        return  cat_ar;
    }
    private String []images(List<Place>mPlaces)
    {
        String []URLs=new String[mPlaces.size()];
        for(int i=0;i<mPlaces.size();i++)
        {
            URLs[i]=mPlaces.get(i).imageURL;
        }

        return  URLs;
    }
    private boolean Text_checker()
    {
        if(titleEN.getText().toString().isEmpty()||titleEN.getText().toString().equalsIgnoreCase(""))
        {
            titleEN.setError("please add this feild");
            return false;
        }
        else if(titleAR.getText().toString().isEmpty()||titleAR.getText().toString().equalsIgnoreCase(""))
        {
            titleAR.setError("please add this feild");
            return false;
        }
        return true;
    }

}
