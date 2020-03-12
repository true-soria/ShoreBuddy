package com.example.shorebuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        readCSVfile();
    }


    private void readCSVfile(){
        InputStream is = getResources().openRawResource(R.raw.fishinglocations_reduced);
        BufferedReader reader = new BufferedReader( new InputStreamReader(is, StandardCharsets.UTF_8));
        String line = null;
        try {
            line = reader.readLine();
            while((line = reader.readLine()) != null){
                String[] values = line.split(",");
                System.out.println("Current line is "+line);
            }
        } catch (IOException e) {
            Log.wtf("My Activity", "Error reading csv file "+ line, e);
            e.printStackTrace();
        }
    }
}
