package com.example.seyaha;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ViewPager viewPager;
    List <String> imageUrls;
    List<Place> mPlace;
    viewPagerAdapter adapter;
    double latitude,longitude;
    String placeName;


    SupportMapFragment mapFragment;
    EasyFlipView seasonFlip,timeToGoFlip,estimationFlip,ageFlip;
    TextView seasonTv,timeToGoTv,ageTv1,ageTv2,estimationTv,costTv,tempTv,airQualityTv,internetTv,placeNameInfo, placeNameRecommendations,placeNameLocation,description;
    RoundCornerProgressBar costProgressBar,tempProgressBar,airQualityProgressBar,internetProgressBar;
    View frontLayoutSeason,backLayoutSeason,frontLayoutTime,backLayouTime,frontLayoutAge,backLayoutAge,frontLayoutEstimated,backLayoutEstimated;
    ImageView seasonImg,timeToGoImg,estimationImg;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);



        //text views and description deceleration
        placeNameRecommendations =findViewById(R.id.place_name_recommendations);
        placeNameInfo=findViewById(R.id.place_name_information_about);
        placeNameLocation=findViewById(R.id.place_name_location);
        description=findViewById(R.id.description_tv);

        //information about the place deceleration
        costProgressBar=findViewById(R.id.cost_progress);
        tempProgressBar=findViewById(R.id.temp_progress);
        airQualityProgressBar=findViewById(R.id.air_quality_progress);
        internetProgressBar=findViewById(R.id.internet_progress);
        costTv=findViewById(R.id.cost_tv);
        tempTv=findViewById(R.id.temp_tv);
        airQualityTv=findViewById(R.id.air_quality_tv);
        internetTv=findViewById(R.id.internet_tv);
        //recommendations about the place deceleration
        seasonFlip=findViewById(R.id.season_btn);
        timeToGoFlip=findViewById(R.id.time_btn);
        ageFlip=findViewById(R.id.age_btn);
        estimationFlip=findViewById(R.id.estimated_btn);


        Intent i=getIntent();
        mPlace=(List<Place>)i.getSerializableExtra("places");


        latitude=mPlace.get(0).latitude;
        longitude=mPlace.get(0).longitude;
        placeName=mPlace.get(0).nameEN;

        mapFragment.getMapAsync(this);

        run_viewPager();

        setCostProgress(mPlace.get(0).cost);
        setTempProgress("40");
        setAirQualityProgress("20");
        setInternetProgress(mPlace.get(0).internet);

        setSeason(mPlace.get(0).recommendedSeason);
        setTimeToGo(mPlace.get(0).recommendedTime);
        setAge(mPlace.get(0).recommendedAge);
        setEstimatedTime(mPlace.get(0).estimatedTime);

        description.setText(mPlace.get(0).descEN);
        placeNameRecommendations.setText(mPlace.get(0).nameEN);
        placeNameInfo.setText(mPlace.get(0).nameEN);
        placeNameLocation.setText(mPlace.get(0).nameEN);

    }

    private void run_viewPager() {
        imageUrls = new ArrayList <>();

        for(int i=0;i<mPlace.size();i++)
        {
            imageUrls.add(mPlace.get(i).imageURL);
        }

        adapter = new viewPagerAdapter(imageUrls, this);
        viewPager = findViewById(R.id.ViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100, 0, 100, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                setCostProgress(mPlace.get(position).cost);
                setTempProgress("40");
                setAirQualityProgress("20");
                setInternetProgress(mPlace.get(position).internet);

                setSeason(mPlace.get(position).recommendedSeason);
                setTimeToGo(mPlace.get(position).recommendedTime);
                setAge(mPlace.get(position).recommendedAge);
                setEstimatedTime(mPlace.get(position).estimatedTime);

                description.setText(mPlace.get(position).descEN);
                placeNameRecommendations.setText(mPlace.get(position).nameEN);
                placeNameInfo.setText(mPlace.get(position).nameEN);
                placeNameLocation.setText(mPlace.get(position).nameEN);


                latitude=mPlace.get(position).latitude;
                longitude=mPlace.get(position).longitude;
                placeName=mPlace.get(position).nameEN;
                mapFragment.getMapAsync(DetailedActivity.this);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        LatLng googleMapPlace = new LatLng(latitude, longitude);
        MarkerOptions my_own_marker = new MarkerOptions().position(googleMapPlace).title(placeName);
        my_own_marker.icon((getBitmapDescriptor(R.drawable.ic_gps)));
        mMap.addMarker(my_own_marker);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("third circle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleMapPlace, 16.0f));
       // mMap.animateCamera(CameraUpdateFactory.zoomIn());
        //mMap.animateCamera(CameraUpdateFactory.zoomOut());

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

    private void setCostProgress(int cost)
    {

        costTv.setText(cost+"JD");
        if(cost<=20)
        {
            costProgressBar.setProgress(100);
            costProgressBar.setProgressColor(Color.GREEN);
        }
        else if(cost>20 && cost<=60)
        {
            costProgressBar.setProgress(60);
            costProgressBar.setProgressColor(Color.rgb(255,165,0));
        }
        else
        {
            costProgressBar.setProgress(30);
            costProgressBar.setProgressColor(Color.RED);
        }
        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(costProgressBar, "progress", 0.0f,costProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();

    }

    private void setTempProgress(String temp)
    {
        int result=Integer.parseInt(temp);

        if(result<=10)
        {
            tempProgressBar.setProgressColor(Color.RED);
            tempProgressBar.setProgress(result);
            tempTv.setText(getResources().getString(R.string.cold)+temp+"\u2103"+"(now)");

        }
        else  if(result>10 && result<=18)
        {
            tempTv.setText(getResources().getString(R.string.normal)+temp+"\u2103"+"(now)");
            tempProgressBar.setProgress(result);
            tempProgressBar.setProgressColor(Color.rgb(255,165,0));
        }
        else if(result>18 && result<=30)
        {
            tempProgressBar.setProgressColor(Color.GREEN);
            tempProgressBar.setProgress(55);
            tempTv.setText(getResources().getString(R.string.perfect)+temp+"\u2103"+"(now)");
        }
        else
        {
            tempProgressBar.setProgressColor(Color.RED);
            tempProgressBar.setProgress(55-result);
            tempTv.setText(getResources().getString(R.string.hot)+temp+"\u2103"+"(now)");
        }

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(tempProgressBar, "progress", 0.0f,tempProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();

    }

    private void setAirQualityProgress(String airQuality)
    {
        airQualityTv.setText(airQuality+"\u00B5"+"g/m3"+"(now)");
        int result=Integer.parseInt(airQuality);
        airQualityProgressBar.setProgress(100-result);
        if(result<=25)
        {
            airQualityProgressBar.setProgressColor(Color.GREEN);
        }
        else if(result>25 && result<=50)
        {
            airQualityProgressBar.setProgressColor( Color.rgb(255,165,0));
        }
        else
        {
         airQualityProgressBar.setProgressColor(Color.RED);
        }
        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(airQualityProgressBar, "progress", 0.0f,airQualityProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();
    }

    private void setInternetProgress(int internet)
    {
        switch (internet)
        {
            case 0:
                internetProgressBar.setProgressColor(Color.RED);
                internetProgressBar.setProgress(30);
                internetTv.setText(getResources().getString(R.string.bad_internet));
                break;
            case 1:
                internetProgressBar.setProgressColor(Color.rgb(255,165,0));
                internetProgressBar.setProgress(60);
                internetTv.setText(getResources().getString(R.string.good_internet));
                break;
            case 2:
                internetProgressBar.setProgressColor(Color.GREEN);
                internetProgressBar.setProgress(100);
                internetTv.setText(getResources().getString(R.string.great_internet));
                break;
        }
        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(internetProgressBar, "progress", 0.0f,internetProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();
    }

   private void setSeason(int season)
    {

        frontLayoutSeason=seasonFlip.findViewById(R.id.front_season);
        backLayoutSeason=seasonFlip.findViewById(R.id.back_season);
        seasonTv=backLayoutSeason.findViewById(R.id.back_text);
        seasonImg=frontLayoutSeason.findViewById(R.id.front_icon);
       switch (season)
       {
           case 0:
             seasonTv.setText(getResources().getString(R.string.summer_season));
             seasonImg.setImageResource(R.drawable.ic_summer);
           break;

           case 1:
               seasonTv.setText(getResources().getString(R.string.winter_season));
               seasonImg.setImageResource(R.drawable.ic_winter);
           break;

           case 2:
               seasonTv.setText(getResources().getString(R.string.spring_season));
               seasonImg.setImageResource(R.drawable.ic_spring);
           break;

           case 3:
               seasonTv.setText(getResources().getString(R.string.autumn_season));
               seasonImg.setImageResource(R.drawable.ic_autumn);
           break;
       }
    }

    private  void setTimeToGo(int time)
    {
        frontLayoutTime=findViewById(R.id.front_time);
        backLayouTime=findViewById(R.id.back_time);
        timeToGoTv=backLayouTime.findViewById(R.id.back_text);
        timeToGoImg=frontLayoutTime.findViewById(R.id.front_icon);

        switch (time)
        {
            case 0:
                timeToGoTv.setText(getResources().getString(R.string.day_time));
                timeToGoImg.setImageResource(R.drawable.ic_day);
                break;

            case 1:
                timeToGoTv.setText(getResources().getString(R.string.night_time));
               timeToGoImg.setImageResource(R.drawable.ic_night);
                break;
        }
    }

    private void setAge(String age)
    {
        frontLayoutAge=findViewById(R.id.front_ag);
        backLayoutAge=findViewById(R.id.back_age);
        ageTv1=backLayoutAge.findViewById(R.id.back_text);
        ageTv2=frontLayoutAge.findViewById(R.id.back_text);
        ageTv1.setText(age);
        ageTv2.setText(age);
    }

    private void setEstimatedTime(int estimatedTime)
    {
        frontLayoutEstimated=findViewById(R.id.front_estimated);
        backLayoutEstimated=findViewById(R.id.back_estimated);
        estimationTv=backLayoutEstimated.findViewById(R.id.back_text);
        estimationImg=frontLayoutEstimated.findViewById(R.id.front_icon);
        estimationTv.setText(estimatedTime+"Hrs");
        estimationImg.setImageResource(R.drawable.ic_sand_clock);
    }
}