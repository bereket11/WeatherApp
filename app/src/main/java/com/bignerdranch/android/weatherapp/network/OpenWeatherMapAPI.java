package com.bignerdranch.android.weatherapp.network;

/**
 * Created by berekethaile on 4/24/18.
 */
import com.bignerdranch.android.weatherapp.model.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherMapAPI {
    @GET("/data/2.5/weather")
    Call<WeatherData> getWeather(@Query("zip") String zip,
                                 @Query("units") String units,
                                 @Query("appid") String appid);
}
