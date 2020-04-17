package com.example.shorebuddy.views;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class MainFragment extends Fragment {

    private MainViewModel mainViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        TextView currentLakeTextView = rootView.findViewById(R.id.current_lake_text);
        TextView fishTextView = rootView.findViewById(R.id.fishList_Text);

        mainViewModel.getFishInCurrentLake().observe(getViewLifecycleOwner(),
                fishInLake -> {
                    StringBuilder fishDisplay = new StringBuilder();
                    for (Fish fish : fishInLake) {
                        fishDisplay.append("\n").append(fish.species);
                    }
                    fishTextView.setText(String.format("Fish Species Present:\n%s", fishDisplay.toString()));
                }
        );

        mainViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(),
                lake -> currentLakeTextView.setText(String.format("%s", lake.lakeName)));

        //TODO remove timestamp
        TextView currentWeatherTextView = rootView.findViewById(R.id.last_updated_weather_text);
        WeatherView weatherView = rootView.findViewById(R.id.current_weather_text);
        SolunarDisplayView solunarView = rootView.findViewById(R.id.current_solunar);
        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.refresh_layout);

        mainViewModel.getUpdatingStatus().observe(getViewLifecycleOwner(), refreshLayout::setRefreshing);

        mainViewModel.getWeatherData().observe(getViewLifecycleOwner(), weather -> {
            currentWeatherTextView.setText(String.format(Locale.US, "Weather Timestamp: %s", weather.getTimeStamp().getTime()));
            weatherView.set_weather(weather);
            mainViewModel.weatherUpdated();
        });

        mainViewModel.getSolunarData().observe(getViewLifecycleOwner(), solunar -> {
            solunarView.setSolunar(solunar);
            mainViewModel.solunarUpdated();
        });

        mainViewModel.getToastData().observe(getViewLifecycleOwner(),
                resourceId -> Toast.makeText(getContext(), getResources().getString(resourceId), Toast.LENGTH_LONG).show());


        Button selectLakeBtn = rootView.findViewById(R.id.select_lake_btn);
        selectLakeBtn.setOnClickListener(this::onClickSelectLakeBtn);

        refreshLayout.setOnRefreshListener(() -> {
            mainViewModel.requestWeatherUpdate();
            mainViewModel.requestSolunarUpdate();
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void onClickSelectLakeBtn(View v) {
        NavDirections action = MainFragmentDirections.actionMainFragmentToLakeSelectFragment();
        findNavController(this).navigate(action);
    }

    private void onClickSelectCameraBtn(View v){
//        NavDirections action = MainFragmentDirections.actionMainFragmentToCameraFragment2();
//        findNavController(this).navigate(action);
    }

    private void onClickSelectCatchesBtn(View v){
//        NavDirections action = MainFragmentDirections.actionMainFragmentToCatchRecordsFragment3();
//        findNavController(this).navigate(action);
    }

}
