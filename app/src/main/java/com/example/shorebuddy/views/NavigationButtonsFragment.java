package com.example.shorebuddy.views;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shorebuddy.CameraActivity;
import com.example.shorebuddy.CatchesActivity;
import com.example.shorebuddy.MainActivity;
import com.example.shorebuddy.R;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Objects;

public class NavigationButtonsFragment extends Fragment {
    private Button weatherButton;
    private Button catchesButton;
    private Button cameraButton;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bottom_navigation_buttons, container, false);
        weatherButton = rootView.findViewById(R.id.mainActivityButton);
        cameraButton = rootView.findViewById(R.id.cameraButton);
        catchesButton = rootView.findViewById(R.id.catchesButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(getActivity(), CameraActivity.class);
                startActivity(cameraIntent);
            }
        });

        catchesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent catchesIntent = new Intent(getActivity(), CatchesActivity.class);
                startActivity(catchesIntent);
            }
        });

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(getActivity(),MainActivity.class);
                startActivity(weatherIntent);
            }
        });

        return rootView;
    }
}
