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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewFragment extends Fragment {

    private ViewFragmentBinding binding;

    private PainViewModel painViewModel;

    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseUser firebaseUser;



    public ViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = ViewFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        painViewModel = new ViewModelProvider(this).get(PainViewModel.class);

        painViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {
            @Override
            public void onChanged(List<PainRecord> painRecords)
            {if(painRecords!=null){
                if(painRecords.size()>0){
                    viewDataInRecycler(painRecords);
                }else{
                    binding.noData.setText("No records found !!");
                    binding.noData.setVisibility(View.VISIBLE);
                }
            }else{
                binding.noData.setText("No records found !!");
                binding.noData.setVisibility(View.VISIBLE);
            }

            }

        });

        return view;
    }

    //Recycler view method
    public void viewDataInRecycler(List<PainRecord> painRecords){

            ArrayList<PainRecord> userPainRecords = new ArrayList<>();
            for(PainRecord p:painRecords){
                if(p.getEmail().equals(firebaseUser.getEmail())){
                    userPainRecords.add(p);
                }
            }
            if(userPainRecords.size()>0) {
                binding.noData.setVisibility(View.GONE);
                adapter = new RecyclerViewAdapter(userPainRecords);
                //To add line separating between rows
                binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
                binding.recyclerView.setAdapter(adapter);
                layoutManager = new LinearLayoutManager(getActivity());
                binding.recyclerView.setLayoutManager(layoutManager);

            }else{
                binding.noData.setText("No records found !!");
                binding.noData.setVisibility(View.VISIBLE);
        }


    }

}