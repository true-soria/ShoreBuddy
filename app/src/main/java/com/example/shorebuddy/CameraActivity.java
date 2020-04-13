package com.example.shorebuddy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {

    private ImageView pictureTaken;
    private static final int REQUEST_IMAGE_TAKEN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        pictureTaken = findViewById(R.id.imageTaken);
    }

    public void takePicture(View view) {
        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (imageTakenIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(imageTakenIntent, REQUEST_IMAGE_TAKEN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_TAKEN && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            pictureTaken.setImageBitmap(imageBitmap);
        }
    }
}
