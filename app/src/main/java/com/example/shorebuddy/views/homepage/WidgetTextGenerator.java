package com.example.shorebuddy.views.homepage;

public interface WidgetTextGenerator {
    
    enum propertyValues {
        MOON_CYCLE, MOON_RISE, MOON_TRANSIT, MOON_SET,
        MINOR1, MINOR2, MAJOR1, MAJOR2, DAY_RATING,
        TEMPERATURE, PRESSURE, HUMIDITY, MAIN,
        DESCRIPTION, WIND_SPEED, WIND_DIRECTION
    }

    propertyValues getPropertyValueID();

    void setData(String data);
}
