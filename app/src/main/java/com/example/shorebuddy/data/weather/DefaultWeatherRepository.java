package com.example.shorebuddy.data.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.shorebuddy.BuildConfig;
import com.example.shorebuddy.utilities.NetworkAccessor;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.Locale;

public class DefaultWeatherRepository implements WeatherRepository {
    public interface OnAPIErrorHandler {
        void onApiError(Exception e);
    }

    private final OnAPIErrorHandler errorHandler;
    private final NetworkAccessor networkAccessor;
    private final MutableLiveData<Weather> weatherData;
    private LatLng currentLocation;

    public DefaultWeatherRepository(OnAPIErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        networkAccessor = NetworkAccessor.getInstance();
        weatherData = new MutableLiveData<>();
        Weather defaultWeather = new Weather(new LatLng(0, 0));
        weatherData.setValue(defaultWeather);
    }


    @Override
    public LiveData<Weather> getWeatherData(LatLng location) {
        currentLocation = location;
        updateWeatherData();
        return weatherData;
    }

    @Override
    public void updateWeatherData() {
        if (currentLocation != null) {
            String url = String.format(Locale.US,
                    "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=imperial&appid=%s",
                    currentLocation.latitude,
                    currentLocation.longitude,
                    BuildConfig.WEATHER_API_KEY);
            StringRequest weatherRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    Weather weather = JSONWeatherParser.parse(response);
                    weatherData.setValue(weather);
                } catch (JSONException e) {
                    errorHandler.onApiError(e);
                }
            }, errorHandler::onApiError);
            weatherRequest.setShouldCache(false);
            networkAccessor.addToRequestQueue(weatherRequest);
        }
    }
}
