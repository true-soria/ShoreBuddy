package com.example.shorebuddy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shorebuddy.R;

public class WeatherAttributeView extends ConstraintLayout {
    private final TextView dataText;

    public WeatherAttributeView(Context context) {
        this(context, null);
    }

    public WeatherAttributeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherAttributeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String labelText;
        Drawable icon;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherAttributeViewOptions, defStyleAttr, 0);
            labelText = a.getString(R.styleable.WeatherAttributeViewOptions_label);
            icon = a.getDrawable(R.styleable.WeatherAttributeViewOptions_icon);
            a.recycle();
        }
        else {
            labelText = "Placeholder";
            icon = getResources().getDrawable(R.drawable.ic_compass_rose,null);
        }

        inflate(getContext(), R.layout.weather_attribute_layout, this);
        setBackground(getResources().getDrawable(R.drawable.rounded_square, null));
        dataText = findViewById(R.id.data_text_view);
        ImageView iconView = findViewById(R.id.icon);
        iconView.setImageDrawable(icon);
        TextView staticTitleView = findViewById(R.id.static_label_text);
        staticTitleView.setText(labelText);
        if (labelText.equals(getResources().getString(R.string.wind_speed))) {
            staticTitleView.setMaxLines(2);
        }
    }

    public void setData(String data) {
        dataText.setText(data);
    }
}
