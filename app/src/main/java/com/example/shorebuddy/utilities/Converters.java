package com.example.shorebuddy.utilities;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

public class Converters {
    @TypeConverter
    public static boolean intToBool(int value) {
        return value == 1;
    }

    @TypeConverter
    public static int boolToInt(boolean value) { if (value) return 1; return 0; }

    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(value);
        return date;
    }

    @TypeConverter
    public static Long dateToTimestamp(Calendar date) {
        return date.getTimeInMillis();
    }

    @TypeConverter
    public static String coordinatesToString(LatLng value){
        return value.latitude +","+ value.longitude;
    }

    @TypeConverter
    public static LatLng stringToCoordinates(String value){
        String[] latlng = value.split(",");
        return new LatLng(Double.parseDouble(latlng[0]),Double.parseDouble(latlng[1]));
    }

    @TypeConverter
    public static String degreesToCardinalDirection(int degreeHeading) {
        String cardinalDirection = "NS";
        int headingSection = (int) ((degreeHeading + 11) / 22.5);
        switch (headingSection)
        {
            case 0:
                cardinalDirection = "N";
                break;
            case 1:
                cardinalDirection = "NNE";
                break;
            case 2:
                cardinalDirection = "NE";
                break;
            case 3:
                cardinalDirection = "ENE";
                break;
            case 4:
                cardinalDirection = "E";
                break;
            case 5:
                cardinalDirection = "ESE";
                break;
            case 6:
                cardinalDirection = "SE";
                break;
            case 7:
                cardinalDirection = "SSE";
                break;
            case 8:
                cardinalDirection = "S";
                break;
            case 9:
                cardinalDirection = "SSW";
                break;
            case 10:
                cardinalDirection = "SW";
                break;
            case 11:
                cardinalDirection = "WSW";
                break;
            case 12:
                cardinalDirection = "W";
                break;
            case 13:
                cardinalDirection = "WNW";
                break;
            case 14:
                cardinalDirection = "NW";
                break;
            case 15:
                cardinalDirection = "NNW";
                break;
        }
        return cardinalDirection;
    }

}
