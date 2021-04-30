package com.example.paindiary.retrofit;

import com.google.gson.annotations.SerializedName;

//Weather corresponds to main in the json response
public class Weather {

    @SerializedName("temp")
    public String temp;

    @SerializedName("pressure")
    public String pressure;

    @SerializedName("humidity")
    public String humidity;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
