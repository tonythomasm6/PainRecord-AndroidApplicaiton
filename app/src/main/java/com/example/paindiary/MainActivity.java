package com.example.paindiary;

/**
 * Author: Tony Thomas
 * StudentID: 31296149
 * Last update: 15-May-2021
 * **/


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.paindiary.dailyDataPush.DailyDataPushWorkManager;
import com.example.paindiary.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        binding.passLogin.setTransformationMethod(new PasswordTransformationMethod());
        binding.checkBox.setChecked(false);

        binding.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.checkBox.isChecked()){
                    binding.passLogin.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    binding.passLogin.setTransformationMethod(null);
                }
            }
        });


        //Listener on sign in button
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailLogin.getText().toString().trim();
                String pass = binding.passLogin.getText().toString().trim();

                //Validations on form fields
                if(email.isEmpty()){
                    binding.emailLogin.setError("Email can not be blank");
                    binding.emailLogin.requestFocus();
                    return;
                }
                if(pass.isEmpty()){
                    binding.passLogin.setError("Password cannot be blank");
                    binding.passLogin.requestFocus();
                    return;
                }
                else {

                    auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast toast = Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_LONG);
                            toast.show();
                            //Redirecting to home activity on successful login
                            startActivity(new Intent(MainActivity.this, HomeActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast toast = Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            }
        });


        binding.signup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });


        binding.forgotPass.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotActivity.class));
            }
        });

        //Calling work manager
        callWorkManager();

    }

    public void callWorkManager(){

        Calendar pushTime = Calendar.getInstance();

        // Data pushed everyday at 10PM
        pushTime.set(Calendar.HOUR_OF_DAY, 22);
        pushTime.set(Calendar.MINUTE, 0);
        pushTime.set(Calendar.SECOND, 0);

        if (pushTime.before(Calendar.getInstance().getTime())) {
            pushTime.add(Calendar.HOUR_OF_DAY, 24);
        }

        long timeDiff = pushTime.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();

        WorkRequest insertDataReq = new OneTimeWorkRequest.Builder(DailyDataPushWorkManager.class)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS).build();

        WorkManager
                .getInstance(getApplicationContext())
                .enqueue(insertDataReq);
    }
}