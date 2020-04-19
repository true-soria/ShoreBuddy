package com.example.shorebuddy.data.lakes;

import androidx.annotation.NonNull;
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
    public final String lakeName;

    @NotNull
    public final String elevation;

    @NotNull
    public final String county;

    public final int region;

    @TypeConverters({Converters.class})
    public final boolean boatramp;

    @TypeConverters({Converters.class})
    public final boolean restroom;

    @ColumnInfo(name = "fishingComm")
    public final String fishingComments;

    @TypeConverters({Converters.class})
    public final boolean fuel;

    @ColumnInfo(name = "wcaccess")
    @TypeConverters({Converters.class})
    public final boolean wheelChairAccess;

    final double latitude;

    final double longitude;

    public Lake(@NonNull String lakeName,
                @NonNull String elevation,
                @NonNull String county,
                String fishingComments,
                int region,
                boolean boatramp,
                boolean restroom,
                boolean fuel,
                boolean wheelChairAccess,
                double latitude,
                double longitude)
    {
        this.lakeName = lakeName;
        this.elevation = elevation;
        this.county = county;
        this.region = region;
        this.boatramp = boatramp;
        this.restroom = restroom;
        this.fishingComments = fishingComments;
        this.wheelChairAccess = wheelChairAccess;
        this.fuel = fuel;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude,longitude);
    }
}
