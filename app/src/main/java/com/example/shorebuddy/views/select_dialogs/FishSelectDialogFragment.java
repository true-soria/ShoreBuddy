package com.example.shorebuddy.views.select_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shorebuddy.adapters.BasicFilterAdapter;

import java.util.List;

public class FishSelectDialogFragment extends SelectDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogSelectResultViewModel.setData(dialogSelectViewModel.getAllSpecies());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    void setupDataObservation(BasicFilterAdapter adapter) {
        dialogSelectResultViewModel.getData().observe(getViewLifecycleOwner(), fish -> {
            adapter.setData(fish);
            this.data = fish;
        });
    }

    @Override
    void setResult(List<String> data) {
        dialogSelectResultViewModel.setResult(data);
    }
}
