package com.example.shorebuddy.data.solunar;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.shorebuddy.MainActivity;
import com.example.shorebuddy.utilities.NetworkAccessor;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Locale;

public class SolunarAPIRepository implements SolunarRepository {
    public interface OnSolAPIErrorHandler {
        void onSolAPIError (Exception e);
    }
    private final OnSolAPIErrorHandler errorHandler;
    private final MutableLiveData<Solunar> solunarData;
    private final NetworkAccessor networkAccessor;

    private Calendar currentDate;
    private int timeZone;
    private LatLng currentLocation;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");

    public SolunarAPIRepository(OnSolAPIErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
        networkAccessor = NetworkAccessor.getInstance();

        this.solunarData = new MutableLiveData<>();
        Solunar defaultSolunar = new Solunar(new LatLng(0, 0));
        solunarData.setValue(defaultSolunar);
    }

    @Override
    public LiveData<Solunar> getSolunarData(LatLng location, Calendar date) {
        currentLocation = location;
        currentDate = date;
        timeZone = -4; // default of Pacific Time until there is a reason to change it.

        updateSolunarData();
        return solunarData;
    }

    @Override
    public void updateSolunarData() {
        if (currentLocation != null) {
            String url = String.format(Locale.US,
                    "https://api.solunar.org/solunar/%f,%f,%s,%d",
                    currentLocation.latitude,
                    currentLocation.longitude,
                    simpleDateFormat.format(currentDate.getTime()),
                    timeZone);
            StringRequest solunarRequest = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    Solunar solunar = JSONSolunarAPIParser.parse(response, currentLocation);
                    solunar.setDateReference(currentDate);
                    solunar.setLocation(currentLocation);
                    solunarData.setValue(solunar);
                } catch (JSONException e) {
                    errorHandler.onSolAPIError(e);
                }
            }, errorHandler::onSolAPIError);
            solunarRequest.setShouldCache(false);
            networkAccessor.addToRequestQueue(solunarRequest);
        }
    }

}
