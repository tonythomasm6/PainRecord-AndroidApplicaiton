package com.example.paindiary.fragments;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.paindiary.R;
import com.example.paindiary.databinding.AddFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;
import com.example.paindiary.db.viewModel.WeatherViewModel;
import com.example.paindiary.retrofit.RetrofitClient;
import com.example.paindiary.retrofit.RetrofitInterface;
import com.example.paindiary.retrofit.SearchResponse;
import com.example.paindiary.retrofit.Weather;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFragment extends Fragment {

    private AddFragmentBinding addBinding;
    private FirebaseUser firebaseUser;
    private PainViewModel painViewModel;
    private String userEmail;
    private PainRecord todayRecord = null;
    private RetrofitInterface retrofitInterface; // Weather

    double tempCelsius = 0;
    double hum = 0;
    double pressure = 0;

    public AddFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addBinding = AddFragmentBinding.inflate(inflater, container, false);
        View view = addBinding.getRoot();
        retrofitInterface = RetrofitClient.getRetrofitService();//Weather

        loadWeatherInfo();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = firebaseUser.getEmail(); // Getting logged in user Email

        painViewModel = new ViewModelProvider(this).get(PainViewModel.class);
       // enterSampleTestData(); /*Sample data for testing purpose*/
        //Method to populate pain locations in spinner
        populateSpinnerLocations();

        loadDataOfToday(); // Fetching any data if record present already for today
        //For updating progress in seek bar
        int progress = addBinding.seekBar.getProgress();
        addBinding.painIntenseVal.setText(""+progress);

        addBinding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                addBinding.painIntenseVal.setText(""+progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
             @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });


        //Delete all button
        addBinding.deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                painViewModel.deleteALl();
                Toast.makeText(getActivity(), "All records deleted !!", Toast.LENGTH_LONG).show();
            }
        });


        //Save button click
        addBinding.saveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                PainRecord painRecord = fetchFormData(); // Method to fetch entered form data
                if(painRecord !=null) {
                    painViewModel.insert(painRecord);  //Inserting into database
                   // addBinding.viewResult.setText(painRecord.toString()); // For debug purpose
                    Toast toast = Toast.makeText(getActivity(), "Record saved successfully", Toast.LENGTH_LONG);
                    toast.show();
                    addBinding.saveButton.setEnabled(false);
                    addBinding.editButton.setEnabled(true);
                }else{
                    Toast toast = Toast.makeText(getActivity(), "Saving failed !!!", Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }
            }
        });


        //Edit button functionality
        addBinding.editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editData();
            }
        });
        return view;

    }

    //Method to populate the spinner with pain locations.
    public void  populateSpinnerLocations(){
        String[] locs ={"Back","Neck","Head","Knees","Hips","Abdomen","Elbows",
                "Shoulder","Shins","Jaw","Facial"};
        final ArrayAdapter<String> locsAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item,locs);
        addBinding.location.setAdapter(locsAdapter);
    }


    //Method to fetch the entered form data and also validations
    public PainRecord fetchFormData() {
        //Fetching radio button value for mood.
        PainRecord painRecord = null;
        try {
            RadioGroup radioGroup = addBinding.locRadio;
            int radioId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = getActivity().findViewById(radioId);
            String moodData = radioButton.getText().toString();

            int painIntensity = addBinding.seekBar.getProgress();
            String painLoc = addBinding.location.getSelectedItem().toString();
            String stepsTaken = addBinding.stepsTakenText.getText().toString();
            String goalSteps = addBinding.goalStepsText.getText().toString();

            if(stepsTaken.isEmpty()){ // Validation for if steps taken is empty
                addBinding.stepsTakenText.setError("Please enter a valid value");
            }
            else{
                int steps = Integer.parseInt(stepsTaken);
                int goal = Integer.parseInt(goalSteps);
                Date date = getFormattedCurrentDate();
                //Getting formatted current date and adding it to the Entity.
                painRecord = new PainRecord(painIntensity, painLoc, moodData, steps,goal, userEmail,date,tempCelsius,pressure,hum);
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Exception : Saving failed !!!", Toast.LENGTH_LONG).show();
        }
        return painRecord;
    }

    //To get the today's date without time in Date format
    public Date getFormattedCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = formatter.format(new Date());
        Date date = new Date();
       try {
           date = formatter.parse(dateStr);
       }catch(Exception e){

       }
        return date;
    }


    //Loading record if entered for the day
    public void loadDataOfToday(){
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                CompletableFuture<List<PainRecord>> painRecordCompletableFuture = painViewModel.findTodayRecord(userEmail,getFormattedCurrentDate());
                painRecordCompletableFuture.thenApply(painRecords -> {
                    if(painRecords!=null){
                        if(painRecords.size()>0){
                            todayRecord = painRecords.get(0);
                            addBinding.saveButton.setEnabled(false);
                            addBinding.editButton.setEnabled(true);
                        }else{
                            addBinding.saveButton.setEnabled(true);
                            addBinding.editButton.setEnabled(false);
                        }

                    }else{
                        addBinding.viewResult.setText("No record found for today");
                        addBinding.saveButton.setEnabled(true);
                        addBinding.editButton.setEnabled(false);
                    }return painRecords;
                });
            }
        }catch(Exception e){
            String ex = e.toString();
            addBinding.viewResult.setText("System error");
        }
    }

    //Method called on Edit button: To edit today's data
    public void editData(){
        PainRecord painRecordForm = fetchFormData(); // Getting details entered for today

        painRecordForm.setId(todayRecord.getId());   // Updating the record with the id of current data
        painViewModel.update(painRecordForm);
        Toast toast = Toast.makeText(getActivity(), "Record updated ", Toast.LENGTH_LONG);
        toast.show();
    }



    /**Data for testing purpose**/
   public void enterSampleTestData(){

       SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String[] moods = {"Very Low","Good", "Very Good", "Low", "Very Low", "Good","Average","Average","Very Good","Low" };
        int[] painIntensities = {1,4,3,2,5,8,9,4,5,10};
        String[] painLocs = {"Back","Knees","Hips","Back","Shoulder","Abdomen","Elbows","Jaw","Shins","Head"};
        int[] steps = {500,10000,8000,56666,10000,50000,8000,4000,8000,1000};
        String[] dates = {"20/1/2021","10/2/2021","12/3/2021","28/3/2021","5/2/2021","20/1/2021","8/3/2021","18/2/2021","22/1/2021","2/1/2021"};
try{
        for(int i=0;i<10;i++){
            //painViewModel.insert(new PainRecord(painIntensities[i], painLocs[i], moods[i], steps[i],userEmail,formatter.parse(dates[i])));
        }

}catch(Exception e){
String ex = e.toString();
}
    }

    //Hitting api to get weather
    public void loadWeatherInfo(){
        WeatherViewModel model = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        model.getWeather().observe(getViewLifecycleOwner(), new Observer<HashMap<String, Double>>() {
            @Override
            public void onChanged(HashMap<String, Double> stringDoubleHashMap) {
                tempCelsius = stringDoubleHashMap.get("temp");
                hum = stringDoubleHashMap.get("humidity");
                pressure = stringDoubleHashMap.get("pressure");

            }
        });
        }

}
