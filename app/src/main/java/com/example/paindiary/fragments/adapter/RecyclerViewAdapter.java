package com.example.paindiary.fragments.adapter;

import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
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


    private int expandPosition = -1;

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
            final PainRecord painRecord = painRecords.get(position);

            //To expand the recycler view on click
            final boolean  isExpanded = position==expandPosition; // Default value set to -1
        //Showing and hiding details based on click
            viewHolder.binding.weather.setVisibility(isExpanded? View.VISIBLE:View.GONE);
            viewHolder.binding.upArrow.setVisibility(isExpanded? View.VISIBLE:View.GONE);
            viewHolder.binding.downArrow.setVisibility(isExpanded? View.GONE:View.VISIBLE);
            viewHolder.binding.parent.setActivated(isExpanded);
            //On click listener
            viewHolder.binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandPosition = isExpanded? -1:position;
                //TransitionManager.beginDelayedTransition();
                notifyDataSetChanged();
            }
        });


                viewHolder.binding.painIntenseVal.setText(Integer.toString(painRecord.getPainIntensity()));
                viewHolder.binding.painLocationVal.setText(painRecord.getPainLocation());
                viewHolder.binding.moodVal.setText(painRecord.getMood());
                viewHolder.binding.dateVal.setText(new SimpleDateFormat("dd/MM/yyyy").format(painRecord.getDate()));
                viewHolder.binding.stepsVal.setText(Integer.toString(painRecord.getStepsTaken()));
                viewHolder.binding.goalStepsValue.setText(Integer.toString(painRecord.getGoalSteps()));
                //Weather details
                viewHolder.binding.temperatureVal.setText(Double.toString(painRecord.getTemp()));
                viewHolder.binding.humidityVal.setText(Double.toString(painRecord.getHumidity()));
                viewHolder.binding.pressureVal.setText(Double.toString(painRecord.getPressure()));


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
