package com.example.shorebuddy.viewmodels.fish_select;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.fish.DefaultFishRepository;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.fish.FishRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FishSelectResultViewModel extends AndroidViewModel {
    private OnFishFilterSaved fishSelectedCallback;
    private LiveData<List<Fish>> fish;

    public FishSelectResultViewModel(@NotNull  Application application) {
        super(application);
        FishRepository fishRepository = new DefaultFishRepository(application);
        fish = fishRepository.getAllFish();
    }

    public LiveData<List<Fish>> getFish() {
        return fish;
    }

    public void setFishSelectedCallback(OnFishFilterSaved fishSelectedCallback) {
        this.fishSelectedCallback = fishSelectedCallback;
    }

    public void setResultFish(List<Fish> fish) {
        fishSelectedCallback.onFishSelected(fish);
    }

    public interface OnFishFilterSaved {
        void onFishSelected(List<Fish> fish);
    }
}
