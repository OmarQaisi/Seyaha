package com.example.seyaha;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ImageViewHolder> {
    Bitmap[] images;
    String [] placesname;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view);
        return imageViewHolder;
    }

    public  RecycleAdapter(Bitmap [] images,String[] placesname)
    {
        this.images=images;
        this.placesname=placesname;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder , int position)
    {
        Bitmap image_bitmap=images[position];
        holder.album.setImageBitmap(image_bitmap);
        String cat_name=placesname[position];
        holder.categorynames.setText(cat_name);
    }

    @Override
    public int getItemCount()
    {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder
    {
        ImageView album;
        TextView categorynames;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ImageViewHolder(View itemView)
        {
            super(itemView);
            album=itemView.findViewById(R.id.imgview);
            album.setClipToOutline(true);
            categorynames=itemView.findViewById(R.id.categorytext);
        }

    }
}
