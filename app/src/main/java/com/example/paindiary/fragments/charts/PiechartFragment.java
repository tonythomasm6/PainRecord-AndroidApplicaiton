package com.example.paindiary.fragments.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.charts.CircularGauge;
import com.example.paindiary.databinding.AddFragmentBinding;
import com.example.paindiary.databinding.PiechartFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PiechartFragment extends Fragment {

    private PiechartFragmentBinding binding;
    private PainViewModel painViewModel;
    private HashMap<String,Integer> chartData = new HashMap<>();
    private FirebaseUser firebaseUser;
    private String userEmail;
    //Initializing count
    int backCount = 0,neckCount=0,headCount=0,kneesCount=0,hipsCount=0,
            abdomenCount=0,elbowsCount=0,shoulderCount=0,shinsCount=0,jawCount=0,facialCount =0;
    public PiechartFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = PiechartFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        painViewModel = new ViewModelProvider(this).get(PainViewModel.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = firebaseUser.getEmail();



        painViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {

                    @Override
                    public void onChanged(List<PainRecord> painRecords) {
                        if(painRecords!=null){
                            if(painRecords.size()>0){
                                for(PainRecord p : painRecords){
                                    if(p.getEmail().equals(userEmail)){
                                        if(p.getPainLocation().equals("Back"))
                                           backCount+=1;
                                        else if(p.getPainLocation().equals("Neck"))
                                            neckCount+=1;
                                        else if(p.getPainLocation().equals("Head"))
                                            headCount+=1;
                                        else if(p.getPainLocation().equals("Knees"))
                                            kneesCount+=1;
                                        else if(p.getPainLocation().equals("Hips"))
                                            hipsCount+=1;
                                        else if(p.getPainLocation().equals("Abdomen"))
                                            abdomenCount+=1;
                                        else if(p.getPainLocation().equals("Elbows"))
                                            elbowsCount+=1;
                                        else if(p.getPainLocation().equals("Shoulder"))
                                            shoulderCount+=1;
                                        else if(p.getPainLocation().equals("Shins"))
                                            shinsCount+=1;
                                        else if(p.getPainLocation().equals("Jaw"))
                                            jawCount+=1;
                                        else if(p.getPainLocation().equals("Facial"))
                                            facialCount+=1;
                                    }
                                }
                                drawChart();
                            }
                        }
                    }
                });





        return view;
    }

    public void drawChart(){
        Pie pie = AnyChart.pie();



        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Back", backCount));
        data.add(new ValueDataEntry("Neck", neckCount));
        data.add(new ValueDataEntry("Head", headCount));
        data.add(new ValueDataEntry("Knees", kneesCount));
        data.add(new ValueDataEntry("Hips", hipsCount));
        data.add(new ValueDataEntry("Abdomen", abdomenCount));
        data.add(new ValueDataEntry("Elbows", elbowsCount));
        data.add(new ValueDataEntry("Shoulder", shoulderCount));
        data.add(new ValueDataEntry("Shins", shinsCount));
        data.add(new ValueDataEntry("Jaw", jawCount));
        data.add(new ValueDataEntry("Facial", facialCount));

        AnyChartView anyChartView = binding.anyChartView;
        pie.data(data);
        anyChartView.setChart(pie);



    }
}
