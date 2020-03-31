package com.example.shorebuddy.data.solunar;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SolunarTest {

    private Solunar solunarTest1 = new Solunar();
    private int[] hourlyRatingTest = {0, 0, 0, 20, 20, 0, 0, 0,
                                        20, 20, 40, 40, 0, 0, 20, 20,
                                        0, 0, 0, 20, 20, 40, 40, 0};
    Calendar calendar = Calendar.getInstance();

    @Test
    public void getMoonCycle() {
        assertEquals("Waxing Crescent", solunarTest1.getMoonCycle());
    }

    @Test
    public void getMoonRise() {
        assertEquals("14:22", solunarTest1.getMoonRise());
    }

    @Test
    public void getMoonRiseDouble() {
        assertEquals(14.366666666666667, solunarTest1.getMoonRiseDouble(), 0.1);
    }

    @Test
    public void getMoonTransit() {

    }

    @Test
    public void getMoonTransitDouble() {
    }

    @Test
    public void getMoonSet() {
    }

    @Test
    public void getMoonSetDouble() {
    }

    @Test
    public void getSunRise() {
    }

    @Test
    public void getSunRiseDouble() {
    }

    @Test
    public void getSunTransit() {
    }

    @Test
    public void getSunTransitDouble() {
    }

    @Test
    public void getSunSet() {
    }

    @Test
    public void getSunSetDouble() {
    }

    @Test
    public void getMoonUnder() {
    }

    @Test
    public void getMoonUnderDouble() {
    }

    @Test
    public void getMoonIllumination() {
    }

    @Test
    public void getDayRating() {
    }

    @Test
    public void getHourlyRating() {
        assertArrayEquals( hourlyRatingTest, solunarTest1.getHourlyRating());
    }
}