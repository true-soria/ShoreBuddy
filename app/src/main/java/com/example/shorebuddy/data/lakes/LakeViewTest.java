package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LakeViewTest extends AndroidViewModel {

    private DefaultLakeRepository lakeRepository;
    private LiveData<List<Lake>> allLakes;

    public LakeViewTest(@NonNull Application application) {
        super(application);
        this.lakeRepository = new DefaultLakeRepository(application);
        allLakes = lakeRepository.getAllLakes();
    }

    LiveData<List<Lake>> getAllLakes(){return allLakes;}

    public void insert(Lake name){lakeRepository.insert(name);}
}
