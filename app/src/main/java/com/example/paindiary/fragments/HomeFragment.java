package com.example.paindiary.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.paindiary.MainActivity;
import com.example.paindiary.SignupActivity;
import com.example.paindiary.databinding.HomeFragmentBinding;
import com.example.paindiary.db.viewModel.WeatherViewModel;
import com.example.paindiary.retrofit.RetrofitClient;
import com.example.paindiary.retrofit.RetrofitInterface;
import com.example.paindiary.retrofit.SearchResponse;
import com.example.paindiary.retrofit.Weather;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private HomeFragmentBinding binding;
    private static final String API_KEY = "a802b6bdfb6965b6f045710d2f52fd02"; // Weather
    private RetrofitInterface retrofitInterface; // Weather

    //Default location set for map
    double lat = -37.876823;
    double lon = 145.045837;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        retrofitInterface = RetrofitClient.getRetrofitService();//Weather
        getWeather(lat, lon);

        binding.sunny.setVisibility(View.GONE);
        binding.cloudy.setVisibility(View.GONE);
        binding.noncloud.setVisibility(View.GONE);

        /*binding.getWeather.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Getting latitude and longitude from address entered
                if(binding.address.getText().toString().isEmpty()){
                    binding.address.setError("Address can not be empty");
                    return;
                }
                else{
                    getLocation();
                    }
            }
        });  */

        return view;
    }

    public void getWeather(double lat, double lon) {
        Call<SearchResponse> callAsync =
                retrofitInterface.customSearch(lat, lon, API_KEY);
        callAsync.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    Weather list = response.body().weather;
                    String temp = list.getTemp();
                    double tempDouble = Double.parseDouble(temp) - 273.15;
                    DecimalFormat df = new DecimalFormat("###.##");
                    String tempCelsius = df.format(tempDouble) + "°C";
                    String hum = list.getHumidity();
                    String press = list.getPressure();
                    binding.temperature.setText(tempCelsius);
                    binding.humidity.setText(list.getHumidity() + " %");
                    binding.pressure.setText(list.getPressure() + " hPa");

                    //
                    if(tempDouble>20){
                        binding.sunny.setVisibility(View.VISIBLE);
                    }else if(tempDouble <21 && tempDouble>10){
                        binding.cloudy.setVisibility(View.VISIBLE);
                    }else{
                        binding.noncloud.setVisibility(View.VISIBLE);
                    }

                    //Using weather view model to transfer weather data
                    WeatherViewModel model = new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
                    HashMap<String,Double> weather = new HashMap<>();
                    weather.put("temp",Double.parseDouble(df.format(tempDouble)));
                    weather.put("humidity",Double.parseDouble(hum));
                    weather.put("pressure",Double.parseDouble(press));
                    model.setWeather(weather);


                } else {
                    Toast toast = Toast.makeText(getActivity(), "System error in " +
                            "fetching weather", Toast.LENGTH_LONG);
                    toast.show();
                }
                return;
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Toast toast = Toast.makeText(getActivity(), "System error in " +
                        "fetching weather 2", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        });
        return;
    }

    /*public void getLocation(){
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            String add = binding.address.getText().toString();
            List<Address> address = geocoder.getFromLocationName(add,1);
            if(address.size()==0){
                binding.address.setError("Please enter a valid or detail address");
                return;
            }

            double lat = address.get(0).getLatitude();
            double lon = address.get(0).getLongitude();

            binding.latitude.setText(Double.toString(lat));
            binding.longitude.setText(Double.toString(lon));

            getWeather(lat,lon);
        } catch (IOException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getActivity(), "System error in fetching location", Toast.LENGTH_LONG);
            toast.show();
        }return;
    }*/


}