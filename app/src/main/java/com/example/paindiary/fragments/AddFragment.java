package com.example.paindiary.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;

import com.example.paindiary.R;
import com.example.paindiary.databinding.AddFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AddFragment extends Fragment {

    private AddFragmentBinding addBinding;
    private FirebaseUser firebaseUser;
    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addBinding = AddFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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

        //Save button click
        addBinding.saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Fetching radio button value for mood.
                RadioGroup radioGroup = addBinding.locRadio;
                int radioId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton =getActivity().findViewById(radioId);
                String moodData = radioButton.getText().toString();

                int painIntensity = addBinding.seekBar.getProgress();

                String painLoc = addBinding.location.getSelectedItem().toString();

                String stepsTaken = addBinding.stepsTakenText.getText().toString();

                addBinding.viewResult.setText("Intensity :"+painIntensity +"\n location :"+painLoc+"\nmood :"+moodData+"\n stepsTaken :"+stepsTaken);


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
