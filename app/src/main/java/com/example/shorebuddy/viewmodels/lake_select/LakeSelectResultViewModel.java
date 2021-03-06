package com.example.shorebuddy.viewmodels.lake_select;

import androidx.lifecycle.ViewModel;

import com.example.shorebuddy.data.lakes.Lake;

public class LakeSelectResultViewModel extends ViewModel {
    private OnLakeSelected lakeSelectedCallback;

    public void setLakeSelectedCallback(OnLakeSelected lakeSelectedCallback) {
        this.lakeSelectedCallback = lakeSelectedCallback;
    }

    public void setResultLake(String lake) {
        lakeSelectedCallback.onLakeSelected(lake);
    }

    public interface OnLakeSelected {
        void onLakeSelected(String lake);
    }
}
