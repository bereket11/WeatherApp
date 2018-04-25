package com.bignerdranch.android.weatherapp;

/**
 * Created by berekethaile on 4/24/18.
 */

public class WeatherInfo {
    private String mainCondition;
    private String descriptionCondition;
    private Double temperature;
    private Double windSpeed;
    private int pressure;
    private int humidity;
    private int sunrise;
    private int sunset;
    private String cityName;

    public WeatherInfo (String mainCondition, String descriptionCondition, Double temperature,
                        Double windSpeed, int pressure, int humidity, int sunrise, int sunset,
                        String cityName) {
        this.mainCondition = mainCondition;
        this.descriptionCondition = descriptionCondition;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.cityName = cityName;
    }

    public String getMainCondition() {
        return mainCondition;
    }

    public String getDescriptionCondition() {
        return descriptionCondition;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getSunrise() {
        return sunrise;
    }
    public int getSunset() {
        return sunset;
    }

    public String getCityName() {
        return cityName;
    }
}

