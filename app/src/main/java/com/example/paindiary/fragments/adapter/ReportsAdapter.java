package com.example.paindiary.fragments.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.paindiary.fragments.AddFragment;
import com.example.paindiary.fragments.HomeFragment;
import com.example.paindiary.fragments.MapsFragment;
import com.example.paindiary.fragments.ReportsFragment;

public class ReportsAdapter extends FragmentPagerAdapter {

    private ReportsFragment myContext;
    int totalTabs;
    public ReportsAdapter(ReportsFragment context, FragmentManager fm, int totalTabs) {
        super(fm, totalTabs);
        this.totalTabs = totalTabs;
        this.myContext = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                AddFragment addFragment = new AddFragment();
                return addFragment;
            case 2:
               MapsFragment mapsFragment = new MapsFragment();
               return mapsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
