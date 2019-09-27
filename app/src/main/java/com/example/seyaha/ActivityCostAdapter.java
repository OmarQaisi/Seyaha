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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ActivityCostAdapter extends ArrayAdapter<ActivityClass> {

    static List<ActivityClass> activities;
    static Map<Integer, Boolean> map;
    static Map<Integer, Integer> cost_map;
    static Map<Integer, Double> time_map;

    public ActivityCostAdapter(@NonNull Context context, List<ActivityClass> activities) {
        super(context, 0, activities);
        this.activities = activities;
        map = new HashMap<>();
        cost_map = new HashMap<>();
        time_map = new HashMap<>();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_cost_item, parent, false);
        }

        final CheckBox mCheckBox = convertView.findViewById(R.id.activity_cost_item_checkbox);
        mCheckBox.setChecked(DetailedActivity.prefs.getboolPrefs("cost_" + position, false));
        if (mCheckBox.isChecked()) {
            map.put(position, true);
            cost_map.put(position, activities.get(position).cost);
            time_map.put(position, activities.get(position).time);
        } else {
            cost_map.put(position, 0);
            map.put(position, false);
            time_map.put(position, 0.0);
        }

        final TextView mActivityPrice = convertView.findViewById(R.id.activity_cost_item_price);
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCheckBox.isChecked()) {
                    map.put(position, true);
                    cost_map.put(position, activities.get(position).cost);
                    time_map.put(position, activities.get(position).time);
                } else {
                    map.put(position, false);
                    cost_map.put(position, 0);
                    time_map.put(position, 0.0);
                }
            }
        });

        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            mCheckBox.setText(activities.get(position).nameAR);
            notifyDataSetChanged();
        } else {
            mCheckBox.setText(activities.get(position).nameEN);
        }

        mActivityPrice.setText(activities.get(position).cost + " JD");

        return convertView;
    }

    public static void SetCheckedActivities(int position, boolean Checked) {
        DetailedActivity.prefs.setboolPrefs("cost_" + position, Checked);
    }

}
