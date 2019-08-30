package com.example.seyaha;


import android.content.Context;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class fav_adapter extends RecyclerView.Adapter<fav_adapter.ImageViewHolder> {
    Context context;
    String [] interests;
    String [] interests_ar;
   static List <String> interests_chosen;
    FirebaseFirestore db;
    int counter[];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.interests_cat, viewGroup, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(view);


        return imageViewHolder;
    }

    public fav_adapter(Context context)
    {
        this.context=context;
        interests = context.getResources().getStringArray(R.array.interestsEN);
        interests_ar=context.getResources().getStringArray(R.array.interestsAR);
        interests_chosen=new ArrayList <String>();
        counter=new int[interests.length];
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position)
    {
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                Log.d("anastt", "onCallback: "+user.displayName+" "+user.intrests.toString());
                interests_chosen=user.intrests;
                if(interests_chosen.size()!=0)
                {
                    Log.d("anasee", "onBindViewHolder: ");
                   for(int i=0;i<interests_chosen.size();i++)
                   {
                       if(interests[position].equals(interests_chosen.get(i)))
                       {
                           holder.checker.setVisibility(View.VISIBLE);
                       }
                   }
                }
            }
        });
        if(SplashScreenActivity.lan.equalsIgnoreCase("ar"))
        {
            holder.category.setText(interests_ar[position].toString().trim());

        }
        else {
            holder.category.setText(interests[position].toString().trim());
        }
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(counter[position]%2==0)
                {
                    holder.checker.setVisibility(View.VISIBLE);
                    interests_chosen.add(interests[position].toString());
                }
                else
                {
                    holder.checker.setVisibility(View.INVISIBLE);
                    interests_chosen.remove(interests[position].toString());
                }
                counter[position]++;
            }
        });

    }
    @Override
    public int getItemCount() {
        if(interests.length==0)
        {
            return 1;
        }
        return interests.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        CustomTextView category;
        ImageView checker;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ImageViewHolder(View itemView) {
            super(itemView);
          category=itemView.findViewById(R.id.txt_fav);
          checker=itemView.findViewById(R.id.img_correct);

        }

    }


}
