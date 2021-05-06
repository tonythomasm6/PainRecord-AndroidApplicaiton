package com.example.paindiary.db.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.paindiary.retrofit.Weather;

import java.util.HashMap;

public class WeatherViewModel extends ViewModel {

    private MutableLiveData<Double> temp;
    private MutableLiveData<Double> humidity;
    private MutableLiveData<Double> pressure;

    private MutableLiveData<HashMap<String,Double>> weather;

    public WeatherViewModel(){
        this.weather = new MutableLiveData<>();
        this.temp = new MutableLiveData<>();
        this.humidity = new MutableLiveData<>();
        this.pressure = new MutableLiveData<>();
    }


    public MutableLiveData<HashMap<String,Double>> getWeather(){
        return weather;
    }

    public void setWeather(HashMap<String,Double> weather){
        this.weather.setValue(weather);
    }


    public MutableLiveData<Double> getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp.setValue(temp);
    }

    public MutableLiveData<Double> getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity.setValue(humidity);
    }

    public MutableLiveData<Double> getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure.setValue(pressure);
    }
}
