package com.example.shorebuddy.data.solunar;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public abstract class SolunarRepository {
    public abstract LiveData<Solunar> getSolunarData(LatLng location, Calendar date);

    public LiveData<Solunar> getSolunarData(LatLng location) {
        return getSolunarData(location, Calendar.getInstance());
    }

    public abstract void updateSolunarData();
}
