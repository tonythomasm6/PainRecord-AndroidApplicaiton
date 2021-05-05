package com.example.paindiary.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.paindiary.databinding.ReportsFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;
import com.example.paindiary.fragments.adapter.ReportsAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ReportsFragment extends Fragment {
    private ReportsFragmentBinding binding;
    private PainViewModel painViewModel;
    public ReportsFragment(){}

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ReportsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        tabLayout.addTab(tabLayout.newTab().setText("Home"));
        tabLayout.addTab(tabLayout.newTab().setText("Sport"));
        tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ReportsAdapter adapter = new ReportsAdapter(this,getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });












        return view;

    }
}
