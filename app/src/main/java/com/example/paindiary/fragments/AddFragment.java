package com.example.paindiary.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;
import com.example.paindiary.databinding.AddFragmentBinding;

public class AddFragment extends Fragment {

    private AddFragmentBinding addBinding;

    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addBinding = AddFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();

        //Method to populate pain locations
        populateSpinnerLocations();

        //For updating progress in seek bar
        int progress = addBinding.seekBar.getProgress();
        addBinding.painIntenseVal.setText(""+progress);
        addBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                addBinding.painIntenseVal.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
        return view;

    }

    public void  populateSpinnerLocations(){
        //Populate pain locations
        String[] locs ={"Back","Neck","Head","Knees","Hips","Abdomen","Elbows","Shoulder","Shins","Jaw","Facial"};
        final ArrayAdapter<String> locsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,locs);
        addBinding.location.setAdapter(locsAdapter);
    }
}
