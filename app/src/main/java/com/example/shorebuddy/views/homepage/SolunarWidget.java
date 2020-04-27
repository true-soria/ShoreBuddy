package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.solunar.Solunar;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

public class SolunarWidget extends ModuleWidget {

    static final String MOON = "moon";
    private Drawable icon;
    private String title;

    private final WidgetTextLine property1;
    private final WidgetTextLine property2;
    private final WidgetTextLine property3;
    private final WidgetTextLine property4;

    public SolunarWidget(Context context) {
        this(context, null);
    }

    public SolunarWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SolunarWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.solunar_widget, this);

        property1 = findViewById(R.id.sol_property1);
        property2 = findViewById(R.id.sol_property2);
        property3 = findViewById(R.id.sol_property3);
        property4 = findViewById(R.id.sol_property4);

        this.icon = getResources().getDrawable(R.drawable.ic_moon1, null);
        this.title = String.format(Locale.US, "%s", MOON);
    }


    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setData(Object data)
    {
        Solunar solunar = tryCasting(data);

        this.property1.setData(findProperty(solunar, property1.getPropertyValueID()));
        this.property2.setData(findProperty(solunar, property2.getPropertyValueID()));
        this.property3.setData(findProperty(solunar, property3.getPropertyValueID()));
        this.property4.setData(findProperty(solunar, property4.getPropertyValueID()));
        setTitleAndIcon(solunar.moonCycle);
    }

    private String findProperty(Solunar solunar, WidgetTextGenerator.propertyValues valueID)
    {
        String propertyValue = MOON;
        switch (valueID)
        {
            case MINOR1:
                propertyValue = String.format(Locale.US, "%s to %s", solunar.minor1Start, solunar.minor1Stop);
                break;
            case MINOR2:
                propertyValue = String.format(Locale.US, "%s to %s", solunar.minor2Start, solunar.minor2Stop);
                break;
            case MAJOR1:
                propertyValue = String.format(Locale.US, "%s to %s", solunar.major1Start, solunar.major1Stop);
                break;
            case MAJOR2:
                propertyValue = String.format(Locale.US, "%s to %s", solunar.major2Start, solunar.major2Stop);
                break;
        }

        return propertyValue;
    }

    private void setTitleAndIcon(String title)
    {
        this.title = String.format(Locale.US, "%s", title);
        int iconReference = R.drawable.ic_moon1;

        switch (title)
        {
            case "New Moon":
                iconReference = R.drawable.ic_moon1;
                break;
            case "Waxing Crescent":
                iconReference = R.drawable.ic_moon1;
                break;
            case "First Quarter":
                iconReference = R.drawable.ic_moon1;
                break;
            case "Waxing Gibbous":
                iconReference = R.drawable.ic_moon1;
                break;
            case "Full Moon":
                iconReference = R.drawable.ic_moon1;
                break;
            case "Waning Gibbous":
                iconReference = R.drawable.ic_moon1;
                break;
            case "Last Quarter":
                iconReference = R.drawable.ic_moon1;
                break;
            case "Waning Crescent":
                iconReference = R.drawable.ic_moon1;
                break;
        }

        this.icon = getResources().getDrawable(iconReference, null);
    }

    private Solunar tryCasting(Object data)
    {
        Solunar solunar;
        try {
            solunar = (Solunar) data;
        }
        catch (ClassCastException e) {
            solunar = new Solunar(new LatLng(0,0));
            // TODO report some error
        }
        return solunar;
    }
}
