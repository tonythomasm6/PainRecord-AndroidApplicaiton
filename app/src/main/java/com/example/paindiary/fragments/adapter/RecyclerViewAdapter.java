package com.example.paindiary.fragments.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.paindiary.databinding.RvLayoutBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<PainRecord> painRecords;

    private FirebaseUser firebaseUser;

    public RecyclerViewAdapter(List<PainRecord> painRecords){
        this.painRecords = painRecords;
    }

    //This method creates a new view holder that is constructed with a new View,
    //inflated from a layout
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvLayoutBinding binding = RvLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    // this method binds the view holder created with data that will be displayed
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewHolder, int position) {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            final PainRecord painRecord = painRecords.get(position);
            if(painRecord.getEmail().equalsIgnoreCase(firebaseUser.getEmail())) {
                viewHolder.binding.painIntenseVal.setText(Integer.toString(painRecord.getPainIntensity()));
                viewHolder.binding.painLocationVal.setText(painRecord.getPainLocation());
                viewHolder.binding.moodVal.setText(painRecord.getMood());
                viewHolder.binding.dateVal.setText(new SimpleDateFormat("dd/MM/yyyy").format(painRecord.getDate()));
                viewHolder.binding.stepsVal.setText(Integer.toString(painRecord.getStepsTaken()));
            }
    }

    @Override
    public int getItemCount() {
        return painRecords.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder {
        private RvLayoutBinding binding;
        public ViewHolder(RvLayoutBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
