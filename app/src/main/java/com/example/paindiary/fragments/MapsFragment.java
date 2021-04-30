package com.example.paindiary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.paindiary.databinding.AddFragmentBinding;
import com.example.paindiary.databinding.MapsFragmentBinding;

public class MapsFragment  extends Fragment {

    private MapsFragmentBinding binding;

    public MapsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = MapsFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        return view;

    }
}
