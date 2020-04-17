package com.example.shorebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CatchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catches);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    case R.id.weatherIcon:
//                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.catchRecordIcon:
//                        startActivity(new Intent(getApplicationContext(),CatchesActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.cameraIcon:
//                        startActivity(new Intent(getApplicationContext(),CameraActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
                return false;
            }
        });
    }
}
