package com.example.shorebuddy.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.shorebuddy.R;


public class CameraFragment extends Fragment {

    private ImageView pictureTaken;
    private Button takePictureButton;
    private Button weatherButton;
    private Button catchRecordsButton;
    private static final int REQUEST_IMAGE_TAKEN = 101;

    public CameraFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        pictureTaken = view.findViewById(R.id.imageTaken);
//        takePictureButton = view.findViewById(R.id.takePictureButton);
//        weatherButton = view.findViewById(R.id.mainFragment);
//        catchRecordsButton = view.findViewById(R.id.catchesButton);
//        final NavController navController = Navigation.findNavController(view);
//
//        weatherButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // navController.navigate(R.id.action_cameraFragment_to_mainFragment);
//            }
//        });
//
//        catchRecordsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               // navController.navigate(R.id.action_cameraFragment_to_catchRecordsFragment);
//            }
//        });
//    }

//    public void takePicture(View view) {
//        Intent imageTakenIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        if (imageTakenIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(imageTakenIntent, REQUEST_IMAGE_TAKEN);
//        }
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_TAKEN && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            pictureTaken.setImageBitmap(imageBitmap);
//        }
//    }


}
