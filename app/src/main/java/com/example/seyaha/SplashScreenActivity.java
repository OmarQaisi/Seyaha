package com.example.seyaha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {


    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;


    //firebase
    private FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mFirebaseAuthListner;
    private FirebaseUser user;
    private final  int RC_SIGN_IN=1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mFirebaseAuth=FirebaseAuth.getInstance();
        mFirebaseAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user=firebaseAuth.getCurrentUser();
                if(user==null)
                {
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.FacebookBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build()
                    );

                    AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                            .Builder(R.layout.activity_login)
                            .setGoogleButtonId(R.id.gmail_btn)
                            .setFacebookButtonId(R.id.facbook_btn)
                            .build();

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setIsSmartLockEnabled(false)
                                    .setTheme(R.style.AppThemeFirebaseAuth)
                                    .setAuthMethodPickerLayout(customLayout)
                                    .build(),
                            RC_SIGN_IN);
                }
                else
                {
                    checkallpermissions();
                }
            }
        };
    }

    private void loading()
    {
        // here if internet is connected
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent i=new Intent(SplashScreenActivity.this, TourActivity.class);
                startActivity(i);
            }
        },3000);
    }
    private void checkInternet()
    {
        if(SeyahaUtils.checkInternetConnectivity(this))
        {
            loading();
        }
        else
        {
            showSnackBar();
        }
    }
    private void init()
    {
        coordinatorLayout=findViewById(R.id.coordinator);
    }
    private void showSnackBar()
    {
        snackbar=Snackbar.make(coordinatorLayout,getResources().getString(R.string.lost_internet),Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        if (snackbar.isShown())
                        {
                            snackbar.dismiss();
                        }
                        checkInternet();
                    }
                });

        snackbar.show();
    }
    private void checkallpermissions()
    {

        if (ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.LOCATION_HARDWARE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(SplashScreenActivity.this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(SplashScreenActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS}, 1);
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(user!=null) {
            init();
            checkInternet();
        }
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);
    }


    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListner);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_OK)
        {
            Toast.makeText(this, "successful sign in", Toast.LENGTH_SHORT).show();
            checkallpermissions();
            init();
            checkInternet();
        }
        else if(resultCode==RESULT_CANCELED)
        {
            Toast.makeText(this, "sign in canceled", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


}

