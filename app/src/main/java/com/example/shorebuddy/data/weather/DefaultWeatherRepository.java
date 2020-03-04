package com.example.shorebuddy.data.weather;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

// TODO
public class DefaultWeatherRepository implements WeatherRepository {

    @Override
    public LiveData<Weather> getWeatherData(LatLng location) {
        return null;
    }

    @Override
    public void updateWeatherData(LatLng location) {

    }
}
