package com.example.shorebuddy.data.solunar;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

class JSONSolunarAPIParser {
    public static Solunar parse(String str, LatLng location, Calendar date) throws JSONException {
        Solunar solunar;

        JSONObject root = new JSONObject(str);
        JSONObject hourlyRatingInfo = root.getJSONObject("hourlyRating");

        solunar = new Solunar(location, date);
        solunar.moonCycle = root.getString("moonPhase");
        solunar.moonRise = root.getString("moonRise");
        solunar.moonRiseDouble = root.getDouble("moonRiseDec");
        solunar.moonTransit = root.getString("moonTransit");
        solunar.moonTransitDouble = root.getDouble("moonTransitDec");
        solunar.moonSet = root.getString("moonSet");
        solunar.moonSetDouble = root.getDouble("moonSetDec");
        solunar.moonUnder = root.getString("moonUnder");
        solunar.moonUnderDouble = root.getDouble("moonUnderDec");

        solunar.sunRise = root.getString("sunRise");
        solunar.sunRiseDouble = root.getDouble("sunRiseDec");
        solunar.sunTransit = root.getString("sunTransit");
        solunar.sunTransitDouble = root.getDouble("sunTransitDec");
        solunar.sunSet = root.getString("sunSet");
        solunar.sunSetDouble = root.getDouble("sunSetDec");

        solunar.moonIllumination = root.getDouble("moonIllumination");
        solunar.dayRating = root.getInt("dayRating");
        for (int i = 0; i < Solunar.HOURS_PER_DAY; i++) {
            solunar.hourlyRating[i] = hourlyRatingInfo.getInt("" + i);
        }

        return solunar;
    }
}
