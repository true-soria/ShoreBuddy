package com.example.shorebuddy.utilities;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

    @TypeConverter
    public static ArrayList<String> fromString(String value){
        Type listType = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(value,listType);
    }


    @TypeConverter
    public static String fromArrayList(ArrayList<String> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }


}
