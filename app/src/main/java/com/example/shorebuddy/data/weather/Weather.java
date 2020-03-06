package com.example.shorebuddy.data.weather;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

@Entity(tableName = "weather_cache")
public class Weather {
    public Weather(LatLng location) {
        mLatitude = location.latitude;
        mLongitude = location.longitude;
        mTimeStamp = Calendar.getInstance();
        temperature = 0;
        pressure = 0;
        humidity = 0;
        windDirection = 0;
        windSpeed = 0;
        main = "";
        description = "";
        iconPath = "";
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private final Calendar mTimeStamp;

    @ColumnInfo(name = "latitude")
    private final double mLatitude;

    @ColumnInfo(name = "longitude")
    private final double mLongitude;

    @ColumnInfo(name = "temperature")
    public double temperature;

    @ColumnInfo(name = "pressure")
    public double pressure;

    @ColumnInfo(name = "humidity")
    public double humidity;

    @ColumnInfo(name = "main")
    public String main;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "windSpeed")
    public double windSpeed;

    @ColumnInfo(name = "windDirection")
    public double windDirection;

    public String iconPath;

    public LatLng getLocation() {
        return new LatLng(mLatitude, mLongitude);
    }

    public Calendar getTimeStamp() {
        return mTimeStamp;
    }
}


