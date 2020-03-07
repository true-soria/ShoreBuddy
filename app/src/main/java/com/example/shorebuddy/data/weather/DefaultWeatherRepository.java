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
    private final MutableLiveData<Weather> mWeatherData;
    private LatLng mCurrentLoc;

    public DefaultWeatherRepository(OnAPIErrorHandler errorHandler) {
        mErrorHandler = errorHandler;
        mNetworkAccessor = NetworkAccessor.getInstance();
        mWeatherData = new MutableLiveData<>();
        Weather defaultWeather = new Weather(new LatLng(0, 0));
        mWeatherData.setValue(defaultWeather);
    }


    @Override
    public LiveData<Weather> getWeatherData(LatLng location) {
        mCurrentLoc = location;
        updateWeatherData();
        return mWeatherData;
    }

    @Override
    public void updateWeatherData() {
        if (mCurrentLoc != null) {
            String url = String.format(Locale.US,
                    "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=imperial&appid=%s",
                    mCurrentLoc.latitude,
                    mCurrentLoc.longitude,
                    BuildConfig.WEATHER_API_KEY);
            StringRequest weatherRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    Weather weather = JSONWeatherParser.parse(response);
                    mWeatherData.setValue(weather);
                } catch (JSONException e) {
                    mErrorHandler.onApiError(e);
                }
            }, mErrorHandler::onApiError);
            weatherRequest.setShouldCache(false);
            mNetworkAccessor.addToRequestQueue(weatherRequest);
        }
    }
}
