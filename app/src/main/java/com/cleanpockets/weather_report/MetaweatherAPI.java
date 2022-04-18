package com.cleanpockets.weather_report;

import com.cleanpockets.weather_report.weather_models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MetaweatherAPI {

    @GET("{woeid}")
    Call<WeatherModel> getWeatherReport(@Path("woeid") int cityWoeid);


}
