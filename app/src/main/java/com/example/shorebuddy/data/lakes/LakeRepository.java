package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.relationships.LakeWithFish;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

// TODO: Implement LakeRepository Interface
public interface LakeRepository {
    LiveData<List<Lake>> getAllLakes();
    LiveData<List<Lake>> getFilteredLakes(SearchQuery search);
    LiveData<List<Fish>> getFishInLake(Lake lake);
}
