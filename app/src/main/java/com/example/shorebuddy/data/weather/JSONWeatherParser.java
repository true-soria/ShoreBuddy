package com.example.shorebuddy.data.weather;

import com.example.shorebuddy.utilities.Converters;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

class JSONWeatherParser {
    public static Weather parse(String str) {
        Weather weather;
        try {
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
            weather.temperature = Converters.kelvinToFahrenheit(mainInfo.getDouble("temp"));
            weather.pressure = mainInfo.getDouble("pressure");
            weather.humidity = mainInfo.getDouble("humidity");
            weather.windSpeed = windInfo.getDouble("speed");
            weather.windDirection = windInfo.getDouble("deg");
        } catch (JSONException e) {
            weather = new Weather(new LatLng(0,0));
            weather.main = "Error parsing weather data";
        }
        return weather;
    }
}
