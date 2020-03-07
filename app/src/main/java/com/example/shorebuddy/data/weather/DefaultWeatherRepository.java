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

    private final OnAPIErrorHandler mErrorHandler;
    private final NetworkAccessor mNetworkAccessor;
    private final MutableLiveData<Weather> weatherData;
    private LatLng currentLoc;

    public DefaultWeatherRepository(OnAPIErrorHandler errorHandler) {
        mErrorHandler = errorHandler;
        mNetworkAccessor = NetworkAccessor.getInstance();
        weatherData = new MutableLiveData<>();
        Weather defaultWeather = new Weather(new LatLng(0, 0));
        weatherData.setValue(defaultWeather);
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
            String url = String.format(Locale.US,
                    "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=imperial&appid=%s",
                    currentLoc.latitude,
                    currentLoc.longitude,
                    BuildConfig.WEATHER_API_KEY);
            StringRequest weatherRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    Weather weather = JSONWeatherParser.parse(response);
                    weatherData.setValue(weather);
                } catch (JSONException e) {
                    mErrorHandler.onApiError(e);
                }
            }, mErrorHandler::onApiError);
            weatherRequest.setShouldCache(false);
            mNetworkAccessor.addToRequestQueue(weatherRequest);
        }
    }
}
