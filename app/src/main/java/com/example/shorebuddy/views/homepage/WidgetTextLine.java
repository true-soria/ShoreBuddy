package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shorebuddy.R;

import java.util.Locale;

public class WidgetTextLine extends ConstraintLayout implements WidgetTextGenerator{
    private final TextView propertyValue;
    private final propertyValues propertyValueID;

    public WidgetTextLine(Context context) {
        this(context, null);
    }

    public WidgetTextLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetTextLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        String propertyName;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WidgetTextGenerator, defStyleAttr, 0);
            propertyName = a.getString(R.styleable.WidgetTextGenerator_property_name);
            propertyValueID = propertyValues.values()[a.getInt(R.styleable.WidgetTextGenerator_property_value, 0)];
            a.recycle();
        }
        else {
            propertyName = "";
            propertyValueID = propertyValues.MOON_CYCLE;
        }

        inflate(getContext(), R.layout.widget_text_layout, this);
        TextView staticTitleView = findViewById(R.id.name_text);
        staticTitleView.setText(propertyName);
        propertyValue = findViewById(R.id.value_text);
        propertyValue.setText(String.format(Locale.US, "%s", propertyName));
        setData(String.format(Locale.US, "%s", propertyName));
    }

    public propertyValues getPropertyValueID() { return propertyValueID; }

    public void setData(String data) {
        propertyValue.setText(data);
    }
}
