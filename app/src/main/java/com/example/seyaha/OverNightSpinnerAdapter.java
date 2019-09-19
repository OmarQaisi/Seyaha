package com.example.seyaha;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;


public class OverNightSpinnerAdapter extends ArrayAdapter {

    List<Integer> overNightStay;
    Context context;
    public OverNightSpinnerAdapter(@NonNull Context context,int resource, List<Integer> overNightStay) {
        super(context,resource);
        this.context=context;
        this.overNightStay = overNightStay;
    }

    @Override
    public int getCount() {
        return overNightStay.size();
    }

    @Override
    public Object getItem(int position) {
        return overNightStay.get(position)+"JD";
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            if(convertView==null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.over_night_spinner_item, parent, false);
            }
            TextView overNightName = convertView.findViewById(R.id.over_night_name);

            TextView overNightPrice = convertView.findViewById(R.id.over_night_price);

            overNightPrice.setText(overNightStay.get(position)+"JD");

            switch (position) {
                case 0:
                    overNightName.setText(context.getResources().getText(R.string.cheap_cost));
                    break;
                case 1:
                    overNightName.setText(context.getResources().getText(R.string.average_cost));
                    break;
                case 2:
                    overNightName.setText(context.getResources().getText(R.string.expensive_cost));
                    break;
            }
        }
        catch (Exception e)
        {
            Log.e("motassem", "getView: ",e);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            convertView = LayoutInflater.from(context).inflate(R.layout.over_night_spinner_item, parent, false);
            TextView overNightName = convertView.findViewById(R.id.over_night_name);

            TextView overNightPrice = convertView.findViewById(R.id.over_night_price);

            overNightPrice.setText(overNightStay.get(position)+"JD");

            switch (position) {
                case 0:
                    overNightName.setText(context.getResources().getText(R.string.cheap_cost));
                    break;
                case 1:
                    overNightName.setText(context.getResources().getText(R.string.average_cost));
                    break;
                case 2:
                    overNightName.setText(context.getResources().getText(R.string.expensive_cost));
                    break;
            }
        }
        catch (Exception e)
        {
            Log.e("motassem", "getView: ",e);
        }

        return convertView;
    }
}
