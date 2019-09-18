package com.example.seyaha;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bitvale.switcher.SwitcherC;

import java.util.List;


public class ActivityCostAdapter extends ArrayAdapter<ActivityClass> {

    List<ActivityClass> activities;

    public ActivityCostAdapter(@NonNull Context context, List<ActivityClass> activities) {
        super(context, 0);
        this.activities = activities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_cost_item, parent, false);
        }

        final SwitcherC mCheckBox = convertView.findViewById(R.id.activity_cost_item_checkbox);
        TextView mActivityName = convertView.findViewById(R.id.activity_cost_item_text);
        TextView mActivityPrice = convertView.findViewById(R.id.activity_cost_item_price);

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {


                } else {


                }
            }
        });
        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            mActivityName.setText(activities.get(position).nameAR);

        } else {
            mActivityName.setText(activities.get(position).nameEN);
        }

        mActivityPrice.setText(activities.get(position).cost);


        return convertView;
    }
}
