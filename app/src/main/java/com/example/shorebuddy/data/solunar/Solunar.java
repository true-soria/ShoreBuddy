package com.example.shorebuddy.data.solunar;

import android.icu.text.SimpleDateFormat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Scanner;

@Entity(tableName = "solunar_cache")
public class Solunar{
    public Solunar(LatLng location) {
        latitude = location.latitude;
        longitude = location.longitude;
        //dateReference = "01/01/2000";
        timestamp = Calendar.getInstance();

        moonCycle = "";

        moonRise = "";
        moonTransit = "";
        moonSet = "";
        moonUnder = "";
        moonRiseDouble = 0;
        moonTransitDouble = 0;
        moonSetDouble = 0;
        moonUnderDouble = 0;

        minor1Start = "";
        minor1Stop = "";
        minor2Start = "";
        minor2Stop = "";
        minor1StartDouble = 0;
        minor1StopDouble = 0;
        minor2StartDouble = 0;
        minor2StopDouble = 0;
        major1Start = "";
        major1Stop = "";
        major2Start = "";
        major2Stop = "";
        major1StartDouble = 0;
        major1StopDouble = 0;
        major2StartDouble = 0;
        major2StopDouble = 0;

        sunRise = "";
        sunTransit = "";
        sunSet = "";
        sunRiseDouble = 0;
        sunTransitDouble = 0;
        sunSetDouble = 0;

        moonIllumination = 0;
        dayRating = 0;
        hourlyRating = new int[24];

    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Calendar timestamp;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/DD/YYYY");
    static final int HOURS_PER_DAY = 24;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    // Day to which this Solunar object refers to
    //@ColumnInfo(name = "date")
    //private String dateReference;

    // Moon phase as a descriptive string (e.g. "Waning Gibbous")
    @ColumnInfo(name = "moonCycle")
    public String moonCycle;

    // Moon Position Strings of format ##:## in 24 hour time
    // Alternatively, the Double suffix returns 24 hour doubles
    @ColumnInfo(name = "moonRise")
    public String moonRise;

    @ColumnInfo(name = "moonTransit")
    public String moonTransit;

    @ColumnInfo(name = "moonSet")
    public String moonSet;

    @ColumnInfo(name = "moonUnder")
    public String moonUnder;

    @ColumnInfo(name = "moonRiseDouble")
    public double moonRiseDouble;

    @ColumnInfo(name = "moonTransitDouble")
    public double moonTransitDouble;

    @ColumnInfo(name = "moonSetDouble")
    public double moonSetDouble;

    @ColumnInfo(name = "moonUnderDouble")
    public double moonUnderDouble;


    // Moon Minor and Major Strings of format ##:## in 24 hour time
    // Alternatively, the Double suffix returns 24 hour doubles
    @ColumnInfo(name = "minor1Start")
    public String minor1Start;

    @ColumnInfo(name = "minor1Stop")
    public String minor1Stop;

    @ColumnInfo(name = "minor2Start")
    public String minor2Start;

    @ColumnInfo(name = "minor2Stop")
    public String minor2Stop;

    @ColumnInfo(name = "minor1StartDouble")
    public double minor1StartDouble;

    @ColumnInfo(name = "minor1StopDouble")
    public double minor1StopDouble;

    @ColumnInfo(name = "minor2StartDouble")
    public double minor2StartDouble;

    @ColumnInfo(name = "minor2StopDouble")
    public double minor2StopDouble;

    @ColumnInfo(name = "major1Start")
    public String major1Start;

    @ColumnInfo(name = "major1Stop")
    public String major1Stop;

    @ColumnInfo(name = "major2Start")
    public String major2Start;

    @ColumnInfo(name = "major2Stop")
    public String major2Stop;

    @ColumnInfo(name = "major1StartDouble")
    public double major1StartDouble;

    @ColumnInfo(name = "major1StopDouble")
    public double major1StopDouble;

    @ColumnInfo(name = "major2StartDouble")
    public double major2StartDouble;

    @ColumnInfo(name = "major2StopDouble")
    public double major2StopDouble;

    // Sun Position Strings of format ##:## in 24 hour time
    // Alternatively, the Double suffix returns 24 hour doubles
    @ColumnInfo(name = "sunRise")
    public String sunRise;

    @ColumnInfo(name = "sunTransit")
    public String sunTransit;

    @ColumnInfo(name = "sunSet")
    public String sunSet;

    @ColumnInfo(name = "sunRiseDouble")
    public double sunRiseDouble;

    @ColumnInfo(name = "sunTransitDouble")
    public double sunTransitDouble;

    @ColumnInfo(name = "sunSetDouble")
    public double sunSetDouble;

    // Percent Illumination of the Moon
    @ColumnInfo(name = "moonIllumination")
    public double moonIllumination;

    // I don't know, but seems important
    @ColumnInfo(name = "dayRating")
    public int dayRating;

    @ColumnInfo(name = "hourlyRating")
    public int[] hourlyRating;

    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    public void setLocation(LatLng location) {
        latitude = location.latitude;
        longitude = location.longitude;
    }

    /*public Calendar getDateReference() {
        Calendar dateReferenceObj = Calendar.getInstance();
        Scanner scanner = new Scanner(this.dateReference);
        int month = scanner.nextInt() - 1;
        int day = scanner.nextInt() - 1;
        int year = scanner.nextInt();

        dateReferenceObj.set(year, month, day);
        return dateReferenceObj;
    }

    public void setDateReference (Calendar date) {
        dateReference = simpleDateFormat.format(date);
    }*/

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar calendar) {
        timestamp = calendar;
    }

}
