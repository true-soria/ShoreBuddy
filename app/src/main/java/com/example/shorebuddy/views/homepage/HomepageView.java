package com.example.shorebuddy.views.homepage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomepageView extends Fragment {

    private List<Drawable> icons = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<ConstraintLayout> widgets = new ArrayList<>();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWidgets();
    }

    public void initWidgets() {
        // TODO: widgets for all module types
    }
}
