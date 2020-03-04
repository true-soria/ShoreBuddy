package com.example.shorebuddy.data.weather;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

public class JSONWeatherParser {
    public Weather parse(String str, LatLng location) {
        Weather weather = new Weather(location);
        return weather;
    }
}
