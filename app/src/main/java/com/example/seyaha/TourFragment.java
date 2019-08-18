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

public class TourFragment extends Fragment {

    ArrayList<Tour> tours = new ArrayList<Tour>();
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;
    FloatingActionButton addTourBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View mView = inflater.inflate(R.layout.fragment_tour,container,false);

        initalizeTours();
        TourAdapter mAdapter = new TourAdapter(tours);
        mRecyclerView = mView.findViewById(R.id.rv);
        mManger = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mManger);
        mRecyclerView.setAdapter(mAdapter);

        addTourBtn = mView.findViewById(R.id.add_tour_btn);
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreCallback() {
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

    protected void initalizeTours() {
        String[] tour1_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
                "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
                "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        Tour tour1 = new Tour(tour1_imgs,4.6,98,"3-Day", "Categories: Historical and Religion");

        String[] tour2_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
                "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
                "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        Tour tour2 = new Tour(tour2_imgs,4.7,125,"5-Day", "Categories: Adventure and Historical");

        String[] tour3_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
                "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
                "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        Tour tour3 = new Tour(tour3_imgs,2.7,12,"4-Day", "Categories: Historical, Adventure and Treatment.");

        tours.add(tour1);
        tours.add(tour2);
        tours.add(tour3);
    }

}
