package com.example.shorebuddy.data.solunar;

import android.icu.text.SimpleDateFormat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

@Entity(tableName = "solunar_cache")
public class Solunar{
    public Solunar(LatLng location, Calendar date) {
        latitude = location.latitude;
        longitude = location.longitude;
        dateReference = simpleDateFormat.format(date.getTime());
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

    private final Calendar timestamp;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/DD/YYYY");
    static final int HOURS_PER_DAY = 24;

    @ColumnInfo(name = "date")
    private String dateReference;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

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

    // I don't know, but seems importnant TODO: ask Shaun
    @ColumnInfo(name = "dayRating")
    public int dayRating;

    @ColumnInfo(name = "hourlyRating")
    public int[] hourlyRating;

    // TODO: Do we need Major and Minor... hunt times?
}
