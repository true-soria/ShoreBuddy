package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

// TODO: Implement LakeRepository Interface
public interface LakeRepository {
    LiveData<List<Lake>> getAllLakes();

    LiveData<List<Lake>> getFilteredLakes(SearchQuery search);
}
