package com.example.seyaha;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<TourAdapter.ImageViewHolder> {
    public static List<Tour> mTours;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_item,viewGroup,false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view);
        return imageViewHolder;
    }

    public TourAdapter(List<Tour> tours)
    {
        mTours = tours;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ImageViewHolder holder , int position)
    {

        Tour tour = mTours.get(position);

        holder.mTitle.setText(tour.title);

        holder.mDescription.setText(tour.desc);

        holder.mRating.setText(tour.rating+"");

        holder.mComments.setText(tour.comments+"");

        Picasso.get().load(tour.ImageURLs[0]).fit().into(holder.img1);
        holder.img1.setClipToOutline(true);

        Picasso.get().load(tour.ImageURLs[1]).fit().into(holder.img2);
        holder.img2.setClipToOutline(true);

        Picasso.get().load(tour.ImageURLs[2]).fit().into(holder.img3);
        holder.img3.setClipToOutline(true);
    }

    @Override
    public int getItemCount()
    {
        return mTours.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img1, img2, img3;
        TextView mTitle, mDescription, mRating, mComments;

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ImageViewHolder(View itemView)
        {
            super(itemView);

            mTitle = itemView.findViewById(R.id.title_txt);
            mDescription = itemView.findViewById(R.id.desc);
            mRating = itemView.findViewById(R.id.rate_num);
            mComments = itemView.findViewById(R.id.comment_num);
            img1 = itemView.findViewById(R.id.img1);
            img2 = itemView.findViewById(R.id.img2);
            img3 = itemView.findViewById(R.id.img3);
        }

    }
}
