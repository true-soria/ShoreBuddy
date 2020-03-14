package com.example.shorebuddy.data.lakes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.internal.$Gson$Preconditions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


// TODO: Implement as a Room model
@Entity(tableName = "lake_table")
public class Lake {

    @PrimaryKey
    @NotNull
    public final String name;

    private ArrayList<String> fishList = new ArrayList<String>();

    @ColumnInfo(name = "Latitude")
    private double latitude;

    @ColumnInfo(name = "Longitude")
    private double longitude;

    @Ignore
    public Lake(@NotNull String name) {
        this.name = name;
        this.latitude = 0;
        this.longitude = 0;
    }

    public Lake(@NotNull String name, double latitude, double longitude, ArrayList<String> fishList){
        this.name=name;
        this.latitude=latitude;
        this.longitude=longitude;
        this.fishList=fishList;

    }

    public double getLatitude(){return this.latitude;}

    public double getLongitude(){return this.longitude;}

    public void setLatitude(double latitude){
        this.latitude=latitude;
    }
    public void setLongitude(double longitude){ this.longitude=longitude;}

    public LatLng getLakeLatLng(){
        return new LatLng(latitude,longitude);
    }

    public ArrayList<String> getFishList(){return this.fishList;}

    public void setFishList(ArrayList<String> fishList){this.fishList=fishList;}

//    @ColumnInfo(name = "Fish")
//    public ArrayList<String> fishList;

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

}
