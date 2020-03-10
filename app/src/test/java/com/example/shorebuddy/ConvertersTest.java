package com.example.shorebuddy;

import com.example.shorebuddy.utilities.Converters;
import com.google.android.gms.maps.model.LatLng;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertersTest {

    @Test
    public void coordinatesToString() {
        assertEquals("12.0,13.0", Converters.coordinatesToString(new LatLng(12,13)));
    }

    @Test
    public void stringToCoordinates() {
        LatLng latLng = new LatLng(12,13);
        assertEquals(latLng,Converters.stringToCoordinates("12.0,13.0"));
    }
}