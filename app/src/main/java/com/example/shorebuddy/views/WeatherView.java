package com.example.shorebuddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.shorebuddy.R;
import com.example.shorebuddy.data.weather.Weather;
import com.example.shorebuddy.utilities.NetworkAccessor;

import java.util.Locale;

public class WeatherView extends ConstraintLayout {
    private final ImageLoader imageLoader;
    public WeatherView(Context context) {
        this(context, null);
    }

    public WeatherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        imageLoader = NetworkAccessor.getInstance().getImageLoader();
        inflate(getContext(), R.layout.weather_control_layout, this);
    }

    public void set_weather(Weather new_weather) {
        // TODO: format weather data strings elsewhere
        View rootView = getRootView();
        TextView temp = rootView.findViewById(R.id.temp_text_view);
        temp.setText(String.format(Locale.US, "%.2f°F", new_weather.temperature));
        WeatherAttributeView pressureView = rootView.findViewById(R.id.pressure_view);
        pressureView.setData(String.format(Locale.US, "%d hPa", new_weather.pressure));
        WeatherAttributeView humidityView = rootView.findViewById(R.id.humidity_view);
        humidityView.setData(String.format(Locale.US, "%d%%", new_weather.humidity));
        WeatherAttributeView windSpeedView = rootView.findViewById(R.id.wind_speed_view);
        windSpeedView.setData(String.format(Locale.US, "%.2f mph", new_weather.windSpeed));
        WeatherAttributeView windDirView = rootView.findViewById(R.id.wind_dir_view);
        windDirView.setData(String.format(Locale.US, "%d°", new_weather.windDirection));
        NetworkImageView weatherIcon = rootView.findViewById(R.id.weather_icon_image_view);
        // TODO pull weather icons into project
        if (!new_weather.iconPath.isEmpty()) {
            String s = String.format("https://openweathermap.org/img/wn/%s@2x.png", new_weather.iconPath);
            weatherIcon.setBackground(getResources().getDrawable(R.drawable.rounded_square_blue, null));
            weatherIcon.setImageUrl(s, imageLoader);
        }
    }
}
