package com.example.shorebuddy.views.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.HomepageAdapter;
import com.example.shorebuddy.viewmodels.LakeSelect.LakeSelectResultViewModel;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class HomepageView extends Fragment {

    private LakeWidget lakeWidget;
    private FishWidget fishWidget;
    private SolunarWidget solunarWidget;
    private WeatherWidget weatherWidget;
    private MainViewModel mainViewModel;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
        lakeWidget = new LakeWidget(getContext());
        fishWidget = new FishWidget(getContext());
        solunarWidget = new SolunarWidget(getContext());
        weatherWidget = new WeatherWidget(getContext());
    }

    private List<ModuleWidget> getWidgets() {
        List<ModuleWidget> list = new ArrayList<>();
        list.add(lakeWidget);
        list.add(fishWidget);
        list.add(weatherWidget);
        list.add(solunarWidget);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage, container, false);

        setup(rootView);
        return rootView;
    }

    private void setup(View rootView) {
        Activity activity = getActivity();
        RecyclerView recyclerView = rootView.findViewById(R.id.homepage_recycler);
        HomepageAdapter adapter = new HomepageAdapter(getWidgets());

        mainViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(), currentLake -> {
                lakeWidget.setData(currentLake);
                adapter.setWidgets(getWidgets());
        });

        mainViewModel.getFishInCurrentLake().observe(getViewLifecycleOwner(), fish -> {
            fishWidget.setData(fish);
            adapter.setWidgets(getWidgets());
        });

        mainViewModel.getSolunarData().observe(getViewLifecycleOwner(), solunar -> {
            solunarWidget.setData(solunar);
            adapter.setWidgets(getWidgets());
        });

        mainViewModel.getWeatherData().observe(getViewLifecycleOwner(), weather -> {
            weatherWidget.setData(weather);
            adapter.setWidgets(getWidgets());
        });

        FloatingActionButton selectLakeButton = rootView.findViewById(R.id.selectLakeBtn);
        selectLakeButton.setOnClickListener((v) -> {
            LakeSelectResultViewModel lakeViewModel = new ViewModelProvider(getActivity()).get(LakeSelectResultViewModel.class);
            lakeViewModel.setLakeSelectedCallback(mainViewModel);
            NavDirections action = HomepageViewDirections.actionHomepageViewToLakeSelectFragment();
            findNavController(this).navigate(action);
        });

        adapter.setWidgets(getWidgets());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
}
