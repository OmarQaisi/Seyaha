package com.example.seyaha;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private List<Hotel> hotels;
    Context context;


    public HotelAdapter(List<Hotel> hotels, Context context) {
        this.hotels = hotels;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // relate viewHolder with it's layout
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.hotel_item, parent, false);
        final HotelViewHolder viewHolder = new HotelViewHolder(view);

        // move to PlaceInfoActivity on viewHolder click.
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use Intent to send data.
                Intent i = new Intent(context, PlaceInfoActivity.class);
                int index = viewHolder.getAdapterPosition();
                i.putExtra("place_id", hotels.get(index).getmPlaceId());
                i.putExtra("place_photo", hotels.get(index).getmThumbnail());
                i.putExtra("place_name", hotels.get(index).getmTitle());
                i.putExtra("place_lat", hotels.get(index).getmLat());
                i.putExtra("place_lng", hotels.get(index).getmLng());
                context.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {

        Hotel hotel = hotels.get(position);

        holder.tvTitle.setText(hotel.getmTitle());
        holder.tvHours.setText(hotel.getmHours());

        // change the color of the state(open or not).
        if (hotel.getmHours().equals("open now")) {
            holder.tvHours.setTextColor(context.getResources().getColor(R.color.green_open));
        } else if (hotel.getmHours().equals("close")) {
            holder.tvHours.setTextColor(context.getResources().getColor(R.color.red_close));
        } else {
            holder.tvHours.setTextColor(Color.BLACK);

        }

        try {
            holder.ratingBar.setRating(Float.parseFloat(hotel.getmRating()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        String imgProtocol = hotel.getmThumbnail();
        Glide.with(context)
                .load(imgProtocol)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imgThumbnail);

    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvHours;
        ImageView imgThumbnail;
        RatingBar ratingBar;
        CardView container;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.hotel_image);
            tvTitle = itemView.findViewById(R.id.hotel_title);
            tvHours = itemView.findViewById(R.id.hotel_opening_hours);
            ratingBar = itemView.findViewById(R.id.hotel_rating);
            container = itemView.findViewById(R.id.hotel_container);
        }

    }
}
