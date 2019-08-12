package com.example.seyaha;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class OverviewAdapter extends RecyclerView.Adapter<OverviewAdapter.ViewHolder>
{

    public static Tour mTour;
    ColorDrawable colorDrawable;
    public OverviewAdapter(Tour tour)
    {
        mTour=tour;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.overview_item,parent,false);
       ViewHolder mViewHolder=new ViewHolder(mView);
        return mViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
          holder.placeName.setText(mTour.placeNames[position]);
          holder.category.setText(mTour.categories.get(mTour.placeNames[position]));
        colorDrawable =new ColorDrawable(Color.GRAY);
          Picasso.get().load(mTour.ImageURLs[position]).placeholder(colorDrawable).into(holder.placePic);
          holder.placePic.setClipToOutline(true);

          int i=position+1;
          holder.days.setText("Day "+i);

          if(position%2==0)
          {
              holder.virticalLine.setBackgroundColor(holder.itemView.getResources().getColor(R.color.light_blue));
              holder.itemView.setElevation(4);
          }
          else
          {
              holder.virticalLine.setBackgroundColor(holder.itemView.getResources().getColor(R.color.dark_grey));
              holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.light_grey_shade));

          }

          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  Tour.addClickEffect(v);
              }
          });


    }

    @Override
    public int getItemCount() {
        return mTour.placeNames.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView placeName,category,days;
        ImageView placePic;
        ImageButton voice;
        View virticalLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            placeName=itemView.findViewById(R.id.place_name_overview);
            category=itemView.findViewById(R.id.category_tv_overview);
            placePic=itemView.findViewById(R.id.place_pic_overview);
            voice=itemView.findViewById(R.id.arabic_voice_image_button);
            days=itemView.findViewById(R.id.days_tv);
            virticalLine=itemView.findViewById(R.id.verical_line_overview);
        }
    }


}
