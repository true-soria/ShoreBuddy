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

import org.jetbrains.annotations.NotNull;

import java.util.List;
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

        renderSelectedLake(rootView);
        renderWeather(rootView);
        renderSolunar(rootView);
        renderFishInLake(rootView);
        setupRefreshLayout(rootView);
        setupLakeSelectBtn(rootView);
        setupToast();

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

    private void renderSelectedLake(@NotNull View rootView) {
        TextView currentLakeTextView = rootView.findViewById(R.id.current_lake_text);
        mainViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(), currentLake ->
                currentLakeTextView.setText(String.format("%s", currentLake.lakeName)));
    }

    private void renderFishInLake(@NotNull View rootView) {
        TextView fishTextView = rootView.findViewById(R.id.fishList_Text);
        mainViewModel.getFishInCurrentLake().observe(getViewLifecycleOwner(), fishInLake -> {
            String fishText = createFishText(fishInLake);
            fishTextView.setText(String.format("Fish Species Present:\n%s", fishText));
        });
    }

    private void renderWeather(@NotNull View rootView) {
        WeatherView weatherView = rootView.findViewById(R.id.current_weather_text);
        TextView currentWeatherTextView = rootView.findViewById(R.id.last_updated_weather_text);
        mainViewModel.getWeatherData().observe(getViewLifecycleOwner(), weather -> {
            currentWeatherTextView.setText(String.format(Locale.US, "Weather Timestamp: %s", weather.getTimeStamp().getTime()));
            weatherView.set_weather(weather);
            mainViewModel.weatherUpdated();
        });
    }

    private void renderSolunar(@NotNull View rootView) {
        SolunarDisplayView solunarView = rootView.findViewById(R.id.current_solunar);
        mainViewModel.getSolunarData().observe(getViewLifecycleOwner(), solunar -> {
            solunarView.setSolunar(solunar);
            mainViewModel.solunarUpdated();
        });
    }

    private void setupRefreshLayout(@NotNull View rootView) {
        SwipeRefreshLayout refreshLayout = rootView.findViewById(R.id.refresh_layout);
        mainViewModel.getUpdatingStatus().observe(getViewLifecycleOwner(), refreshLayout::setRefreshing);
        refreshLayout.setOnRefreshListener(() -> {
            mainViewModel.requestWeatherUpdate();
            mainViewModel.requestSolunarUpdate();
        });
    }

    private void setupLakeSelectBtn(@NotNull View rootView) {
        Button selectLakeBtn = rootView.findViewById(R.id.select_lake_btn);
        selectLakeBtn.setOnClickListener(this::onClickSelectLakeBtn);
    }

    private void setupToast() {
        mainViewModel.getToastData().observe(getViewLifecycleOwner(),
                resourceId -> Toast.makeText(getContext(), getResources().getString(resourceId), Toast.LENGTH_LONG).show());
    }

    @NotNull
    private String createFishText(@NotNull List<Fish> fishInLake) {
        StringBuilder fishText = new StringBuilder();
        for (Fish fish : fishInLake) {
            fishText.append("\n").append(fish.species);
        }
        return fishText.toString();
    }
}
