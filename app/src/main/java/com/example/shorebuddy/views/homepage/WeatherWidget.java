package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public class WeatherWidget extends ConstraintLayout implements ModuleWidget {

    public WeatherWidget(Context context) { this(context, null); }

    public WeatherWidget(Context context, AttributeSet attrs) { this(context, attrs, 0); }

    public WeatherWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Drawable getIcon() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }
}
