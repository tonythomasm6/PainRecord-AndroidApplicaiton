package com.example.paindiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.paindiary.databinding.ActivityMainBinding;
import com.example.paindiary.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        //Listener on register button
        binding.register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String email = binding.email.getText().toString().trim();
                String pass = binding.pass.getText().toString().trim();
                String repass = binding.repass.getText().toString().trim();

                //Form field validations
                if(email.isEmpty()){
                    binding.email.setError("Email can not be blank");
                    binding.email.requestFocus();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.email.setError("Please enter valid email");
                    binding.email.requestFocus();
                    return;
                }
                else if(pass.isEmpty()){
                    binding.pass.setError("Password cannot be blank");
                    binding.pass.requestFocus();
                    return;
                }
                else if(repass.isEmpty()){
                    binding.pass.setError("Please re-enter password");
                    binding.pass.requestFocus();
                    return;
                }
                else if(!pass.equalsIgnoreCase(repass)){
                    binding.repass.setError("Passwords does not match");
                    binding.repass.requestFocus();
                    return;
                }
                else{
                    //Registering the user on successful validation
                    auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        Toast toast = Toast.makeText(SignupActivity.this, "User registered successfully", Toast.LENGTH_LONG);
                                        toast.show();
                                        startActivity(new Intent(SignupActivity.this, MainActivity.class)); // Redirecting to login screen
                                    }else {
                                        Toast toast = Toast.makeText(SignupActivity.this,
                                                "Registration failed:"+task.getException().getMessage().toString(), Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                }
                            });
                }
                }

        });



    }
}