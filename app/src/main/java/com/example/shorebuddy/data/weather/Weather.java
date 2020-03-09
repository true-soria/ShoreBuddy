package com.example.shorebuddy.data.weather;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

@Entity(tableName = "weather_cache")
public class Weather {
    Weather(LatLng location) {
        latitude = location.latitude;
        longitude = location.longitude;
        timeStamp = Calendar.getInstance();
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

    private final Calendar timeStamp;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "temperature")
    public double temperature;

    @ColumnInfo(name = "pressure")
    public int pressure;

    @ColumnInfo(name = "humidity")
    public int humidity;

    @ColumnInfo(name = "main")
    public String main;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "windSpeed")
    public double windSpeed;

    @ColumnInfo(name = "windDirection")
    public int windDirection;

    public String iconPath;

    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    void setLocation(LatLng location) {
        latitude = location.latitude;
        longitude = location.longitude;
    }

    public Calendar getTimeStamp() {
        return timeStamp;
    }
}


