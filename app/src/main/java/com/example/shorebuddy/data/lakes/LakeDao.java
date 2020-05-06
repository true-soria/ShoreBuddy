package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shorebuddy.data.relationships.LakeWithFish;

import java.util.List;

@Dao
public interface LakeDao {
    @Query("SELECT * FROM lakes WHERE name LIKE :query ORDER BY name ASC")
    LiveData<List<Lake>> getFilteredLakes(String query);

    @Query("SELECT * FROM lakes WHERE name LIKE :queryName AND county == :queryCounty ORDER BY name ASC")
    LiveData<List<Lake>> getFilteredLakes(String queryName, String queryCounty);

    @Transaction
    @Query("SELECT * FROM lakes WHERE name LIKE :query LIMIT 1")
    LiveData<LakeWithFish> findLakeWithFish(String query);

    @Query("SELECT DISTINCT (county) FROM lakes WHERE county != '' ORDER BY county ASC")
    LiveData<List<String>> getAllCounties();
}
