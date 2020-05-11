package com.example.shorebuddy.viewmodels.LakeSelect;

import androidx.lifecycle.ViewModel;

import com.example.shorebuddy.data.lakes.Lake;

public class LakeSelectResultViewModel extends ViewModel {
    private OnLakeSelected lakeSelectedCallback;

    public void setLakeSelectedCallback(OnLakeSelected lakeSelectedCallback) {
        this.lakeSelectedCallback = lakeSelectedCallback;
    }

    public void setResultLake(Lake lake) {
        lakeSelectedCallback.onLakeSelected(lake);
    }

    public interface OnLakeSelected {
        void onLakeSelected(Lake lake);
    }
}
