package com.example.shorebuddy.utilities;

import androidx.room.TypeConverter;

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
}
