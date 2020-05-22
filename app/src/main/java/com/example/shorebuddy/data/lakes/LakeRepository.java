package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.fish.Fish;

import java.util.List;

// TODO: Implement LakeRepository Interface
public interface LakeRepository {
    LiveData<List<String>> getFilteredLakes(LakeFilter search);
    LiveData<List<Fish>> getFishInLake(Lake lake);
    LiveData<List<String>> getAllCounties();
    LiveData<Lake> getLake(String lakeName);
}
