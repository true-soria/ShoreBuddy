package com.example.shorebuddy.data.weather;

import androidx.annotation.NonNull;
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
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "timeStamp")
    public Calendar tmeStamp;

    @ColumnInfo(name = "latitude")
    private double mLatitude;

    @ColumnInfo(name = "longitude")
    private double mLongitude;

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

    private boolean isValid = true;

    public LatLng getLocation() {
        return new LatLng(mLatitude, mLongitude);
    }

    public boolean isValid() {
        return isValid;
    }
}

/* {"coord": { "lon": 139,"lat": 35},
        "weather": [
        {
        "id": 800,
        "main": "Clear",
        "description": "clear sky",
        "icon": "01n"
        }
        ],
        "base": "stations",
        "main": {
        "temp": 281.52,
        "feels_like": 278.99,
        "temp_min": 280.15,
        "temp_max": 283.71,
        "pressure": 1016,
        "humidity": 93
        },
        "wind": {
        "speed": 0.47,
        "deg": 107.538
        },
        "clouds": {
        "all": 2
        },
        "dt": 1560350192,
        "sys": {
        "type": 3,
        "id": 2019346,
        "message": 0.0065,
        "country": "JP",
        "sunrise": 1560281377,
        "sunset": 1560333478
        },
        "timezone": 32400,
        "id": 1851632,
        "name": "Shuzenji",
        "cod": 200
        } */
