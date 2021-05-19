package com.example.paindiary.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.paindiary.ForgotActivity;
import com.example.paindiary.MainActivity;
import com.example.paindiary.SignupActivity;
import com.example.paindiary.databinding.AddFragmentBinding;
import com.example.paindiary.databinding.LogoutFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogoutFragment extends Fragment {


private FirebaseUser firebaseUser;
private LogoutFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = LogoutFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().signOut();

        startActivity(new Intent(LogoutFragment.this.getActivity(), MainActivity.class));
        Toast.makeText(getActivity(), "Successfully Logged out !!", Toast.LENGTH_LONG).show();

        return view;

    }
}
