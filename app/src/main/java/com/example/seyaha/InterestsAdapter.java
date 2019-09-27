package com.example.seyaha;

import android.content.Context;

import android.os.Build;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class InterestsAdapter extends RecyclerView.Adapter<InterestsAdapter.ImageViewHolder> {
    private static final String TAG = "InterestsAdapter";
    Context context;
    String[] interests;
    String[] interests_ar;
    static List<String> interests_chosen;
    static HashSet<String> intrests_hashSet;
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

    public InterestsAdapter(Context context) {
        this.context = context;
        interests = context.getResources().getStringArray(R.array.interestsEN);
        interests_ar = context.getResources().getStringArray(R.array.interestsAR);
        interests_chosen = new ArrayList<String>();
        intrests_hashSet = new HashSet<>();
        counter = new int[interests.length];
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ImageViewHolder holder, final int position) {
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                intrests_hashSet.addAll(user.intrests);
                if (intrests_hashSet.size() != 0) {
                    Iterator<String> i = intrests_hashSet.iterator();
                    while (i.hasNext()) {
                        if (interests[position].equals(i.next())) {
                            holder.checker.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            holder.category.setText(interests_ar[position].trim());

        } else {
            holder.category.setText(interests[position].trim());
        }

        holder.checker.setVisibility(View.INVISIBLE);
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter[position] % 2 == 0) {
                    holder.checker.setVisibility(View.VISIBLE);
                    intrests_hashSet.add(interests[position]);
                } else {
                    holder.checker.setVisibility(View.INVISIBLE);
                    intrests_hashSet.remove(interests[position]);
                }
                counter[position]++;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (interests.length == 0) {
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
            category = itemView.findViewById(R.id.txt_fav);
            checker = itemView.findViewById(R.id.img_correct);
        }
    }

    public void clearInterests() {
        Arrays.fill(counter, 0);
        notifyDataSetChanged();
    }

}
