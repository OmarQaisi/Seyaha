package com.example.seyaha;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.com.felix.horizontalbargraph.HorizontalBar;
import br.com.felix.horizontalbargraph.model.BarItem;

public class DetailedActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ViewPager viewPager;
    List <viewPagerModel> models;
    Tour tour;
    viewPagerAdapter adapter;

    Integer []colors=null;
    ArgbEvaluator argbEvaluator=new ArgbEvaluator();

    ImageButton seasonImg,timeToGoImg,estimationImg;
    TextView seasonTv,timeToGoTv,ageTv,estimationTv,costTv,tempTv,airQualityTv,internetTv;
    RoundCornerProgressBar costProgressBar,tempProgressBar,airQualityProgressBar,internetProgressBar;



    int seasonImgResource,timeToGoImgResource,estimationImgResource;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        run_viewPager();

        costProgressBar=findViewById(R.id.cost_progress);
        tempProgressBar=findViewById(R.id.temp_progress);
        airQualityProgressBar=findViewById(R.id.air_quality_progress);
        internetProgressBar=findViewById(R.id.internet_progress);
        costTv=findViewById(R.id.cost_tv);
        tempTv=findViewById(R.id.temp_tv);
        airQualityTv=findViewById(R.id.air_quality_tv);
        internetTv=findViewById(R.id.internet_tv);

       createCostProgress("150");
       createTempProgress("22");


        seasonImg=findViewById(R.id.prefered_season_image_button);
        seasonTv=findViewById(R.id.prefered_season_text_view);
        seasonImg.setTag(seasonTv.getText()+"");
        seasonImgResource=R.drawable.ic_summer;

        timeToGoImg=findViewById(R.id.prefered_time_to_go);
        timeToGoTv=findViewById(R.id.prefered_time_to_go_tv);
        timeToGoImg.setTag(timeToGoTv.getText()+"");
        timeToGoImgResource=R.drawable.ic_day;

        estimationImg=findViewById(R.id.prefered_average_time_img);
        estimationTv=findViewById(R.id.prefered_average_time_tv);
        estimationImg.setTag(estimationTv.getText()+"");
        estimationImgResource=R.drawable.ic_sand_clock;
        seasonImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(seasonImg.getTag()!="none")
                {
                    seasonImg.setImageResource(android.R.color.transparent);
                    seasonImg.setTag("none");
                    seasonTv.setVisibility(View.VISIBLE);
                }
                else
                {
                        seasonTv.setVisibility(View.INVISIBLE);
                        seasonImg.setTag(seasonTv.getText()+"");
                        seasonImg.setImageResource(seasonImgResource);
                }
            }
        });

        timeToGoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             if(timeToGoImg.getTag()!="none")
             {
                 timeToGoImg.setImageResource(android.R.color.transparent);
                 timeToGoTv.setVisibility(View.VISIBLE);
                 timeToGoImg.setTag("none");
             }
             else
             {
                 timeToGoImg.setImageResource(timeToGoImgResource);
                 timeToGoTv.setVisibility(View.INVISIBLE);
                 timeToGoImg.setTag(timeToGoTv.getText()+"");
             }
            }
        });

        estimationImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(estimationImg.getTag()!="none")
                {
                    estimationImg.setImageResource(android.R.color.transparent);
                    estimationTv.setVisibility(View.VISIBLE);
                    estimationImg.setTag("none");
                }
                else
                {
                    estimationImg.setImageResource(estimationImgResource);
                    estimationTv.setVisibility(View.INVISIBLE);
                    estimationImg.setTag(estimationTv.getText()+"");
                }
            }
        });

    }

    private void run_viewPager() {
        String desc = "Observe the comments made about you by your parents, friends, teachers or you may directly ask them. Note these points on a paper and try to make a sample of description";
        models = new ArrayList <>();
        models.add(new viewPagerModel(R.drawable.wadi_rum));
        models.add(new viewPagerModel(R.drawable.nav_drawer_background));
        models.add(new viewPagerModel(R.drawable.profile_pic));

        adapter = new viewPagerAdapter(models, this);
        viewPager = findViewById(R.id.ViewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(100, 0, 100, 0);
        Integer[] colorsTemp = {getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.light_grey), getResources().getColor(R.color.dark_grey)};
        colors = colorsTemp;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adapter.getCount() - 1) && position < (colors.length) - 1) {
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(31.9539, 35.9106);
        LatLng bau = new LatLng(32.0250, 35.7167);
        MarkerOptions my_own_marker = new MarkerOptions().position(bau).title("My Nightmare");
        my_own_marker.icon((getBitmapDescriptor(R.drawable.ic_gps)));
        mMap.addMarker(my_own_marker);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("third circle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bau, 16.0f));
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

    private void createCostProgress(String cost)
    {

        int result=Integer.parseInt(cost);

        if(result<=100)
        {
            costProgressBar.setProgress(100);
            costProgressBar.setProgressColor(Color.GREEN);
            costTv.setText("$"+cost);

        }
        else if(result>100 && result<=250)
        {
            tempProgressBar.setProgress(60);
            tempTv.setText("$"+cost);
            tempProgressBar.setProgressColor(Color.rgb(255,165,0));
        }
        else
        {
            costProgressBar.setProgress(30);
            costProgressBar.setProgressColor(Color.RED);
            costTv.setText("$"+cost);
        }

    }

    private void createTempProgress(String temp)
    {
        int result=Integer.parseInt(temp);
        if(result<=10)
        {
            tempProgressBar.setProgress(30);
            tempProgressBar.setProgressColor(Color.RED);
            tempTv.setText(getResources().getString(R.string.cold)+temp+"\u2103");

        }
        else  if(result>10 && result<=18)
        {
            tempProgressBar.setProgress(60);
            tempTv.setText(getResources().getString(R.string.normal)+temp+"\u2103");
            tempProgressBar.setProgressColor(Color.rgb(255,165,0));
        }
        else if(result>18 && result<=30)
        {
            tempProgressBar.setProgress(100);
            tempProgressBar.setProgressColor(Color.GREEN);
            tempTv.setText(getResources().getString(R.string.perfect)+temp+"\u2103");
        }
        else
        {
            tempProgressBar.setProgress(30);
            tempProgressBar.setProgressColor(Color.RED);
            tempTv.setText(getResources().getString(R.string.hot)+temp+"\u2103");
        }

    }


}