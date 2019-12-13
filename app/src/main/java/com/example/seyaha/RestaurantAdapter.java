package com.example.seyaha;

import android.content.Context;
import android.content.Intent;
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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants;
    Context context;

    public RestaurantAdapter(List<Restaurant> restaurants, Context context) {
        this.restaurants = restaurants;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // relate viewHolder with it's layout
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.restaurant_item, parent, false);
        final RestaurantViewHolder viewHolder = new RestaurantViewHolder(view);

        // move to PlaceInfoActivity on viewHolder click.
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use Intent to send data.
                Intent i = new Intent(context, PlaceInfoActivity.class);
                int index = viewHolder.getAdapterPosition();
                i.putExtra("place_id", restaurants.get(index).getmPlaceId());
                i.putExtra("place_photo", restaurants.get(index).getmThumbnail());
                i.putExtra("place_name", restaurants.get(index).getmTitle());
                i.putExtra("place_lat", restaurants.get(index).getmLat());
                i.putExtra("place_lng", restaurants.get(index).getmLng());
                context.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {

        Restaurant restaurant = restaurants.get(position);

        holder.tvTitle.setText(restaurant.getmTitle());
        holder.tvHours.setText(restaurant.getmHours());

        // change the color of state(open or not).
        if (restaurant.getmHours().equals("open now")) {
            holder.tvHours.setTextColor(context.getResources().getColor(R.color.green_open));
        } else if (restaurant.getmHours().equals("close")) {
            holder.tvHours.setTextColor(context.getResources().getColor(R.color.red_close));
        }

        try {
            holder.ratingBar.setRating(Float.parseFloat(restaurant.getmRating()));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        String imgProtocol = restaurant.getmThumbnail();
        Glide.with(context)
                .load(imgProtocol)
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imgThumbnail);

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvHours;
        ImageView imgThumbnail;
        RatingBar ratingBar;
        CardView container;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            imgThumbnail = itemView.findViewById(R.id.restaurant_image);
            tvTitle = itemView.findViewById(R.id.restaurant_title);
            tvHours = itemView.findViewById(R.id.restaurant_opening_hours);
            ratingBar = itemView.findViewById(R.id.restaurant_rating);
            container = itemView.findViewById(R.id.restaurant_container);
        }
    }
}
