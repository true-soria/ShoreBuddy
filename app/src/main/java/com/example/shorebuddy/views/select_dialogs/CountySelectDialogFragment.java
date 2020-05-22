package com.example.shorebuddy.views.select_dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shorebuddy.R;
import com.example.shorebuddy.adapters.BasicFilterAdapter;

import java.util.List;

public class CountySelectDialogFragment extends SelectDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogSelectResultViewModel.setData(dialogSelectViewModel.getAllCounties());
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        binding.titleText.setText(R.string.counties_title);
        return rootView;
    }
    @Override
    void setupDataObservation(BasicFilterAdapter adapter) {
        dialogSelectResultViewModel.getData().observe(getViewLifecycleOwner(), counties -> {
            adapter.setData(counties);
            this.data = counties;
        });
    }

    @Override
    void setResult(List<String> data) {
        dialogSelectResultViewModel.setResult(data);
    }
}
