package com.example.seyaha;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class OverviewAdapter extends ArrayAdapter
{
    List<OverviewItem> mList=new ArrayList<>();
    Context context;

    TextView placeName,category,days;
    View verticalLine;
    ImageView placePic;

    public OverviewAdapter(@NonNull Context context, List<OverviewItem> list) {
        super(context, 0,list);
        mList=list;
        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View mView=convertView;

        if(mView==null)
        {
            mView= LayoutInflater.from(context).inflate(R.layout.overview_item,parent,false);
        }

        OverviewItem mItem=mList.get(position);


        placeName=mView.findViewById(R.id.place_name_overview);
        placeName.setText(mItem.placeName);


        category=mView.findViewById(R.id.category_tv_overview);
        category.setText(mItem.category);


        days=mView.findViewById(R.id.days_tv);
        int i=position+1;
        days.setText("Day "+i);

        verticalLine=mView.findViewById(R.id.verical_line_overview);
        ConstraintLayout layout=mView.findViewById(R.id.layout_overview);

        if(position%2==0)
        {
            verticalLine.setBackgroundColor(mView.getResources().getColor(R.color.light_blue));
            mView.setElevation(4);
        }
        else
        {
            verticalLine.setBackgroundColor(mView.getResources().getColor(R.color.dark_grey));
            layout.setBackgroundColor(mView.getResources().getColor(R.color.light_grey_shade));
        }

        placePic=mView.findViewById(R.id.place_pic_overview);
        placePic.setClipToOutline(true);


        return mView;
    }
}
