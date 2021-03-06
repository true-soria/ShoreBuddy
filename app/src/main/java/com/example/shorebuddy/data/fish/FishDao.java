package com.example.shorebuddy.data.fish;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shorebuddy.data.relationships.FishWithLakes;

import java.util.List;

@Dao
public interface FishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Fish fish);

    @Query("SELECT * FROM fish ORDER BY species ASC")
    LiveData<List<Fish>> getAllFish();

    @Query("SELECT * FROM fish WHERE species LIKE :species")
    LiveData<List<Fish>> findFish(String species);

    @Transaction
    @Query("SELECT * FROM fish")
    LiveData<List<FishWithLakes>> getFishWithLakes();

    @Transaction
    @Query("SELECT * FROM fish WHERE species LIKE :species")
    LiveData<List<FishWithLakes>> findFishWithLakes(String species);

    @Query("SELECT * FROM fish WHERE species=:species")
    LiveData<Fish> getFish(String species);

    @Query("SELECT species FROM fish")
    LiveData<List<String>> getAllFishSpecies();
}
