package com.example.seyaha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;

public class CategoriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SplashScreenActivity splash_screen;
    private RecyclerView.LayoutManager layoutManager;
    Bitmap[] imagess;
    RecycleAdapter adapter;
    String placesname[];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        placesname=getResources().getStringArray(R.array.categorynames);
        splash_screen=new SplashScreenActivity();
        recyclerView=findViewById(R.id.rv);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        imagess=new Bitmap[splash_screen.link.length];
        for(int i=0;i<splash_screen.link.length;i++)
        {
            imagess[i]=loadImage(i);
        }
        adapter=new RecycleAdapter(imagess,placesname);
        recyclerView.setAdapter(adapter);

    }

    private Bitmap loadImage(int i)
    {   Bitmap bitmap = null;
        try {
            File sdCard = Environment.getExternalStorageDirectory();

            File directory = new File(sdCard.getAbsolutePath() + "/saved_images");

            File file = new File(directory,  "Image-"+i+".jpg"); //or any other format supported

            FileInputStream streamIn = new FileInputStream(file);

            bitmap = BitmapFactory.decodeStream(streamIn); //This gets the image

            streamIn.close();
            return  bitmap;
        }catch (Exception e)
        {
            Toast.makeText(this,e+"",Toast.LENGTH_LONG).show();
        }
        return  bitmap;
    }
}
