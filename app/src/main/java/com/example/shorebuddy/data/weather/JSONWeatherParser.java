package com.example.shorebuddy.data.weather;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

class JSONWeatherParser {
    public static Weather parse(String str) throws JSONException {
        Weather weather;
            JSONObject root = new JSONObject(str);
            JSONObject weatherInfo = root.getJSONArray("weather").getJSONObject(0);
            JSONObject coords = root.getJSONObject("coord");
            JSONObject mainInfo = root.getJSONObject("main");
            JSONObject windInfo = root.getJSONObject("wind");
            LatLng location = new LatLng(coords.getDouble("lat"), coords.getDouble("lon"));
            weather = new Weather(location);
            weather.main = weatherInfo.getString("main");
            weather.description = weatherInfo.getString("description");
            weather.iconPath = weatherInfo.getString("icon");
            weather.temperature = mainInfo.getDouble("temp");
            weather.pressure = mainInfo.getInt("pressure");
            weather.humidity = mainInfo.getInt("humidity");
            weather.windSpeed = windInfo.getDouble("speed");
            weather.windDirection = windInfo.getInt("deg");
        return weather;
    }
}
