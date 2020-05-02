package com.example.shorebuddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.solunar.Solunar;

import java.util.Locale;

public class SolunarDisplayView extends ConstraintLayout {
    public SolunarDisplayView(Context context) {
        this(context, null);
    }

    public SolunarDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SolunarDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.solunar_display, this);
    }

    public void setSolunar(Solunar solunar) {
        View rootView = getRootView();
        WeatherAttributeView moonPhase = rootView.findViewById(R.id.moon_phase);
        moonPhase.setData(String.format(Locale.US, "%s", solunar.moonCycle));
        WeatherAttributeView major1 = rootView.findViewById(R.id.moon_major_1);
        major1.setData(String.format(Locale.US, "%s to %s", solunar.major1Start, solunar.major1Stop));
        WeatherAttributeView major2 = rootView.findViewById(R.id.moon_major_2);
        major2.setData(String.format(Locale.US, "%s to %s", solunar.major2Start, solunar.major2Stop));
        WeatherAttributeView minor1 = rootView.findViewById(R.id.moon_minor_1);
        minor1.setData(String.format(Locale.US, "%s to %s", solunar.minor1Start, solunar.minor1Stop));
        WeatherAttributeView minor2 = rootView.findViewById(R.id.moon_minor_2);
        minor2.setData(String.format(Locale.US, "%s to %s", solunar.minor2Start, solunar.minor2Stop));
    }
}
