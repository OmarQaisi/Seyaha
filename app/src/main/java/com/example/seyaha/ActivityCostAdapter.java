package com.example.seyaha;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;


public class ActivityCostAdapter extends ArrayAdapter<ActivityClass> {

    List<ActivityClass> activities;
    static int  totalcost=0;
    public ActivityCostAdapter(@NonNull Context context, List<ActivityClass> activities)
    {
        super(context, 0,activities);
        this.activities = activities;


    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_cost_item, parent, false);
        }

        final CheckBox mCheckBox = convertView.findViewById(R.id.activity_cost_item_checkbox);

        final TextView mActivityPrice = convertView.findViewById(R.id.activity_cost_item_price);

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    totalcost+= activities.get(position).cost;
                    SetCheckedActivities(position,true);
                }
                else
                {
                    totalcost-=activities.get(position).cost;
                    SetCheckedActivities(position,false);
                }
            }
        });

        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            mCheckBox.setText(activities.get(position).nameAR);
            notifyDataSetChanged();
        } else {
            mCheckBox.setText(activities.get(position).nameEN);
        }

        mActivityPrice.setText(activities.get(position).cost+"JD");


        return convertView;
    }
    public void SetCheckedActivities(int position,boolean Checked)
    {
        DetailedActivity.prefs.setboolPrefs("cost_"+position,Checked);

    }

}
