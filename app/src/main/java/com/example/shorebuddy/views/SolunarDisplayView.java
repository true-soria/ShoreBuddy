package com.example.shorebuddy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.solunar.Solunar;

import java.util.Locale;

public class SolunarDisplayView extends ConstraintLayout {
    public SolunarDisplayView(Context context) {
        this(context, null);
    }

    public SolunarDisplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SolunarDisplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.solunar_display, this);
    }

    public void setSolunar(Solunar solunar) {
        View rootView = getRootView();
        TextView text = rootView.findViewById(R.id.solunar_text_view);
        text.setText(String.format(Locale.US, "Sun Rise: %s\nSun Set: %s\n", solunar.sunRise, solunar.sunSet));
    }
}
