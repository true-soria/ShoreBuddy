package com.example.shorebuddy.utilities;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    public static double kelvinToFahrenheit(double kelvinTemp) {
        return (((kelvinTemp - 273.15) * (9./5.)) + 32);
    }


    @TypeConverter
    public static String coordinatesToString(LatLng value){
        return Double.toString(value.latitude)+","+Double.toString(value.longitude);
    }

    @TypeConverter
    public static LatLng stringToCoordinates(String value){
        String[] latlng = value.split(",");
        return new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]));
    }


}
