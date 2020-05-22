package com.example.shorebuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import com.example.shorebuddy.utilities.BottomViewToggle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomViewToggle {
    BottomNavigationView bottomNavigationView;
    boolean bottomViewEnabled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setUpNavigation();
        getSupportActionBar().hide();
        ConstraintLayout constraintLayout = findViewById(R.id.container);
        constraintLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            constraintLayout.getWindowVisibleDisplayFrame(rect);
            int screenHeight = constraintLayout.getRootView().getHeight();
            int keypadHeight = screenHeight - rect.bottom;
            if (keypadHeight > screenHeight * 0.15 && bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.GONE);
            } else if (bottomViewEnabled && bottomNavigationView != null) {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setUpNavigation(){
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.getNavController());
        disableBottomNavigationView();
    }

    @Override
    public void disableBottomNavigationView() {
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.GONE);
            bottomViewEnabled = false;
        }
    }

    @Override
    public void enableBottomNavigationView() {
        if (!bottomViewEnabled && bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomViewEnabled = true;
        }
    }
}
