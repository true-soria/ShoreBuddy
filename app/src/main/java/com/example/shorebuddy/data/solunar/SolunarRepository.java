package com.example.shorebuddy.data.solunar;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public interface SolunarRepository {
    LiveData<Solunar> getSolunarData(LatLng location, Calendar date);

    void updateSolunarData();
}

// TODO: Implement SolunarRepository interface;
