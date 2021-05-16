package com.example.paindiary.fragments.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.paindiary.fragments.AddFragment;
import com.example.paindiary.fragments.MapsFragment;
import com.example.paindiary.fragments.charts.DonutChartFragment;
import com.example.paindiary.fragments.charts.LinechartFragment;
import com.example.paindiary.fragments.charts.PiechartFragment;
import com.example.paindiary.fragments.ReportsFragment;

//Class to display charts as different tabs
public class ReportsAdapter extends FragmentStatePagerAdapter {

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
                PiechartFragment piechartFragment = new PiechartFragment();
                return piechartFragment;
            case 1:
                DonutChartFragment donutFragment = new DonutChartFragment();
                return donutFragment;
            case 2:
               LinechartFragment lineFragment = new LinechartFragment();
               return lineFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    //Method added to enable refresh page everytime
    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
