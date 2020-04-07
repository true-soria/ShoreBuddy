package com.example.shorebuddy.data.lakes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.shorebuddy.utilities.Converters;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "lakes")
public class Lake {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "name")
    public String lakeName;

    @NotNull
    public String elevation;

    @NotNull
    public String county;

    public int region;

    @TypeConverters({Converters.class})
    public boolean boatramp;

    @TypeConverters({Converters.class})
    public boolean restroom;

    @ColumnInfo(name = "fishingComm")
    public String fishingComments;

    @TypeConverters({Converters.class})
    public boolean fuel;

    @ColumnInfo(name = "wcaccess")
    @TypeConverters({Converters.class})
    public boolean WheelChairAccess;

    double latitude;

    double longitude;

    public Lake(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setLatLng(LatLng location) {
        latitude = location.latitude;
        longitude = location.longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude,longitude);
    }
}
