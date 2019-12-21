package com.example.seyaha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    View mView;
    SwipeRefreshLayout swipeRefreshLayout;
    TourAdapter mAdapter;
    static int i;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_recommended_tour, container, false);
        textView = mView.findViewById(R.id.test_tour);

        firebase_connection();

        swipeRefreshLayout = mView.findViewById(R.id.refresh_recommended);
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
            public void onCallback(final User user) {


                FirestoreQueries.getTours(new FirestoreQueries.FirestoreTourCallback() {
                    @Override
                    public void onCallback(List<Tour> tours) {
                        mTours = null;
                        mTours = tours;
                        ArrayList<String> userKeywords = (ArrayList<String>) user.intrests;
                        ArrayList<Tour> recommendedTours = new ArrayList<>();
                        for (int i = 0; i < userKeywords.size(); i++) {
                            for (int j = 0; j < mTours.size(); j++) {
                                if (mTours.get(j).tourKeywords.contains(userKeywords.get(i))) {
                                    if (!recommendedTours.contains(mTours.get(j)))
                                        recommendedTours.add(mTours.get(j));

                                }
                            }
                        }
                        if (recommendedTours.size() == 0) {
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setVisibility(View.INVISIBLE);

                        }
                        mAdapter = new TourAdapter(recommendedTours);
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
