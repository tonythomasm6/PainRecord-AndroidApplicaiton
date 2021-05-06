package com.example.paindiary.db.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<HashMap<String,Double>> weather;

    public WeatherViewModel(){
        this.weather = new MutableLiveData<>();
    }


    public MutableLiveData<HashMap<String,Double>> getWeather(){
        return weather;
    }

    public void setWeather(HashMap<String,Double> weather){
        this.weather.setValue(weather);
    }


}
