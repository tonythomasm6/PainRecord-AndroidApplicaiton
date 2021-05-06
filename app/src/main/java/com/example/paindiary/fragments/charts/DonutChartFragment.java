package com.example.paindiary.fragments.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.paindiary.databinding.DonutchartFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonutChartFragment extends Fragment {

    private DonutchartFragmentBinding binding;
    private PainViewModel painViewModel;
    private FirebaseUser firebaseUser;
    private String userEmail;

    private int stepsTaken = 0;
    private int goalSteps = 0;

    public DonutChartFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DonutchartFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        painViewModel = new ViewModelProvider(this).get(PainViewModel.class);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        userEmail = firebaseUser.getEmail();


        painViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {

            @Override
            public void onChanged(List<PainRecord> painRecords) {
                if(painRecords!=null){
                    if(painRecords.size()>0){
                        for(PainRecord p:painRecords){
                            if(p.getEmail().equals(userEmail) && p.getDate().toString().equals(getFormattedCurrentDate().toString())){
                                        stepsTaken = p.getStepsTaken();
                                        goalSteps = p.getGoalSteps();
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
        PieChart pie = binding.piechart;
        ArrayList steps = new ArrayList();

        steps.add(new PieEntry(stepsTaken, "Steps Taken"));
        steps.add(new PieEntry(goalSteps-stepsTaken, "Remaining Steps"));


        try {

            PieDataSet dataSet = new PieDataSet(steps, "");

            PieData data = new PieData(dataSet);
            data.setValueTextSize(12f);
            data.setValueTextColor(Color.BLACK);

            pie.getLegend().setEnabled(false);
            pie.setData(data);

            //dataSet.setDrawValues(true);
            //data.setValueFormatter(new PercentFormatter(pie));

            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pie.animateXY(5000, 5000);
        }
        catch(Exception e){
            String e1 = e.toString();
            String e2 = e1;
        }
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
}
