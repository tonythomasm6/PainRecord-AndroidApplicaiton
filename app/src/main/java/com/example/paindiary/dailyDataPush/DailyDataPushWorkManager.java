package com.example.paindiary.dailyDataPush;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.paindiary.db.entity.PainRecord;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.Date;

public class DailyDataPushWorkManager extends Worker {
    public DailyDataPushWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        if (FirebaseAuth.getInstance().getCurrentUser() == null)
            return null;


        SharedPreferences sharedPref=
                getApplicationContext().getSharedPreferences("painRecord", Context.MODE_PRIVATE);
        String message= sharedPref.getString("painRecordStore",null);
        Gson gson = new Gson();
        PainRecord painRecord = gson.fromJson(message, PainRecord.class);

        FirebaseDatabase.getInstance().getReference("PainRecords")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(String.valueOf(painRecord.id))
                .setValue(painRecord).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Data inserted !!", Toast.LENGTH_LONG).show();
                    Log.i("Info","Data inserted into firebase at "+(new Date()).toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Data not inserted !!", Toast.LENGTH_LONG).show();
                }
            }
        });


        return Result.success();
    }
}
