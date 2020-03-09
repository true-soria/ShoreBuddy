package com.example.shorebuddy.data.solunar;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Calendar;

// TODO: Implement
public class Solunar{

    private JSONObject solunarAPIData;
    private final int hoursPerDay = 24;
    private final String addressAPI = "https://api.solunar.org/solunar/";
    private String[] html = {"34.2985355", "-119.2204548", "202003014", "-4"};

//
//    public Solunar(LatLng location, GregorianCalendar date) throws JSONException {
//        // TODO: the API requires latitude, longitude, date (yyyymmdd), and time zone (int offset)
//        //  there should be an agreed upon source for the last two for adding this data
//        // String html = "https://api.solunar.org/solunar/" + location.latitude + "," + location.longitude + ",";
//
//        String htmlData = "{\"sunRise\":\"10:46\",\"sunTransit\":\"16:12\",\"sunSet\":\"21:39\",\"moonRise\":\"2:48\",\"moonTransit\":\"20:16\",\"moonUnder\":\"8:41\",\"moonSet\":\"14:25\",\"moonPhase\":\"Waning Gibbous\",\"moonIllumination\":0.6172356293980235,\"sunRiseDec\":10.766666666666667,\"sunTransitDec\":16.2,\"sunSetDec\":21.65,\"moonRiseDec\":2.8,\"moonSetDec\":14.416666666666666,\"moonTransitDec\":20.266666666666666,\"moonUnderDec\":8.683333333333334,\"minor1StartDec\":2.3,\"minor1Start\":\"02:18\",\"minor1StopDec\":3.3,\"minor1Stop\":\"03:18\",\"minor2StartDec\":13.916666666666666,\"minor2Start\":\"13:55\",\"minor2StopDec\":14.916666666666666,\"minor2Stop\":\"14:55\",\"major1StartDec\":19.266666666666666,\"major1Start\":\"19:16\",\"major1StopDec\":21.266666666666666,\"major1Stop\":\"21:16\",\"major2StartDec\":7.683333333333334,\"major2Start\":\"07:41\",\"major2StopDec\":9.683333333333334,\"major2Stop\":\"09:41\",\"dayRating\":0,\"hourlyRating\":{\"0\":0,\"1\":0,\"2\":20,\"3\":20,\"4\":0,\"5\":0,\"6\":0,\"7\":20,\"8\":20,\"9\":20,\"10\":40,\"11\":20,\"12\":0,\"13\":0,\"14\":20,\"15\":20,\"16\":0,\"17\":0,\"18\":0,\"19\":20,\"20\":20,\"21\":40,\"22\":40,\"23\":0}}";
//        this.solunarAPIData = (JSONObject) new JSONTokener(htmlData).nextValue();
//    }
//
//    public Solunar(LatLng location) throws JSONException {
//        this(location, new GregorianCalendar());
//    }

    public Solunar(){
        String htmlData = "{\"sunRise\":\"10:27\",\"sunTransit\":\"16:10\",\"sunSet\":\"21:53\",\"moonRise\":\"14:22\",\"moonTransit\":\"20:41\",\"moonUnder\":\"9:04\",\"moonSet\":\"3:40\",\"moonPhase\":\"Waxing Crescent\",\"moonIllumination\":0.43029742032317797,\"sunRiseDec\":10.45,\"sunTransitDec\":16.166666666666668,\"sunSetDec\":21.883333333333333,\"moonRiseDec\":14.366666666666667,\"moonSetDec\":3.6666666666666665,\"moonTransitDec\":20.683333333333334,\"moonUnderDec\":9.066666666666666,\"minor1StartDec\":13.866666666666667,\"minor1Start\":\"13:52\",\"minor1StopDec\":14.866666666666667,\"minor1Stop\":\"14:52\",\"minor2StartDec\":3.1666666666666665,\"minor2Start\":\"03:10\",\"minor2StopDec\":4.166666666666666,\"minor2Stop\":\"04:09\",\"major1StartDec\":19.683333333333334,\"major1Start\":\"19:41\",\"major1StopDec\":21.683333333333334,\"major1Stop\":\"21:41\",\"major2StartDec\":8.066666666666666,\"major2Start\":\"08:04\",\"major2StopDec\":10.066666666666666,\"major2Stop\":\"10:04\",\"dayRating\":0,\"hourlyRating\":{\"0\":0,\"1\":0,\"2\":0,\"3\":20,\"4\":20,\"5\":0,\"6\":0,\"7\":0,\"8\":20,\"9\":20,\"10\":40,\"11\":40,\"12\":0,\"13\":0,\"14\":20,\"15\":20,\"16\":0,\"17\":0,\"18\":0,\"19\":20,\"20\":20,\"21\":40,\"22\":40,\"23\":0}}";
        try {
            this.solunarAPIData = (JSONObject) new JSONTokener(htmlData).nextValue();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method for Moon Phases
     * @return Moon phase as a descriptive string (e.g. "Waning Gibbous")
     */

    public String getMoonCycle(){
        try {
            return solunarAPIData.getString("moonPhase");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Moon Cycle getter methods for Moon Rise, Moon Transit, and Moon Set
     * @return Strings of format ##:## in 24 hour time
     * @return Alternatively, the Double suffix returns 24 hour doubles
     */

    public String getMoonRise() {
        try {
            return solunarAPIData.getString("moonRise");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMoonRiseDouble() {
        try {
            return solunarAPIData.getDouble("moonRiseDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMoonTransit() {
        try {
            return solunarAPIData.getString("moonTransit");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMoonTransitDouble() {
        try {
            return solunarAPIData.getDouble("moonTransitDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMoonSet() {
        try {
            return solunarAPIData.getString("moonSet");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMoonSetDouble() {
        try {
            return solunarAPIData.getDouble("moonSetDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sun Cycle getter methods for Sun Rise, Sun Transit, and Sun Set
     * @return Strings of format ##:## in 24 hour time
     */

    public String getSunRise() {
        try {
            return solunarAPIData.getString("sunRise");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getSunRiseDouble() {
        try {
            return solunarAPIData.getDouble("sunRiseDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSunTransit() {
        try {
            return solunarAPIData.getString("sunTransit");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getSunTransitDouble() {
        try {
            return solunarAPIData.getDouble("sunTransitDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSunSet() {
        try {
            return solunarAPIData.getString("sunSet");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getSunSetDouble() {
        try {
            return solunarAPIData.getDouble("sunSetDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Moon Position getter methods for Moon Overhead and Moon Underfoot
     * @return Strings of format ##:## in 24 hour time
     * @return Alternatively, the Double suffix returns 24 hour doubles
     */

    public String getMoonUnder() {
        try {
            return solunarAPIData.getString("moonUnder");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public double getMoonUnderDouble() {
        try {
            return solunarAPIData.getDouble("moonUnderDec");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: accessor methods for Major and Minor... hunt times?

    /**
     * How much of this is necessary?
     * Method for Percent Illumination of the moon
     * Day Rating?? Hourly Rating??
     * @return a percent as a double, an int, and an int array indexed by hour respectively
     */

    public double getMoonIllumination() {
        try {
            return solunarAPIData.getDouble("moonIllumination");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int getDayRating() {
        try {
            return solunarAPIData.getInt("dayRating");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public int[] getHourlyRating() {
        try {
            int[] hourlyRating = new int[hoursPerDay];
            JSONObject hourlyRatingJSON = solunarAPIData.getJSONObject("hourlyRating");

            for (int i = 0; i < hoursPerDay; i++) {
                hourlyRating[i] = hourlyRatingJSON.getInt("" + i);
            }

            return hourlyRating;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
