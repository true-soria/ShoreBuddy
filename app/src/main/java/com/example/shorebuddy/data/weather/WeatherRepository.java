package com.example.shorebuddy.data.weather;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.weather.Weather;
import com.google.android.gms.maps.model.LatLng;

public interface WeatherRepository {
    LiveData<Weather> getWeatherData(LatLng location);

    // Invalidate cache entry and fetch fresh weather data.
    void updateWeatherData(LatLng location);
}

// TODO: Implement WeatherRepository interface
