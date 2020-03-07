package com.example.shorebuddy.data.lakes;

import com.google.android.gms.maps.model.LatLng;

// TODO: Implement as a Room model
public class Lake {
    public final String name;
    public LatLng location = new LatLng(0, 0);

    public Lake(String n) {
        name = n;
    }
}
