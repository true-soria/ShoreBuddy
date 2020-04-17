package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shorebuddy.R;

public class WidgetIconText extends ConstraintLayout implements WidgetTextGenerator {
    private final TextView propertyValue;
    private final propertyValues propertyValueID;

    public WidgetIconText(Context context) {
        this(context, null);
    }

    public WidgetIconText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WidgetIconText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Drawable propertyIcon;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WidgetIconText, defStyleAttr, 0);
            propertyIcon = a.getDrawable(R.styleable.WidgetIconText_property_icon);
            propertyValueID = propertyValues.values()[a.getInt(R.styleable.WidgetIconText_property_value, 0)];
            a.recycle();
        }
        else {
            propertyIcon = getResources().getDrawable(R.drawable.ic_compass_rose,null);
            propertyValueID = propertyValues.MOON_CYCLE;
        }

        inflate(getContext(), R.layout.widget_icon_text_layout, this);
        ImageView staticImage = findViewById(R.id.icon);
        propertyValue = findViewById(R.id.value_text);
        if (propertyIcon != null) {
            staticImage.setImageDrawable(propertyIcon);
        } else {
            staticImage.setVisibility(View.GONE);
        }
    }

    @Override
    public propertyValues getPropertyValueID() { return propertyValueID; }

    @Override
    public void setData(String data) { propertyValue.setText(data); }
}
