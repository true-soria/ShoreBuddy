package com.example.shorebuddy.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class LakeFragment extends Fragment {

    private MainViewModel mainViewModel;
    private GoogleMap mapAPI;
    private SupportMapFragment mapView;

    public LakeFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_lake, container, false);
        mapView = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAPI);
        assert mapView != null;
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mapAPI = googleMap;
                UiSettings mapSetting = mapAPI.getUiSettings();
                mapSetting.setZoomControlsEnabled(true);
                mapAPI.clear();
                Lake currentLake = Objects.requireNonNull(mainViewModel.getCurrentlySelectedLake().getValue());
                mapAPI.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                CameraPosition googlePLex = CameraPosition.builder().target(currentLake.getLatLng()).zoom(14).bearing(0).tilt(45).build();
                mapAPI.animateCamera(CameraUpdateFactory.newCameraPosition(googlePLex), 1000, null);
                mapAPI.addMarker(new MarkerOptions().position(currentLake.getLatLng()).title(currentLake.lakeName));
            }
        });
        renderSelectedLake(rootView);
        return rootView;
    }

    private void renderSelectedLake(@NotNull View rootView) {
        TextView currentLakeTextView = rootView.findViewById(R.id.current_lake_text);
        mainViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(), currentLake ->
                currentLakeTextView.setText(String.format("%s", currentLake.lakeName)));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
