package com.example.paindiary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.paindiary.databinding.ViewFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;

import java.util.List;

public class ViewFragment extends Fragment {

    private ViewFragmentBinding binding;

    private PainViewModel painViewModel;


    public ViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = ViewFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        painViewModel = new ViewModelProvider(this).get(PainViewModel.class);
        //painViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(PainViewModel.class);  // Tony

        painViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords) {
                String allPainRecords ="";
                for(PainRecord p : painRecords){
                        allPainRecords = allPainRecords + p.toString();
                }
                binding.resultText.setText(allPainRecords);
            }

        });
        return view;
    }

}