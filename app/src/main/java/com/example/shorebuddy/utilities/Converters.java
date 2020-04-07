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
    public static boolean intToBool(int value) {
        return value == 1;
    }

    @TypeConverter
    public static int boolToInt(boolean value) { if (value) return 1; return 0; }

    @TypeConverter
    public static String coordinatesToString(LatLng value){
        return value.latitude +","+ value.longitude;
    }

    @TypeConverter
    public static LatLng stringToCoordinates(String value){
        String[] latlng = value.split(",");
        return new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]));
    }

}
