package com.example.shorebuddy.views.homepage;

import android.graphics.drawable.Drawable;

public interface ModuleWidget {

    Drawable getIcon();

    String getTitle();

    // TODO setData method that uses MainViewModel across all
    // void setData(MainViewModel mainViewModel);
}
