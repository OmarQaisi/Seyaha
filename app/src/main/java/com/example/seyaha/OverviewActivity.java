package com.example.seyaha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        ArrayList<OverviewItem> mList=new ArrayList<>();
        mList.add(new OverviewItem("Aqaba","Treatment",null));
        mList.add(new OverviewItem("Petra","Adventure",null));
        mList.add(new OverviewItem("WadiRum","Religion",null));
        mList.add(new OverviewItem("Jerash","Treatment",null));
        mList.add(new OverviewItem("Down Town","Historical",null));
        mList.add(new OverviewItem("Ajloun","Historical",null));

        OverviewAdapter adapter=new OverviewAdapter(this,mList);

        ListView mListView=findViewById(R.id.overview_list_view);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addClickEffect(view);
            }
        });
    }

    private void addClickEffect(View view)
    {
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(500);
        view.startAnimation(animation1);
    }
}
