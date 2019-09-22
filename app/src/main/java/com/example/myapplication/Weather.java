package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("weather_state_abbr")
    @Expose
    private String weather_state_abbr;

    @SerializedName("min_temp")
    @Expose
    private String min_temp;

    @SerializedName("max_temp")
    @Expose
    private String max_temp;

    @SerializedName("the_temp")
    @Expose
    private String the_temp;

    @SerializedName("humidity")
    @Expose
    private String humidity;

    @SerializedName("predictability")
    @Expose
    private String predictability;

    @SerializedName("created")
    @Expose
    private String created;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeather_state_abbr() {
        return weather_state_abbr;
    }

    public void setWeather_state_abbr(String weather_state_abbr) {
        this.weather_state_abbr = weather_state_abbr;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(String the_temp) {
        this.the_temp = the_temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPredictability() {
        return predictability;
    }

    public void setPredictability(String predictability) {
        this.predictability = predictability;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
