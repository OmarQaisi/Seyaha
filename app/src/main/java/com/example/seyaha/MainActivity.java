package com.example.seyaha;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;


    private Toolbar mToolbar;
    private TextView mTextView;

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

        Intent intent = getIntent();
        Bundle mBundle = intent.getBundleExtra("user");
        User mUser = (User) mBundle.getSerializable("user");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);

        mNavProfileAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
        Picasso.get().load(mUser.imageURL).fit().into(mNavProfileAvatar);
        mNavDisplayName = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
        mNavDisplayName.setText(mUser.displayName+"");
        mNavEmail = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
        mNavEmail.setText(mUser.email+"");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(Color.BLACK);
        toggle.syncState();


        if(savedInstanceState == null){
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
            //finish();
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

}
