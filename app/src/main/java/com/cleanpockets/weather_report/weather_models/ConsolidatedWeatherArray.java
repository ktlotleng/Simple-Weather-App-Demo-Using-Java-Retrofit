package com.cleanpockets.weather_report.weather_models;

import com.google.gson.annotations.SerializedName;

public class ConsolidatedWeatherArray {

    @SerializedName("weather_state_name")
    private String weatherCondition;
    @SerializedName("weather_state_abbr")
    private String weatherConditionAbbr;
    @SerializedName("applicable_date")
    private String date;
    @SerializedName("the_temp")
    private double temperature;

    public ConsolidatedWeatherArray(String weatherCondition, String weatherConditionAbbr, String date,double temperature) {
        this.weatherCondition = weatherCondition;
        this.weatherConditionAbbr = weatherConditionAbbr;
        this.date = date;
        this.temperature = temperature;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getWeatherConditionAbbr() {
        return weatherConditionAbbr;
    }

    public String getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return "ConsolidatedWeatherArray{" +
                "weatherCondition='" + weatherCondition + '\'' +
                ", weatherConditionAbbr='" + weatherConditionAbbr + '\'' +
                ", date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                '}';
    }
}
