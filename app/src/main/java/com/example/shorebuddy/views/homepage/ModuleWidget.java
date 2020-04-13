package com.example.shorebuddy.views.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.constraintlayout.widget.ConstraintLayout;

public abstract class ModuleWidget extends ConstraintLayout{

    public Drawable icon;
    public String title;

    public ModuleWidget(Context context) { this(context, null); }

    public ModuleWidget(Context context, AttributeSet attrs) { this(context, attrs, 0); }

    public ModuleWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
