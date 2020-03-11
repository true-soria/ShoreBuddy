package com.example.shorebuddy.data.lakes;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.shorebuddy.utilities.Converters;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

// TODO: Implement as a Room model
@Entity(tableName = "lake_table")
public class Lake {

    @PrimaryKey
    @NotNull
    public final String name;

    @ColumnInfo(name = "Coordinates")
    @TypeConverters(Converters.class)
    public LatLng location = new LatLng(0,0);


    public Lake(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public String getLakeName(){ return this.name;}

    @NotNull
    public LatLng getLakeCoor(){return this.location;}

    @ColumnInfo(name = "Elevation")
    public double elevation;

    @ColumnInfo(name = "Bass")
    public boolean bass;

    @ColumnInfo(name = "Catfish")
    public boolean catfish;

    @ColumnInfo(name = "AdSalmon")
    public boolean adSalmon;

    @ColumnInfo(name = "InlandSalmon")
    public boolean inLandSalmon;

    @ColumnInfo(name = "Panfish")
    public boolean panfish;

    @ColumnInfo(name = "Shad")
    public boolean shad;

    @ColumnInfo(name = "Steelhead")
    public boolean steelhead;

    @ColumnInfo(name = "StripeBass")
    public boolean stripedBass;

    @ColumnInfo(name = "Sturgeon")
    public boolean sturgeon;

    @ColumnInfo(name = "TroutWH")
    public boolean trouthHW;

    @ColumnInfo(name = "TroutWild")
    public boolean troutWild;

    @ColumnInfo(name = "TroutHatch")
    public boolean troutHatch;

    @ColumnInfo(name = "BrookTrout")
    public boolean brookTrout;

    @ColumnInfo(name = "BrownTrout")
    public boolean brownTrout;

    @ColumnInfo(name = "GoldenTrout")
    public boolean goldenTrout;

    @ColumnInfo(name = "RainbowTrout")
    public boolean rainbowTrout;

    @ColumnInfo(name = "LahontanTrout")
    public boolean lahontan;

    @ColumnInfo(name = "Trout")
    public boolean trout;


    //Longitude,Latitude


}
