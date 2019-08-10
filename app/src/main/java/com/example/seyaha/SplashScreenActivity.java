package com.example.seyaha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreenActivity extends AppCompatActivity {

    Bitmap[] imgs;
    public static   String WIFI ="0";
    public static String MOBDATA="0";
    int counter=0;
    SharedPreferences sharedPreferences;
    String check="notDownloaded";

    public String [] link={"https://firebasestorage.googleapis.com/v0/b/seyaha-2efa3.appspot.com/o/climbing.jpg?alt=media&token=6b188ad6-ece5-414e-beaa-8ce3ce66cb15",
            "https://firebasestorage.googleapis.com/v0/b/seyaha-2efa3.appspot.com/o/historical.jpg?alt=media&token=52549211-7e5e-410e-a6cd-cd51667f23a0",
            "https://firebasestorage.googleapis.com/v0/b/seyaha-2efa3.appspot.com/o/treatment.jpg?alt=media&token=d453f77e-58b7-4e62-9d10-014d604fe669",
            "https://firebasestorage.googleapis.com/v0/b/seyaha-2efa3.appspot.com/o/wonderland.jpg?alt=media&token=d02446db-381f-4531-8734-ec8bc386f7d8"};

    ProgressDialog progressDialog;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressDialog=new ProgressDialog(this);
        WIFI=checkInternetConnection();
       MOBDATA=isNetworkConnected();
        checkallpermissions();
    }

    private void checkimgs()
    {
        builder=new AlertDialog.Builder(this);
        imgs=new Bitmap[link.length];
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        check=sharedPreferences.getString("check","");
        Log.e("anas",check+"anas");

        if(!check.equalsIgnoreCase("Downloaded")&& (WIFI.equalsIgnoreCase("connected")|| MOBDATA.equalsIgnoreCase("connected")))
        {

            // editor.putString("check",);

            editor.putString("check","Downloaded");
            editor.commit();
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                progressDialog.setTitle(getResources().getString(R.string.loading));
                progressDialog.setMessage(getResources().getString(R.string.waiting));
                progressDialog.show();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        final Thread dothread = new Thread()
                        {
                            @Override
                            public void run() {
                                try {

                                    for (int i = 0; i < link.length; i++) {
                                        URL url = new URL(link[i]);
                                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                        SaveImage(bmp);
                                        if (i == link.length - 1)
                                        {
                                            progressDialog.dismiss();

                                        }
                                    }

                                } catch (MalformedURLException e)
                                {
                                    e.printStackTrace();
                                } catch (IOException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        };
                        dothread.start();
                    }
                });
                move_to_another_activity();
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }

        }
        else if(!check.equalsIgnoreCase("Downloaded") &&(!WIFI.equalsIgnoreCase("connected")&& !MOBDATA.equalsIgnoreCase("connected")))
        {

            builder.setTitle(getResources().getString(R.string.lost_internet));
            builder.setMessage(getResources().getString(R.string.check_connection));
            builder.setCancelable(false);
            builder.setPositiveButton(getResources().getString(R.string.retry), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    WIFI=checkInternetConnection();
                    MOBDATA=isNetworkConnected();
                    dialogInterface.cancel();
                    checkimgs();
                }
            });
            builder.show();

        }
        else
        {
            move_to_another_activity();
        }
        check=sharedPreferences.getString("check","");
    }


    public void move_to_another_activity()
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i=new Intent(SplashScreenActivity.this,TourActivity.class);
                startActivity(i);
            }
        },3000);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED )
        {
            checkimgs();

        }
        else
        {
            checkallpermissions();
        }

    }

    private void checkallpermissions() {

        if (ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.LOCATION_HARDWARE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(SplashScreenActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS}, 1);
        }
    }

    private String isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if( cm.getActiveNetworkInfo() != null)
        {
            return "connected";
        }
        else
        {
            return "failed";
        }
    }
    @SuppressLint("MissingPermission")
    private String checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected()) {

            return "connected";


        } else {
            return "failed";
        }
    }
    private void SaveImage(Bitmap finalBitmap) {
        Log.e("saved","start saving");
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = "Image-"+ counter++ +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ())
            file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Log.e("saved","save success "+counter);
        } catch (Exception e) {
            Log.e("notsaved",e+"");
            e.printStackTrace();
        }
    }
}
