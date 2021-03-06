package com.example.shorebuddy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
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
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WeatherAttributeView, defStyleAttr, 0);
            labelText = a.getString(R.styleable.WeatherAttributeView_label);
            icon = a.getDrawable(R.styleable.WeatherAttributeView_icon);
            a.recycle();
        }
        else {
            labelText = "Placeholder";
            icon = getResources().getDrawable(R.drawable.ic_compass_rose,null);
        }

        inflate(getContext(), R.layout.weather_attribute_layout, this);
        setBackground(getResources().getDrawable(R.drawable.rounded_square_widgets, null));
        TextView staticTitleView = findViewById(R.id.static_label_text);
        dataText = findViewById(R.id.data_text_view);
        ImageView iconView = findViewById(R.id.icon);
        if (icon != null) {
            iconView.setImageDrawable(icon);
        } else {
            iconView.setVisibility(View.GONE);
            staticTitleView.setPadding(8, 0, 0, 0);
        }
        staticTitleView.setText(labelText);
        if (labelText.equals(getResources().getString(R.string.wind_speed))) {
            staticTitleView.setMaxLines(2);
        }
    }

    public void setData(String data) {
        dataText.setText(data);
    }
}
