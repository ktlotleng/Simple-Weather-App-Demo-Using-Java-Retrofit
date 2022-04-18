package com.cleanpockets.weather_report.weather_models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/* This is the array inside the Weather Model class (outer class) */
public class WeatherModel {

    @SerializedName("title")
    private String cityName;
    @SerializedName("woeid")
    private int cityWoeid;

    @SerializedName("consolidated_weather")
    List<ConsolidatedWeatherArray> consolidatedWeatherArrayList;

    public WeatherModel(String cityName, int cityWoeid, List<ConsolidatedWeatherArray> consolidatedWeatherArrayList) {
        this.cityName = cityName;
        this.cityWoeid = cityWoeid;
        this.consolidatedWeatherArrayList = consolidatedWeatherArrayList;
    }

    public String getCityName() {
        return cityName;
    }

    public int getCityWoeid() {
        return cityWoeid;
    }

    public List<ConsolidatedWeatherArray> getConsolidatedWeatherArrayList() {
        return consolidatedWeatherArrayList;
    }


}
