package com.example.shorebuddy.data;

import com.google.android.gms.maps.model.LatLng;

// TODO: Implement as a Room model
public class Lake {
    public String name;

    public Lake(String n) {
        name = n;
    }
    public LatLng getLocation() {
        return new LatLng(0, 0);
    }
}
