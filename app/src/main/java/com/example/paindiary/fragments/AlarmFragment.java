package com.example.paindiary.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.paindiary.AlertReceiver;
import com.example.paindiary.databinding.AlarmFragmentBinding;

import java.util.Calendar;

public class AlarmFragment extends Fragment {

    private AlarmFragmentBinding binding;

    public AlarmFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AlarmFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.setButton.setOnClickListener(new View.OnClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                TimePicker time = binding.timePicker;
                int hour = time.getHour();
                int min = time.getMinute();

                binding.title.setText("Time="+Integer.toString(hour)+":"+Integer.toString(min));

                setAlarmForTime(time);

            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setAlarmForTime(TimePicker time){

       //Fetching time given
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time.getHour()); // For 1 PM or 2 PM

        c.set(Calendar.MINUTE, time.getMinute());
        c.set(Calendar.SECOND, 0);

        binding.title.setText(c.getTime().toString());
        c.set(Calendar.MINUTE, time.getMinute() - 2);

        try {
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
            //If time already pass for today, then setting to next day
            if (c.before(Calendar.getInstance())) {
                c.add(Calendar.DATE, 1);
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
           // binding.title.setText(c.getTime().toString());
            //binding.title.setText(time.getMinute());

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
