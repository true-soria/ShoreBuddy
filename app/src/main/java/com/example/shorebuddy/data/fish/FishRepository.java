package com.example.shorebuddy.data.fish;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface FishRepository {
    LiveData<List<Fish>> getAllFish();
}
