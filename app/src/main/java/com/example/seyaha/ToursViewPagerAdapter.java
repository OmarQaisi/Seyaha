package com.example.seyaha;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ToursViewPagerAdapter extends FragmentPagerAdapter {
    Context context;

    public ToursViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TopRatedTourFragment();
            case 1:
                return new RecommendedTourFragment();
            default:
                return new TopRatedTourFragment();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.top_rated);
            case 1:
                return context.getString(R.string.recommended);

            default:
                return context.getString(R.string.top_rated);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

}
