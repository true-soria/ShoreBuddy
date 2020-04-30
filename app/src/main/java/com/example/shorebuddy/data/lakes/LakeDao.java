package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shorebuddy.data.relationships.LakeWithFish;

import java.util.List;

@Dao
public interface LakeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Lake lake);

    @Query("SELECT * FROM lakes ORDER BY name ASC")
    LiveData<List<Lake>> getAll();

    @Query("SELECT * FROM lakes WHERE name LIKE :query ORDER BY name ASC")
    LiveData<List<Lake>> getFilteredLakes(String query);

    @Transaction
    @Query("SELECT * FROM lakes WHERE name LIKE :query LIMIT 1")
    LiveData<LakeWithFish> findLakeWithFish(String query);
}
