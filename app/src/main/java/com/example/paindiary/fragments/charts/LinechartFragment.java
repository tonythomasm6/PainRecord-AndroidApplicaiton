package com.example.paindiary.fragments.charts;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.paindiary.databinding.LinechartFragmentBinding;
import com.example.paindiary.db.entity.PainRecord;
import com.example.paindiary.db.viewModel.PainViewModel;
import com.example.paindiary.fragments.datePicker.EndDateFragment;
import com.example.paindiary.fragments.datePicker.StartDateFragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class LinechartFragment extends Fragment {
    private LinechartFragmentBinding binding;
    private PainViewModel painViewModel;
    private List<PainRecord> allPainRecords;

    //Chart variables
    private ArrayList<Entry> yAxis1;// = new ArrayList<>();
    private ArrayList<Entry> yAxis2;// = new ArrayList<>();
    private ArrayList<String> xAxis;// = new ArrayList<>();

    public LinechartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = LinechartFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        painViewModel = new ViewModelProvider(this).get(PainViewModel.class);

        populateSpinnerWeather();

        painViewModel.getAllPainRecords().observe(getViewLifecycleOwner(), new Observer<List<PainRecord>>() {

            @Override
            public void onChanged(List<PainRecord> painRecords) {
                allPainRecords = painRecords;
            }
        });

        binding.startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new StartDateFragment();

                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        binding.endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EndDateFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        binding.generate.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                generateChart();
            }
        });

        binding.correlation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getCorrelation();
            }
        });
        return view;
    }


    //Method to populate the spinner with weather variables
    public void populateSpinnerWeather() {
        String[] weather = {"Temperature", "Humidity", "Pressure"};
        final ArrayAdapter<String> weatherAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, weather);
        binding.weatherVariable.setAdapter(weatherAdapter);
    }


    //Generate Chart
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void generateChart() {

        LineChart lineChart = binding.lineChart;

        selectedRecordsOfPeriod();
        //ArrayList<PainRecord> periodPainRecords = new ArrayList<>();
        String weatherType = binding.weatherVariable.getSelectedItem().toString();
        yAxis1 = new ArrayList<>();
        yAxis2 = new ArrayList<>();
        xAxis = new ArrayList<>();
        int i = 0;
        int j = 0;
       // int k = 0;
        for (PainRecord p : allPainRecords) {


            try {


                if (p.getDate().before(stringToDate(binding.endDate.getText().toString())) &&
                        p.getDate().after(stringToDate(binding.startDate.getText().toString()))) {

                    yAxis1.add(new Entry(i, p.getPainIntensity()));

                    //xAxis.add(dateToString(p.getDate()));
                    xAxis.add((Integer.toString(i+1)));
                    i++;

                    //yaxis 2
                    if (weatherType.equalsIgnoreCase("Temperature")) {
                        yAxis2.add(new Entry(j, (float) p.getTemp()));
                        j++;
                    } else if (weatherType.equalsIgnoreCase("Humidity")) {
                        yAxis2.add(new Entry(j, (float) p.getHumidity()));
                        j++;
                    } else if (weatherType.equalsIgnoreCase("Pressure")) {
                        yAxis2.add(new Entry(j, (float) p.getPressure()));
                        j++;
                    }
                }

            } catch (Exception e) {
                String ex = e.toString();
                String e2 = ex;
            }


        }

        //Plotting chart
        LineDataSet set_1 = new LineDataSet(yAxis1, "Pain Level");
        LineDataSet set_2 = new LineDataSet(yAxis2, weatherType);
        set_2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        LineData data = new LineData(set_1, set_2);
        set_1.setColor(ColorTemplate.rgb("#FF0000"));
        set_1.setCircleColor(ColorTemplate.rgb("#FF0000"));

        set_2.setCircleColor(ColorTemplate.rgb("#0000FF"));
        set_2.setColor(ColorTemplate.rgb("#0000FF"));


        lineChart.getAxisLeft().setAxisMinimum(0f);

        lineChart.getAxisLeft().setAxisMaximum(10.0f);

        lineChart.getAxisRight().setLabelCount(5, true);
        lineChart.getAxisRight().setDrawGridLines(false);

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setLabelCount(1, true);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxis));

        //calculating date different
        long difference = stringToDate(binding.endDate.getText().toString()).getTime() -
                stringToDate(binding.endDate.getText().toString()).getTime();
        int days = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);

        lineChart.getXAxis().setLabelCount(i, true);
        lineChart.setData(data);
        lineChart.setVisibility(View.VISIBLE);


    }


    public void selectedRecordsOfPeriod() {
        for (PainRecord p : allPainRecords) {

        }
    }

    public Date stringToDate(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.parse(date);
        } catch (Exception e) {

        }
        return null;
    }

    public String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM");
        String dateStr = formatter.format(date);
        return dateStr;
    }

    public void getCorrelation() {
        double data[][] = new double[yAxis1.size()][2];
        for (int i = 0; i < yAxis1.size(); i++) {
            data[i][0] = yAxis1.get(i).getY();
            data[i][1] = yAxis2.get(i).getY();
        }
try {
    // create a realmatrix
    RealMatrix m = MatrixUtils.createRealMatrix(data);

    // measure all correlation test: x-x, x-y, y-x, y-x
    for (int i = 0; i < m.getColumnDimension(); i++)
        for (int j = 0; j < m.getColumnDimension(); j++) {
            PearsonsCorrelation pc = new PearsonsCorrelation();
            double cor = pc.correlation(m.getColumn(i), m.getColumn(j));
            System.out.println(i + "," + j + "=[" + String.format(".%2f", cor) + "," + "]");
        }

    // correlation test (another method): x-y
    PearsonsCorrelation pc = new PearsonsCorrelation(m);
    RealMatrix corM = pc.getCorrelationMatrix();

    // significant test of the correlation coefficient (p-value)
    RealMatrix pM = pc.getCorrelationPValues();
    binding.correlationText.setText("p value:" + pM.getEntry(0, 1) + "\n" + " correlation: " + corM.getEntry(0, 1));
}
catch(Exception e){
    e.printStackTrace();
}
    }


}
