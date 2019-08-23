package com.example.seyaha;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;
    private TextView mTextView;
    public  String lan="null";
    NavigationView mNavigationView;

    //nav_header
    private CircleImageView mNavProfileAvatar;
    private TextView mNavDisplayName;
    private TextView mNavEmail;

    private FirebaseAuth mFirebaseAuth;
    private  FirebaseAuth.AuthStateListener mFirebaseAuthListner;

    int close=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                mNavProfileAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
                Picasso.get().load(user.imageURL).fit().into(mNavProfileAvatar);
                mNavDisplayName = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
                mNavDisplayName.setText(user.displayName+"");
                mNavEmail = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
                mNavEmail.setText(user.email+"");
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        toggle.syncState();


        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TourFragment()).commit();
            mNavigationView.setCheckedItem(R.id.nav_tour);
            changeToolbarTitle(getString(R.string.nav_tour));
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user==null)
                {
                    Intent intent=new Intent(MainActivity.this,SplashScreenActivity.class);
                    startActivity(intent);
                }
            }
        };


    }

    @Override
    public void onBackPressed()
    {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if(close%2==0)
        {
            mToast(getResources().getString(R.string.close),1);
        }
        else
        {
            moveTaskToBack(true);
        }
        close++;
    }
    private void mToast(String msg,int duration)
    {
        Toast.makeText(this,msg,duration).show();
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
                changeToolbarTitle(getString(R.string.nav_profile));
                break;
            case R.id.nav_tour:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TourFragment()).commit();
                changeToolbarTitle(getString(R.string.nav_tour));
                break;
            case R.id.nav_upcoming_event:
                Toast.makeText(this, "Comming Soon..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_help_feedback:
                Toast.makeText(this, "Comming Soon..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                AuthUI.getInstance().signOut(this);
                mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);
                break;
            case  R.id.english :
                selectyourlanguage(2);
                break;
            case R.id.arabic :
                selectyourlanguage(1);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeToolbarTitle(String str){
        mTextView = mToolbar.findViewById(R.id.toolbar_title);
        mTextView.setText(str);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);
    }
    public void selectyourlanguage(int i)
    {
        final String [] listitem={"Arabic","English"};
        if(i!=0)
        {
            switch (i)
            {
                case (1) :
                    {
                        setLocale("ar");
                        recreate();
                    }
                    break;
                case (2) : {
                    setLocale("en");
                    recreate();
                }
                break;
            }
        }

    }

    private void setLocale(String lang)
    {
        Locale locale =new Locale(lang);
        Locale.setDefault(locale);
        Configuration conf=new Configuration();
        conf.locale=locale;
        getBaseContext().getResources().updateConfiguration(conf,getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor pref=getSharedPreferences("settings",MODE_PRIVATE).edit();
        pref.putString("my_lang",lang);
        pref.apply();

    }

}
