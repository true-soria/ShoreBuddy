package com.example.shorebuddy.data;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

public interface LakeRepository {
    LiveData<List<Lake>> getAllLakes();

    LiveData<List<Lake>> getFilteredLakes(SearchQuery search);
}

// TODO: Implement the LakeRepository interface
