package com.example.shorebuddy.views.homepage;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.HomepageAdapter;
import com.example.shorebuddy.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomepageView extends Fragment {

    private List<Drawable> icons = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private List<ConstraintLayout> widgets = new ArrayList<>();
    private MainViewModel mainViewModel;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage, container, false);

        initWidgets(rootView);
        initRecyclerView(rootView);
        return rootView;
    }

    private void initWidgets(View rootView) {
        initSolunarWidget(rootView);
        initWeatherWidget(rootView);
    }

    private void initSolunarWidget(View rootView)
    {
        SolunarWidget solunarWidget = new SolunarWidget(getContext());
        // TODO: add data somehow
        // solunarWidget.setData(Some solunar object);
        icons.add(solunarWidget.getIcon());
        titles.add(solunarWidget.getTitle());
        widgets.add(solunarWidget);
    }

    private void initWeatherWidget (View rootView)
    {
        WeatherWidget weatherWidget = new WeatherWidget(getContext());
        // weatherWidget.setData(Some weather object);
        icons.add(weatherWidget.getIcon());
        titles.add(weatherWidget.getTitle());
        widgets.add(weatherWidget);
    }

    private void initRecyclerView(View rootView) {
        Activity activity = getActivity();
        RecyclerView recyclerView = rootView.findViewById(R.id.homepage_recycler);
        HomepageAdapter adapter = new HomepageAdapter(activity, icons, titles, widgets);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }
}
