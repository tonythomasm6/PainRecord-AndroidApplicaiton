package com.example.paindiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.paindiary.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
                            Toast toast = Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            }
        });



        binding.singup.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });



    }
}