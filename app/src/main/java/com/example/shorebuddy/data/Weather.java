package com.example.shorebuddy.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

@Entity(tableName = "weather_cache")
public class Weather {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "timeStamp")
    private Date mTimeStamp;

    @ColumnInfo(name = "latitude")
    private Double mLatitude;

    @ColumnInfo(name = "longitude")
    private Double mLongitude;

    @ColumnInfo(name = "temperature")
    private Double mTemperature;

    @ColumnInfo(name = "pressure")
    private Double mPressure;

    @ColumnInfo(name = "humidity")
    private Double mHumidity;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "windSpeed")
    private Double mWindSpeed;

    @ColumnInfo(name = "windDirection")
    private Double mWindDirection;

    private boolean isValid = true;

    public Date getTimeStamp() {
        return mTimeStamp;
    }

    public Double getTemperature() {
        return mTemperature;
    }

    public Double getPressure() {
        return mPressure;
    }

    public Double getHumidity() {
        return mHumidity;
    }

    public String getDescription() {
        return mDescription;
    }

    public LatLng getLatLng() {
        return new LatLng(mLatitude, mLongitude);
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
