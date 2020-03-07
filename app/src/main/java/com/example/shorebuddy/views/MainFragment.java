package com.example.shorebuddy.views;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shorebuddy.R;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.Locale;
import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);

        TextView currentLakeTextView = rootView.findViewById(R.id.current_lake_text);
        mViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(),
                lake -> currentLakeTextView.setText(String.format("%s%s", getString(R.string.current_lake_text), lake.name)));

        //TODO remove timestamp
        TextView currentWeatherTextView = rootView.findViewById(R.id.last_updated_weather_text);
        WeatherView weatherView = rootView.findViewById(R.id.current_weather_text);
        mViewModel.getWeatherData().observe(getViewLifecycleOwner(), weather -> {
            currentWeatherTextView.setText(String.format(Locale.US, "Weather Timestamp: %s", weather.getTimeStamp().getTime()));
            weatherView.set_weather(weather);
        });

        mViewModel.getToastData().observe(getViewLifecycleOwner(),
                resourceId -> Toast.makeText(getContext(), getResources().getString(resourceId), Toast.LENGTH_LONG).show());


        Button selectLakeBtn = rootView.findViewById(R.id.select_lake_btn);
        selectLakeBtn.setOnClickListener(this::onClickSelectLakeBtn);

        Button refreshWeatherUpdateBtn = rootView.findViewById(R.id.weather_refresh_btn);
        refreshWeatherUpdateBtn.setOnClickListener(view -> mViewModel.requestWeatherUpdate());
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

}
