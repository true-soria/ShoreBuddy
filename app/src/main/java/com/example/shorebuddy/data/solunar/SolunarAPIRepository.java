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
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Locale;

public class SolunarAPIRepository implements SolunarRepository {

    private final MutableLiveData<Solunar> solunarData;

    private static final String TAG = MainActivity.class.getName();

    private Calendar currentDate;
    private int timeZone;
    private LatLng currentLocation;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");

    public SolunarAPIRepository(MutableLiveData<Solunar> solunarData) {
        this.solunarData = solunarData;
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
                    Solunar solunar = JSONSolunarAPIParser.parse(response, currentLocation, currentDate);
                } catch (JSONException e) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(TAG, "SolunarAPI Error: " + error.toString());
                }
            });
        }


    }
}
