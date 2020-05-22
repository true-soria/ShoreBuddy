package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.fish.DefaultFishRepository;
import com.example.shorebuddy.data.fish.FishRepository;
import com.example.shorebuddy.data.lakes.DefaultLakeRepository;
import com.example.shorebuddy.data.lakes.LakeRepository;

import java.util.List;

public class DialogSelectViewModel extends AndroidViewModel {

    private final LiveData<List<String>> counties;
    private final LiveData<List<String>> species;

    public DialogSelectViewModel(@NonNull Application application) {
        super(application);
        LakeRepository lakeRepository = new DefaultLakeRepository(application);
        FishRepository fishRepository = new DefaultFishRepository(application);
        counties = lakeRepository.getAllCounties();
        species = fishRepository.getAllFishSpecies();
    }

    public LiveData<List<String>> getAllCounties() {
        return counties;
    }

    public LiveData<List<String>> getAllSpecies() {
        return species;
    }
}
