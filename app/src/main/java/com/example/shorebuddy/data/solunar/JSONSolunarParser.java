package com.example.shorebuddy.data.solunar;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

class JSONSolunarParser {
    public static Solunar parse(String str, LatLng location) throws JSONException {
        Solunar solunar;

        JSONObject root = new JSONObject(str);
        JSONObject hourlyRatingInfo = root.getJSONObject("hourlyRating");

        solunar = new Solunar(location);
        solunar.moonCycle = root.getString("moonPhase");
        solunar.moonRise = root.getString("moonRise");
        solunar.moonRiseDouble = root.optDouble("moonRiseDec");
        solunar.moonTransit = root.getString("moonTransit");
        solunar.moonTransitDouble = root.optDouble("moonTransitDec");
        solunar.moonSet = root.getString("moonSet");
        solunar.moonSetDouble = root.optDouble("moonSetDec");
        solunar.moonUnder = root.getString("moonUnder");
        solunar.moonUnderDouble = root.optDouble("moonUnderDec");

        solunar.minor1Start = root.getString("minor1Start");
        solunar.minor1StartDouble = root.optDouble("minor1StartDec");
        solunar.minor1Stop = root.getString("minor1Stop");
        solunar.minor1StopDouble = root.optDouble("minor1StopDec");
        solunar.minor2Start = root.getString("minor2Start");
        solunar.minor2StartDouble = root.optDouble("minor2StartDec");
        solunar.minor2Stop = root.getString("minor2Stop");
        solunar.minor2StopDouble = root.optDouble("minor2StopDec");
        solunar.major1Start = root.getString("major1Start");
        solunar.major1StartDouble = root.optDouble("major1StartDec");
        solunar.major1Stop = root.getString("major1Stop");
        solunar.major1StopDouble = root.optDouble("major1StopDec");
        solunar.major2Start = root.getString("major2Start");
        solunar.major2StartDouble = root.optDouble("major2StartDec");
        solunar.major2Stop = root.getString("major2Stop");
        solunar.major2StopDouble = root.optDouble("major2StopDec");

        solunar.sunRise = root.getString("sunRise");
        solunar.sunRiseDouble = root.optDouble("sunRiseDec");
        solunar.sunTransit = root.getString("sunTransit");
        solunar.sunTransitDouble = root.optDouble("sunTransitDec");
        solunar.sunSet = root.getString("sunSet");
        solunar.sunSetDouble = root.optDouble("sunSetDec");

        solunar.moonIllumination = root.optDouble("moonIllumination");
        solunar.dayRating = root.getInt("dayRating");
        for (int i = 0; i < Solunar.HOURS_PER_DAY; i++) {
            solunar.hourlyRating[i] = hourlyRatingInfo.getInt("" + i);
        }

        return solunar;
    }
}
