package com.example.paindiary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paindiary.databinding.ReportsFragmentBinding;

public class ReportsFragment extends Fragment {
    private ReportsFragmentBinding binding;

    public ReportsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ReportsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;

    }
}
