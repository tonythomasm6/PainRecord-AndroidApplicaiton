package com.example.paindiary.retrofit;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {
    @SerializedName("main")
    public Weather weather;

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
