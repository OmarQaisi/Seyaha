package com.example.seyaha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TourFragment extends Fragment {

    List<Tour> mTours = new ArrayList<Tour>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;
    FloatingActionButton addTourBtn;
    ViewPager mViewPager;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_tour,container,false);

        TabLayout tabLayout=mView.findViewById(R.id.tablayout);
         mViewPager=mView.findViewById(R.id.tour_view_pager);
        tabLayout.addTab(tabLayout.newTab().setText("Recommend"));
        tabLayout.addTab(tabLayout.newTab().setText("Top rated"));
        ToursViewPagerAdapter adapter=new ToursViewPagerAdapter(getActivity(),getFragmentManager());
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        return mView;
    }

}
