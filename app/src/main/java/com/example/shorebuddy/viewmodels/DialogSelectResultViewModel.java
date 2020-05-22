package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DialogSelectResultViewModel extends AndroidViewModel {
    private OnDialogDataSaved selectedCallback;
    private LiveData<List<String>> data;

    public DialogSelectResultViewModel(@NotNull  Application application) {
        super(application);
    }

    public LiveData<List<String>> getData() {
        return data;
    }

    public void setData(LiveData<List<String>> data) {
        this.data = data;
    }

    public void setSelectedCallback(OnDialogDataSaved selectedCallback) {
        this.selectedCallback = selectedCallback;
    }

    public void setResult(List<String> data) {
        selectedCallback.onSelected(data);
    }

    public void reset() {
        data = null;
        selectedCallback = null;
    }

    public interface OnDialogDataSaved {
        void onSelected(List<String> data);
    }
}
