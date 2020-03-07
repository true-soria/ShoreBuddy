package com.example.shorebuddy.data.solunar;

import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;

public interface SolunarRepository {
    LiveData<Solunar> getSolunarData(LatLng location);
}

// TODO: Implement SolunarRepository interface;
