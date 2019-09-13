package com.example.seyaha;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class DetailedActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final String TAG = "DetailedActivity";

    private GoogleMap mMap;
    ViewPager viewPager;
    List <String> imageUrls;
    List <Place> mPlace;
    ViewPagerAdapter adapter;
    double latitude, longitude;
    String placeName;
    MediaPlayer mp;
    AlertDialog alertDialog;
    private final String APIKEY = "4e4480d5039580a36c576fa58a0c1d3a";
    private OpenWeatherApi openWeatherApi;
    private double tempApiResult,min,max;
    ImageButton zoom_in, zoom_out, text_to_speech;
    View map_view;
    private Toolbar mToolbar;
    private TextView mTextView;
    ScrollView scrollView;
    SupportMapFragment mapFragment;
    FrameLayout seasonFlip, timeToGoFlip, estimationFlip, ageFlip;
    TextView seasonTv, timeToGoTv, ageTv1, ageTv2, estimationTv, costTv, tempTv, airQualityTv, internetTv, placeNameInfo, placeNameRecommendations, placeNameLocation, description, placeNameTitle;
    RoundCornerProgressBar costProgressBar, tempProgressBar, airQualityProgressBar, internetProgressBar;
    View frontLayoutSeason, backLayoutSeason, frontLayoutTime, backLayouTime, frontLayoutAge, backLayoutAge, frontLayoutEstimated, backLayoutEstimated;
    ImageView seasonImg, timeToGoImg, estimationImg;
    TextView num_of_person;
    private AnimatorSet mSetRightOut;
    private AnimatorSet mSetLeftIn;
    private boolean[] mIsBackVisible = {false, false, false, false};
    int i=1,avaragecost=0,size;;
    static int real_position=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        mToolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
        mTextView = findViewById(R.id.toolbar_title);
        mTextView.setText(R.string.detailed_activity_title);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //retrofit config
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
        openWeatherApi = retrofit.create(OpenWeatherApi.class);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //text views and description deceleration
        placeNameRecommendations = findViewById(R.id.place_name_recommendations);
        placeNameInfo = findViewById(R.id.place_name_information_about);
        placeNameLocation = findViewById(R.id.place_name_location);
        zoom_in = findViewById(R.id.zoomin);
        zoom_out = findViewById(R.id.zoomout);
        description = findViewById(R.id.description_tv);
        scrollView = findViewById(R.id.scrollview);
        placeNameTitle = findViewById(R.id.place_name_title);
        //information about the place deceleration
        costProgressBar = findViewById(R.id.cost_progress);
        tempProgressBar = findViewById(R.id.temp_progress);
        airQualityProgressBar = findViewById(R.id.air_quality_progress);
        internetProgressBar = findViewById(R.id.internet_progress);
        costTv = findViewById(R.id.cost_tv);
        tempTv = findViewById(R.id.temp_tv);
        airQualityTv = findViewById(R.id.air_quality_tv);
        internetTv = findViewById(R.id.internet_tv);
        //recommendations about the place deceleration
        seasonFlip = findViewById(R.id.season_btn);
        timeToGoFlip = findViewById(R.id.time_btn);
        ageFlip = findViewById(R.id.age_btn);
        estimationFlip = findViewById(R.id.estimated_btn);
        loadAnimations();
        Intent i = getIntent();
        mPlace = (List <Place>) i.getSerializableExtra("places");
        description.setMovementMethod(new ScrollingMovementMethod());
        latitude = mPlace.get(0).latitude;
        longitude = mPlace.get(0).longitude;
        placeName = mPlace.get(0).nameEN;
        map_view = mapFragment.getView();
        mapFragment.getMapAsync(this);

        run_viewPager();

        setCostProgress(mPlace.get(0).cost.activities);
        getTempApi(mPlace.get(0).latitude, mPlace.get(0).longitude);
        setAirQualityProgress(mPlace.get(0).airQuality);
        setInternetProgress(mPlace.get(0).internet);
            try {
                mp = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(mPlace.get(0).voiceURL, "raw", getPackageName()));
            }
            catch (Exception e)
            {
                Log.d("place_name", ""+mPlace.get(0).nameEN+" \n"+e);
            }
        setSeason(mPlace.get(0).recommendedSeason);
        setTimeToGo(mPlace.get(0).recommendedTime);
        setAge(mPlace.get(0).recommendedAge);
        setEstimatedTime(mPlace.get(0).estimatedTime);

        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            description.setText(mPlace.get(0).descAR);
            placeNameRecommendations.setText(mPlace.get(0).nameAR);
            placeNameInfo.setText(mPlace.get(0).nameAR);
            placeNameLocation.setText(mPlace.get(0).nameAR);
            placeNameTitle.setText(mPlace.get(0).nameAR);

        } else {
            placeNameTitle.setText(mPlace.get(0).nameEN);
            description.setText(mPlace.get(0).descEN);
            placeNameRecommendations.setText(mPlace.get(0).nameEN);
            placeNameInfo.setText(mPlace.get(0).nameEN);
            placeNameLocation.setText(mPlace.get(0).nameEN);
        }


        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                description.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        description.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                description.getParent().requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });

        text_to_speech = findViewById(R.id.text_to_speech);
        text_to_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.start();
            }
        });

    }

    public void cost_btn(View view)
    {
        show_cost_dialog();
    }
    private void show_cost_dialog()
    {
        Button close,apply;
        ImageButton plus,minus;
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View mView=getLayoutInflater().inflate(R.layout.cost_popup,null);
        builder.setView(mView);
        close=mView.findViewById(R.id.cancel_btn);
        apply=mView.findViewById(R.id.apply);
        RecyclerView recyclerView=mView.findViewById(R.id.rv);
        plus=mView.findViewById(R.id.plus);
        plus.setOnClickListener(this);
        minus=mView.findViewById(R.id.minus);
        minus.setOnClickListener(this);
        num_of_person=mView.findViewById(R.id.num_of_person);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        List<String> names=new ArrayList<>();
        final List<Integer> value=new ArrayList<>();
        names.add(getString(R.string.transportation_cost));
        names.add(getString(R.string.food_cost));
        names.add(getString(R.string.activities_cost));
        names.add(getString(R.string.entries_cost));
        names.add(getString(R.string.three_star_cost));
        names.add(getString(R.string.four_star_cost));
        names.add(getString(R.string.five_star_hotel));
        value.add(mPlace.get(real_position).cost.transportation);
        value.add(mPlace.get(real_position).cost.food);
        value.add(mPlace.get(real_position).cost.activities);
        value.add(mPlace.get(real_position).cost.entranceFees);
        Log.e("debug",mPlace.get(real_position).nameEN);
        value.add(mPlace.get(real_position).cost.overNightStay.get(2).price);
        value.add(mPlace.get(real_position).cost.overNightStay.get(1).price);
        value.add(mPlace.get(real_position).cost.overNightStay.get(0).price);
        Log.e("debug",mPlace.get(real_position).nameEN);
         size=Integer.parseInt(num_of_person.getText().toString());
        Log.e("debug",mPlace.get(real_position).cost.overNightStay.size()+"");
        final RecyclePopupAdapter adapter=new RecyclePopupAdapter(this,names,value);
        recyclerView.setAdapter(adapter);
          alertDialog=builder.create();
        alertDialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hashmap",adapter.positions.size()+"");

                for(Integer i:adapter.positions.keySet())
                {
                    String key=i.toString();
                  //  int value=adapter.positions.get(key);
                    //avaragecost+=value;
                }
                Log.e("avaragecost",avaragecost+"");
                avaragecost=avaragecost*Integer.parseInt(num_of_person.getText().toString());
                Log.e("avaragecost",avaragecost+"");
                setCostProgress(avaragecost);
                adapter.positions.clear();
                avaragecost=0;
                alertDialog.cancel();
            }
        });

    }
    public void temp_btn(View view) {
        Button ok;
        TextView min_temp,max_temp;
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View mView=getLayoutInflater().inflate(R.layout.temperature_popup,null);
        builder.setView(mView);
        ok=mView.findViewById(R.id.btntemperature);
        max_temp=mView.findViewById(R.id.max_temp);
        min_temp=mView.findViewById(R.id.min_temp);
        max_temp.setText(""+new DecimalFormat("##.##").format(max)+"C");
        min_temp.setText(""+new DecimalFormat("##.##").format(min)+"C");
        alertDialog=builder.create();
        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

    }
    public void quality_btn(View view) {
        Button ok;
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View mView=getLayoutInflater().inflate(R.layout.airquality_popup,null);
        builder.setView(mView);
        ok=mView.findViewById(R.id.btnquality);
        alertDialog=builder.create();
        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }
    public void internet_btn(View view) {
        Button ok;
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        View mView=getLayoutInflater().inflate(R.layout.internet_popup,null);
        builder.setView(mView);
        ok=mView.findViewById(R.id.btninternet);
        alertDialog=builder.create();
        alertDialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
    }
    private void getTempApi(double latitude, double longitude) {
        Call <JsonObject> call = openWeatherApi.getTemp(latitude, longitude, APIKEY);
        call.enqueue(new Callback <JsonObject>() {
            @Override
            public void onResponse(Call <JsonObject> call, Response <JsonObject> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "Code: " + response.code());
                    return;
                }
                JsonObject root = response.body();
                JsonObject main = root.getAsJsonObject("main");
                JsonElement element = main.get("temp");
                JsonElement element_min=main.get("temp_min");
                JsonElement element_max=main.get("temp_max");

                tempApiResult = element.getAsDouble() - 273.15;
                min=element_min.getAsDouble()-273.15;
                max=element_max.getAsDouble()-273.15;
                setTempProgress((int) tempApiResult);
            }

            @Override
            public void onFailure(Call <JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void run_viewPager() {
        imageUrls = new ArrayList <>();

        for (int i = 0; i < mPlace.size(); i++) {
            imageUrls.add(mPlace.get(i).imageURL);
        }

        adapter = new ViewPagerAdapter(imageUrls, this);
        viewPager = findViewById(R.id.ViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100, 0, 100, 0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                real_position=position;
                //System.out.println(positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setCostProgress(mPlace.get(position).cost.activities);
                getTempApi(mPlace.get(position).latitude, mPlace.get(position).longitude);
                setAirQualityProgress(mPlace.get(position).airQuality);
                setInternetProgress(mPlace.get(position).internet);

                mp = MediaPlayer.create(getApplicationContext(), getResources().getIdentifier(mPlace.get(position).voiceURL,"raw",getPackageName()));


                setSeason(mPlace.get(position).recommendedSeason);
                setTimeToGo(mPlace.get(position).recommendedTime);
                setAge(mPlace.get(position).recommendedAge);
                setEstimatedTime(mPlace.get(position).estimatedTime);
                if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
                    description.setText(mPlace.get(position).descAR);
                    placeNameRecommendations.setText(mPlace.get(position).nameAR);
                    placeNameInfo.setText(mPlace.get(position).nameAR);
                    placeNameLocation.setText(mPlace.get(position).nameAR);
                    placeNameTitle.setText(mPlace.get(position).nameAR);
                } else {
                    description.setText(mPlace.get(position).descEN);
                    placeNameRecommendations.setText(mPlace.get(position).nameEN);
                    placeNameInfo.setText(mPlace.get(position).nameEN);
                    placeNameLocation.setText(mPlace.get(position).nameEN);
                    placeNameTitle.setText(mPlace.get(position).nameEN);
                }

                latitude = mPlace.get(position).latitude;
                longitude = mPlace.get(position).longitude;
                placeName = mPlace.get(position).nameEN;
                mapFragment.getMapAsync(DetailedActivity.this);

                scrollView.fullScroll(View.FOCUS_UP);
                description.scrollTo(0, 0);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println(state);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng googleMapPlace = new LatLng(latitude, longitude);
        MarkerOptions my_own_marker = new MarkerOptions().position(googleMapPlace).title(placeName);
        my_own_marker.icon((getBitmapDescriptor(R.drawable.ic_pin)));
        mMap.addMarker(my_own_marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(googleMapPlace, 16.0f));
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        zoom_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        zoom_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });

        //mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    private BitmapDescriptor getBitmapDescriptor(@DrawableRes int id) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void setCostProgress(int cost) {

        costTv.setText(cost + " " + getResources().getString(R.string.JD));
        costProgressBar.setProgress(costProgressBar.getMax()-cost);

        if (cost <= 10*size) {
            costProgressBar.setProgressColor(Color.GREEN);
        } else if (cost > 10*size && cost <= 30*size) {
            costProgressBar.setProgressColor(Color.rgb(255, 165, 0));
        } else {
            costProgressBar.setProgressColor(Color.RED);
        }
        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(costProgressBar, "progress", 0.0f, costProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();

    }

    private void setTempProgress(int temp) {

        if (temp <=10) {
            tempProgressBar.setProgressColor(Color.RED);
            tempProgressBar.setProgress(temp);
            tempTv.setText(getResources().getString(R.string.cold) + temp + "\u2103" +getString(R.string.now));

        } else if (temp > 10 && temp <= 18) {
            tempTv.setText(getResources().getString(R.string.normal) + temp + "\u2103" +getString(R.string.now));
            tempProgressBar.setProgress(temp);
            tempProgressBar.setProgressColor(Color.rgb(255, 165, 0));
        } else if (temp > 18 && temp <= 30) {
            tempProgressBar.setProgressColor(Color.GREEN);
            tempProgressBar.setProgress(55);
            tempTv.setText(getResources().getString(R.string.perfect) + temp + "\u2103" +getString(R.string.now));
        } else {
            tempProgressBar.setProgressColor(Color.RED);
            tempProgressBar.setProgress(55 - temp);
            tempTv.setText(getResources().getString(R.string.hot) + temp + "\u2103" +getString(R.string.now));
        }

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(tempProgressBar, "progress", 0.0f, tempProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();

    }

    private void setAirQualityProgress(int airQuality) {
        airQualityTv.setText(airQuality + "\u00B5" + "g/m3");

        airQualityProgressBar.setProgress(100 - airQuality);
        if (airQuality <= 25) {
            airQualityProgressBar.setProgressColor(Color.GREEN);
        } else if (airQuality > 25 && airQuality <= 50) {
            airQualityProgressBar.setProgressColor(Color.rgb(255, 165, 0));
        } else {
            airQualityProgressBar.setProgressColor(Color.RED);

        }

        ObjectAnimator progressAnimator;
        progressAnimator = ObjectAnimator.ofFloat(airQualityProgressBar, "progress", 0.0f, airQualityProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();
    }

    private void setInternetProgress(int internet) {
        ObjectAnimator progressAnimator;

        switch (internet) {
            case 0:
                internetProgressBar.setProgressColor(Color.RED);
                internetProgressBar.setProgress(30);
                internetTv.setText(getResources().getString(R.string.bad_internet));
                break;
            case 1:
                internetProgressBar.setProgressColor(Color.rgb(255, 165, 0));
                internetProgressBar.setProgress(60);
                internetTv.setText(getResources().getString(R.string.good_internet));
                break;
            case 2:
                internetProgressBar.setProgressColor(Color.GREEN);
                internetProgressBar.setProgress(100);
                internetTv.setText(getResources().getString(R.string.great_internet));
                break;

        }

        progressAnimator = ObjectAnimator.ofFloat(internetProgressBar, "progress", 0.0f, internetProgressBar.getProgress());
        progressAnimator.setDuration(1000);
        progressAnimator.setStartDelay(300);
        progressAnimator.start();
    }

    private void setSeason(int season) {

        frontLayoutSeason = seasonFlip.findViewById(R.id.front_season);
        backLayoutSeason = seasonFlip.findViewById(R.id.back_season);
        seasonTv = backLayoutSeason.findViewById(R.id.back_text);
        seasonImg = frontLayoutSeason.findViewById(R.id.front_icon);
        changeCameraDistance(frontLayoutSeason, backLayoutSeason);

        seasonFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(frontLayoutSeason, backLayoutSeason, mIsBackVisible, 0);
            }
        });
        switch (season) {
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

    private void setTimeToGo(int time) {
        frontLayoutTime = findViewById(R.id.front_time);
        backLayouTime = findViewById(R.id.back_time);
        timeToGoTv = backLayouTime.findViewById(R.id.back_text);
        timeToGoImg = frontLayoutTime.findViewById(R.id.front_icon);
        changeCameraDistance(frontLayoutTime, backLayouTime);
        timeToGoFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(frontLayoutTime, backLayouTime, mIsBackVisible, 1);
            }
        });

        switch (time) {
            case 0:
                timeToGoTv.setText(getResources().getString(R.string.day_time));
                break;

            case 1:
                timeToGoTv.setText(getResources().getString(R.string.night_time));
                break;
        }
        timeToGoImg.setImageResource(R.drawable.ic_day_and_night);
    }

    private void setAge(String age) {
        frontLayoutAge = findViewById(R.id.front_ag);
        backLayoutAge = findViewById(R.id.back_age);
        ageTv1 = backLayoutAge.findViewById(R.id.back_text);
        ageTv2 = frontLayoutAge.findViewById(R.id.back_text);
        ageTv2.setTextSize(20);
        ageTv1.setTextSize(20);
        ageTv1.setText(age);
        ageTv2.setText(age);

        changeCameraDistance(frontLayoutAge, backLayoutAge);
        ageFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(frontLayoutAge, backLayoutAge, mIsBackVisible, 2);
            }
        });
    }

    private void setEstimatedTime(int estimatedTime) {
        frontLayoutEstimated = findViewById(R.id.front_estimated);
        backLayoutEstimated = findViewById(R.id.back_estimated);
        estimationTv = backLayoutEstimated.findViewById(R.id.back_text);
        estimationImg = frontLayoutEstimated.findViewById(R.id.front_icon);
        estimationTv.setText(estimatedTime + "Hrs");
        estimationImg.setImageResource(R.drawable.ic_stopwatch);

        changeCameraDistance(frontLayoutEstimated, backLayoutEstimated);
        estimationFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipCard(frontLayoutEstimated, backLayoutEstimated, mIsBackVisible, 3);
            }
        });
    }

    public void flipCard(View front, View back, boolean mIsBackVisible[], int position) {

        if (!mIsBackVisible[position]) {
            mSetRightOut.setTarget(front);
            mSetLeftIn.setTarget(back);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible[position] = true;
        } else {
            mSetRightOut.setTarget(back);
            mSetLeftIn.setTarget(front);
            mSetRightOut.start();
            mSetLeftIn.start();
            mIsBackVisible[position] = false;
        }
    }

    private void changeCameraDistance(View front, View back) {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        front.setCameraDistance(scale);
        back.setCameraDistance(scale);
    }

    private void loadAnimations() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.out_animation);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.in_animation);
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
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.plus :
                if(i!=99)
                i++;
                num_of_person.setText(i+"");
                break;
            case R.id.minus :
                if(i!=1)
                i--;
                num_of_person.setText(i+"");
                break;
        }
    }
}