package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Lake> lakes);

    @Delete
    void delete(Lake lake);

    @Query("SELECT * FROM lake_table")
    LiveData<List<Lake>> getAll();

    @Query("DELETE FROM lake_table")
    void removeAll();

    @Query("SELECT * FROM lake_table WHERE name LIKE :query")
    LiveData<List<Lake>> getFilteredLakes(String query);

    @Transaction
    @Query("SELECT * FROM lake_table WHERE name LIKE :query")
    List<LakeWithFish> findLakeWithFish(String query);
}
