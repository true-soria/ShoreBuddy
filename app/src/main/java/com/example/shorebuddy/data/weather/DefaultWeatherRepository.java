package com.example.shorebuddy.data.weather;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.shorebuddy.BuildConfig;
import com.example.shorebuddy.utilities.NetworkAccessor;
import com.google.android.gms.maps.model.LatLng;

// TODO
public class DefaultWeatherRepository implements WeatherRepository {
    private final NetworkAccessor mNetworkAccessor;
    private final MutableLiveData<Weather> weatherData;
    private LatLng currentLoc;

    public DefaultWeatherRepository() {
        mNetworkAccessor = NetworkAccessor.getInstance();
        weatherData = new MutableLiveData<>();
        Weather fetchingWeather = new Weather(new LatLng(0, 0));
        fetchingWeather.main = "Fetching Weather";

        weatherData.setValue(fetchingWeather);
    }


    @Override
    public LiveData<Weather> getWeatherData(LatLng location) {
        currentLoc = location;
        updateWeatherData();
        return weatherData;
    }

    @Override
    public void updateWeatherData() {
        if (currentLoc != null) {

            @SuppressLint("DefaultLocale")
            String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s", currentLoc.latitude, currentLoc.longitude, BuildConfig.WEATHER_API_KEY);

            StringRequest weatherRequest = new StringRequest(Request.Method.GET, url, response -> {
                Weather weather = JSONWeatherParser.parse(response);
                weatherData.setValue(weather);
            }, error -> {
                //TODO
            });
            weatherRequest.setShouldCache(false);

            mNetworkAccessor.addToRequestQueue(weatherRequest);
        }
    }
}
