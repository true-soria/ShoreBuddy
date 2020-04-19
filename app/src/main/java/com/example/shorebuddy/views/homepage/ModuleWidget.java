package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class ModuleWidget extends ConstraintLayout {

    public ModuleWidget(Context context) {
        super(context);
    }

    public ModuleWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ModuleWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract Drawable getIcon();

    public abstract String getTitle();

    // TODO setData method that uses MainViewModel across all
    // void setData(MainViewModel mainViewModel);
}
