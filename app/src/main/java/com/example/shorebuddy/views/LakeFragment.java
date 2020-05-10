package com.example.shorebuddy.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.viewmodels.LakeSelect.LakeSelectResultViewModel;
import com.example.shorebuddy.viewmodels.MainViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

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
        //renderFishInLake(rootView);
        renderSelectedLake(rootView);
        setupLakeSelectBtn(rootView);
        return rootView;
    }


    private void renderFishInLake(@NotNull View rootView) {
        TextView fishTextView = rootView.findViewById(R.id.fishList_Text);
        mainViewModel.getFishInCurrentLake().observe(getViewLifecycleOwner(), fishInLake -> {
            String fishText = createFishText(fishInLake);
            fishTextView.setText(String.format("Fish Species Present:\n%s", fishText));
        });
    }

    private void renderSelectedLake(@NotNull View rootView) {
        TextView currentLakeTextView = rootView.findViewById(R.id.current_lake_text);
        mainViewModel.getCurrentlySelectedLake().observe(getViewLifecycleOwner(), currentLake ->
                currentLakeTextView.setText(String.format("%s", currentLake.lakeName)));
    }

    private void onClickSelectLakeBtn(View v) {
        LakeSelectResultViewModel resultViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(LakeSelectResultViewModel.class);
        resultViewModel.setLakeSelectedCallback(mainViewModel);
        NavDirections action = LakeFragmentDirections.actionLakeFragmentToLakeSelectFragment();
        findNavController(this).navigate(action);
    }

    private void setupLakeSelectBtn(@NotNull View rootView) {
        Button selectLakeBtn = rootView.findViewById(R.id.select_lake_btn);
        selectLakeBtn.setOnClickListener(this::onClickSelectLakeBtn);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
