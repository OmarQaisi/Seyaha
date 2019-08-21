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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TourFragment extends Fragment {

    List<Tour> mTours = new ArrayList<Tour>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;
    FloatingActionButton addTourBtn;
    View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_tour,container,false);

        FirestoreQueries.getTours(new FirestoreQueries.FirestoreTourCallback() {
            @Override
            public void onCallback(List<Tour> tours) {
                mTours = tours;
                TourAdapter mAdapter = new TourAdapter(mTours);
                mRecyclerView = mView.findViewById(R.id.rv);
                mManger = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mManger);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        addTourBtn = mView.findViewById(R.id.add_tour_btn);
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onCallback(User user) {
                if(user.isAdmin)
                    addTourBtn.show();
                else
                    addTourBtn.setVisibility(View.INVISIBLE);
            }
        });

        addTourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getActivity(),AddTourActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }

}
