package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.weather.Weather;
import com.example.shorebuddy.utilities.Converters;

import java.util.Locale;


public class WeatherWidget extends ModuleWidget {

    static final String WEATHER = "weather";
    private final String bottomPropertyNameValue = "Wind Speed";
    private Drawable icon;
    private String title;

    private final WidgetIconText leftProperty;
    private final WidgetIconText rightProperty;
    private final TextView bottomPropertyName;
    private final TextView bottomPropertyValue;

    public WeatherWidget(Context context) { this(context, null); }

    public WeatherWidget(Context context, AttributeSet attrs) { this(context, attrs, 0); }

    public WeatherWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.weather_widget, this);

        leftProperty = findViewById(R.id.left_property);
        rightProperty = findViewById(R.id.right_property);
        bottomPropertyName = findViewById(R.id.bottom_property_name);
        bottomPropertyValue = findViewById(R.id.bottom_property_value);

        this.icon = getResources().getDrawable(R.drawable.ic_compass_rose,null);
        this.title = String.format(Locale.US, "%s", WEATHER);
    }

    @Override
    public Drawable getIcon() {
        return icon;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setData(Weather weather)
    {
        setDataAndIcon(weather);

        this.leftProperty.setData(findProperty(weather, leftProperty.getPropertyValueID()));
        this.rightProperty.setData(findProperty(weather,rightProperty.getPropertyValueID()));
        this.bottomPropertyName.setText(String.format(Locale.US, "%s", bottomPropertyNameValue));
        this.bottomPropertyValue.setText(String.format(Locale.US, "%.2f mph @ %s",
                weather.windSpeed,
                getHeading(weather.windDirection)));
    }

    private String findProperty(Weather weather, WidgetTextGenerator.propertyValues valueID)
    {
        String propertyValue = WEATHER;
        switch (valueID)
        {
            case TEMPERATURE:
                propertyValue = String.format(Locale.US, "%.2f° F", weather.temperature);
                break;
            case PRESSURE:
                propertyValue = String.format(Locale.US, "%d hPa", weather.pressure);
                break;
            case HUMIDITY:
                propertyValue = String.format(Locale.US, "%d%%", weather.humidity);
                break;
            case MAIN:
                propertyValue = String.format(Locale.US, "%s", weather.main);
                break;
            case DESCRIPTION:
                propertyValue = String.format(Locale.US, "%s", weather.description);
                break;
            case WIND_SPEED:
                propertyValue = String.format(Locale.US, "%.2f mph", weather.windSpeed);
                break;
            case WIND_DIRECTION:
                propertyValue = getHeading(weather.windDirection);
                break;
        }

        return propertyValue;
    }

    private String getHeading(int windDirection)
    {
        if (windDirection == 0)
            return "";
        else
            return String.format(Locale.US, "%d° %s",
                    windDirection, Converters.degreesToCardinalDirection(windDirection));
    }

    private void setDataAndIcon(Weather weather)
    {
        title = String.format(Locale.US, "%s", weather.description);
        // TODO pull weather icons into project
        this.icon = getResources().getDrawable(R.drawable.ic_sunny_day,null);
    }
}
