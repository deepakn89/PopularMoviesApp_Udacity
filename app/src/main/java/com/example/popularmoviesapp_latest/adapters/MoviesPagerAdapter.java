package com.example.popularmoviesapp_latest.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.popularmoviesapp_latest.fragments.Favourite;
import com.example.popularmoviesapp_latest.fragments.Popular;
import com.example.popularmoviesapp_latest.fragments.TopRated;

public class MoviesPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "MoviesPagerAdapter";

    public MoviesPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1:
                return new TopRated();
            case 2:
                return new Favourite();
            case 0:
            default:
                return new Popular();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
