package com.example.paindiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.paindiary.databinding.ActivityForgotBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private ActivityForgotBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        binding = ActivityForgotBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString();
                if(email.isEmpty()){
                    binding.email.setError("Please enter a valid email address");
                    binding.email.requestFocus();
                    return;
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.email.setError("Please enter email in proper format");
                    binding.email.requestFocus();
                    return;
                }else{
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotActivity.this, "Password reset link send to registered mail", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotActivity.this, MainActivity.class)); // Redirecting to login screen
                            }
                        }
                    });
                }
            }
        });

    }
}