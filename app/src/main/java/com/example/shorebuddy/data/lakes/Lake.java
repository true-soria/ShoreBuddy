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


//    public Lake(String name, int elevation, int bassNumber){
//
//    }
//    @ColumnInfo(name = "Coordinates")
//    public LatLng location = new LatLng(0, 0);
    @ColumnInfo(name = "Elevation")
    public double elevation;

    @ColumnInfo(name = "Bass")
    public int numberBass;

    @ColumnInfo(name = "Catfish")
    public int numberCatfish;

    @ColumnInfo(name = "AdSalmon")
    public int numberAdSalmon;


    //TODO implement more columns for database for each species of fish, list of fish
    //Bass,CatFish,AdSalmon,InlandSalm,Panfish,Shad,Steelhead,StripedBas,Sturgeon,TroutWH,TroutWild,TroutHatch,FishingCom,bcBrookTro,bcBrownTro,bcGoldenTr,bcRainbowT,bcLahontan,bcTrout,Longitude,Latitude


}
