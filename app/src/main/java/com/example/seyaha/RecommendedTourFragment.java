package com.example.seyaha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RecommendedTourFragment extends Fragment {

    List<Tour> mTours = new ArrayList<Tour>();
    RecyclerView mRecyclerViewRecommended;
    RecyclerView.LayoutManager mManger;
    FloatingActionButton addTourBtn;
    View mView;
    SwipeRefreshLayout swipeRefreshLayout;
    TourAdapter mAdapter;
    HashSet<Tour> tourshashSet;
    ArrayList<Tour> recommendedTours = new ArrayList<>();
    static int i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_recommended_tour,container,false);


        firebase_connection();

        swipeRefreshLayout =mView.findViewById(R.id.refresh_recommended);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                firebase_connection();
            }
        });

        return mView;
    }

    private void firebase_connection() {



        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {


                FirestoreQueries.getTours(new FirestoreQueries.FirestoreTourCallback() {
                    @Override
                    public void onCallback(List<Tour> tours) {
                        mTours=null;
                        mTours = tours;
                        mTours.remove(tours.get(0));
                        mAdapter = new TourAdapter(mTours);
                        mManger = new LinearLayoutManager(mView.getContext());
                        mRecyclerViewRecommended = mView.findViewById(R.id.rv_recommended);
                        mRecyclerViewRecommended.setLayoutManager(mManger);
                        mRecyclerViewRecommended.setAdapter(mAdapter);
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });


            }
        });


    }



}
