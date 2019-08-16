package com.example.seyaha;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class viewPagerAdapter extends PagerAdapter
{
private List<viewPagerModel>models;
private LayoutInflater layoutInflater;
private Context context;

public viewPagerAdapter(List<viewPagerModel>models,Context context)
{
    this.models=models;
    this.context=context;
}
    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
    layoutInflater=LayoutInflater.from(context);
    View view=layoutInflater.inflate(R.layout.viewpager_item,container,false);
        ImageView imageView;
        imageView=view.findViewById(R.id.viewPager_img);
        imageView.setImageResource(models.get(position).getImage());
        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
}
}
