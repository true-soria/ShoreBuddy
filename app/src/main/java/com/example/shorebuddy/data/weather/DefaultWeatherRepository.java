package com.example.shorebuddy.data.weather;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.shorebuddy.utilities.NetworkAccessor;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

// TODO
public class DefaultWeatherRepository implements WeatherRepository {
    private NetworkAccessor networkAccessor;
    private MutableLiveData<Weather> weatherData;
    private LatLng currentLoc;

    public DefaultWeatherRepository(Context ctx) {
        networkAccessor = NetworkAccessor.getInstance(ctx);
        weatherData = new MutableLiveData<>();
    }


    @Override
    public LiveData<Weather> getWeatherData(LatLng location) {
        currentLoc = location;
        String url = "";
        StringRequest weatherRequest = new StringRequest(Request.Method.GET, url, response -> {
            Weather weather = new Weather(location);
            try {
                JSONObject reader = new JSONObject(response);
                JSONObject w = reader.getJSONObject("weather");
                weather.main = w.getString("main");
            } catch (JSONException e) {
            }
            weatherData.setValue(weather);
        }, error -> {

        });
        return null;
    }

    @Override
    public void updateWeatherData() {
        if (currentLoc == null)
            return;
    }
}
