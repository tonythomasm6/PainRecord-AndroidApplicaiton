package com.example.paindiary.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.paindiary.databinding.AddFragmentBinding;
import com.example.paindiary.databinding.PietabFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;

import java.util.ArrayList;
import java.util.List;

public class PietabFragment extends Fragment {

    private PietabFragmentBinding binding;
    private PainViewModel painViewModel;
    private List<PainRecord> allRecords;
    public PietabFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = PietabFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();







        return view;
    }
}
