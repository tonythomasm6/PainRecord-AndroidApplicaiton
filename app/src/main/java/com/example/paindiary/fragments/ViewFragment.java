package com.example.paindiary.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paindiary.databinding.ViewFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;
import com.example.paindiary.fragments.adapter.RecyclerViewAdapter;

import java.util.List;

public class ViewFragment extends Fragment {

    private ViewFragmentBinding binding;

    private PainViewModel painViewModel;

    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;



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
            public void onChanged(List<PainRecord> painRecords)
            {if(painRecords!=null){
                if(painRecords.size()>0){
                    viewDataInRecycler(painRecords);
                }else{
                    binding.noData.setText("No records found !!");
                    binding.noData.setVisibility(View.GONE);
                }
            }else{
                binding.noData.setText("No records found !!");
                binding.noData.setVisibility(View.GONE);
            }

            }

        });




        return view;
    }

    //Recycler view method
    public void viewDataInRecycler(List<PainRecord> painRecords){
        if(painRecords.size() == 0){

        }else {
            binding.noData.setVisibility(View.VISIBLE);
            adapter = new RecyclerViewAdapter(painRecords);
            //To add line separating between rows
            binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
            binding.recyclerView.setAdapter(adapter);

            layoutManager = new LinearLayoutManager(getActivity());
            binding.recyclerView.setLayoutManager(layoutManager);
        }


    }

}