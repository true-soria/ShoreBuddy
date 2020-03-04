package com.example.shorebuddy.data;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Date;

// TODO: Implement
public class Solunar{

    private JSONObject solunarAPIData;
    private final int hoursPerDay = 24;

    public Solunar(LatLng location, Date date) throws JSONException {
        // TODO: the API requires latitude, longitude, date (yyyymmdd), and time zone (int offset)
        //  there should be an agreed upon source for the last two for adding this data
        // String html = "https://api.solunar.org/solunar/" + location.latitude + "," + location.longitude + ",";

        String htmlData = "{\"sunRise\":\"10:46\",\"sunTransit\":\"16:12\",\"sunSet\":\"21:39\",\"moonRise\":\"2:48\",\"moonTransit\":\"20:16\",\"moonUnder\":\"8:41\",\"moonSet\":\"14:25\",\"moonPhase\":\"Waning Gibbous\",\"moonIllumination\":0.6172356293980235,\"sunRiseDec\":10.766666666666667,\"sunTransitDec\":16.2,\"sunSetDec\":21.65,\"moonRiseDec\":2.8,\"moonSetDec\":14.416666666666666,\"moonTransitDec\":20.266666666666666,\"moonUnderDec\":8.683333333333334,\"minor1StartDec\":2.3,\"minor1Start\":\"02:18\",\"minor1StopDec\":3.3,\"minor1Stop\":\"03:18\",\"minor2StartDec\":13.916666666666666,\"minor2Start\":\"13:55\",\"minor2StopDec\":14.916666666666666,\"minor2Stop\":\"14:55\",\"major1StartDec\":19.266666666666666,\"major1Start\":\"19:16\",\"major1StopDec\":21.266666666666666,\"major1Stop\":\"21:16\",\"major2StartDec\":7.683333333333334,\"major2Start\":\"07:41\",\"major2StopDec\":9.683333333333334,\"major2Stop\":\"09:41\",\"dayRating\":0,\"hourlyRating\":{\"0\":0,\"1\":0,\"2\":20,\"3\":20,\"4\":0,\"5\":0,\"6\":0,\"7\":20,\"8\":20,\"9\":20,\"10\":40,\"11\":20,\"12\":0,\"13\":0,\"14\":20,\"15\":20,\"16\":0,\"17\":0,\"18\":0,\"19\":20,\"20\":20,\"21\":40,\"22\":40,\"23\":0}}";
        this.solunarAPIData = (JSONObject) new JSONTokener(htmlData).nextValue();
    }

    public Solunar(LatLng location) throws JSONException {
        this(location, new Date());
    }

    /**
     * Method for Moon Phases
     * @return Moon phase as a descriptive string (e.g. "Waning Gibbous")
     * @throws JSONException if the html data was not retrievable.
     */

    public String getMoonCycle() throws JSONException {
        return solunarAPIData.getString("moonPhase");
    }

    /**
     * Moon Cycle getter methods for Moon Rise, Moon Transit, and Moon Set
     * @return Strings of format ##:## in 24 hour time
     * @return Alternatively, the Double suffix returns 24 hour doubles
     * @throws JSONException if the html data was not retrievable.
     */

    public String getMoonRise() throws JSONException {
        return solunarAPIData.getString("moonRise");
    }

    public double getMoonRiseDouble() throws JSONException {
        return solunarAPIData.getDouble("moonRiseDec");
    }

    public String getMoonTransit() throws JSONException {
        return solunarAPIData.getString("moonTransit");
    }

    public double getMoonTransitDouble() throws JSONException {
        return solunarAPIData.getDouble("moonTransitDec");
    }

    public String getMoonSet() throws JSONException {
        return solunarAPIData.getString("moonSet");
    }

    public double getMoonSetDouble() throws JSONException {
        return solunarAPIData.getDouble("moonSetDec");
    }

    /**
     * Sun Cycle getter methods for Sun Rise, Sun Transit, and Sun Set
     * @return Strings of format ##:## in 24 hour time
     * @return Alternatively, the Double suffix returns 24 hour doubles
     * @throws JSONException if the html data was not retrievable.
     */

    public String getSunRise() throws JSONException {
        return solunarAPIData.getString("sunRise");
    }

    public double getSunRiseDouble() throws JSONException {
        return solunarAPIData.getDouble("sunRiseDec");
    }

    public String getSunTransit() throws JSONException {
        return solunarAPIData.getString("sunTransit");
    }

    public double getSunTransitDouble() throws JSONException {
        return solunarAPIData.getDouble("sunTransitDec");
    }

    public String getSunSet() throws JSONException {
        return solunarAPIData.getString("sunSet");
    }

    public double getSunSetDouble() throws JSONException {
        return solunarAPIData.getDouble("sunSetDec");
    }


    /**
     * Moon Position getter methods for Moon Overhead and Moon Underfoot
     * @return Strings of format ##:## in 24 hour time
     * @return Alternatively, the Double suffix returns 24 hour doubles
     * @throws JSONException if the html data was not retrievable.
     */

    public String getMoonUnder() throws JSONException {
        return solunarAPIData.getString("moonUnder");
    }

    public double getMoonUnderDouble() throws JSONException {
        return solunarAPIData.getDouble("moonUnderDec");
    }

    // TODO: accessor methods for Major and Minor... hunt times?

    /**
     * How much of this is necessary?
     * Method for Percent Illumination of the moon
     * Day Rating?? Hourly Rating??
     * @return a percent as a double, an int, and an int array indexed by hour respectively
     * @throws JSONException if the html data was not retrievable.
     */

    public double getMoonIllumination() throws JSONException {
        return solunarAPIData.getDouble("moonIllumination");
    }

    public int getDayRating() throws JSONException {
        return solunarAPIData.getInt("dayRating");
    }

    public int[] getHourlyRating() throws JSONException {
        int[] hourlyRating = new int[hoursPerDay];
        JSONObject hourlyRatingJSON = solunarAPIData.getJSONObject("hourlyRating");

        for (int i = 0; i < hoursPerDay; i++) {
            hourlyRating[i] = hourlyRatingJSON.getInt("" + i);
        }

        return hourlyRating;
    }

}
