package com.example.seyaha;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class OverviewActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);


        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView tv = findViewById(R.id.toolbar_title);
        tv.setText("Overview");


        OverviewAdapter adapter=new OverviewAdapter(initilizeData());

        mRecyclerView=findViewById(R.id.overview_list_view);
        mManger=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mManger);
        mRecyclerView.setAdapter(adapter);

}
    private Tour initilizeData()
    {
        String[] tour_imgs = {"https://handluggageonly.co.uk/wp-content/uploads/2017/11/11-Absolutely-Beautiful-Places-You-Have-To-Visit-In-Jordan-003.jpg",
                "https://cdn.theculturetrip.com/images/56-3950403-14421441718c6fa7f1da5148fcba9149806c00cd2a.jpg",
                "https://lonelyplanetimages.imgix.net/mastheads/GettyImages-165047390_high%20.jpg?sharp=10&vib=20&w=1200"};

        String[] mPlaceNames={"petra","ajloun","Dead sea"};

        Map<String,String> mMap=new HashMap<>();
        mMap.put(mPlaceNames[0],"Historical");
        mMap.put(mPlaceNames[1],"Realign");
        mMap.put(mPlaceNames[2],"Treatment");

        Tour mTour=new Tour(tour_imgs,mMap,mPlaceNames);

       return mTour;

    }


}
