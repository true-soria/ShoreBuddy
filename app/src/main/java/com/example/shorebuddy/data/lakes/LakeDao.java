package com.example.shorebuddy.data.lakes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.shorebuddy.data.relationships.LakeWithFish;

import java.util.List;

@Dao
public interface LakeDao {
    @Query("SELECT name FROM lakes WHERE name LIKE :query ORDER BY name ASC")
    LiveData<List<String>> getFilteredLakes(String query);

    @Query("SELECT name FROM lakes WHERE name LIKE :queryName AND county IN (:queryCounty) ORDER BY name ASC")
    LiveData<List<String>> getFilteredLakesCounty(String queryName, List<String> queryCounty);

    @Transaction
    @Query("SELECT DISTINCT lakes.name " +
            "FROM lakes INNER JOIN LakeFishCrossRef ON lakes.name = LakeFishCrossRef.name " +
            "WHERE lakes.name LIKE :queryName AND LakeFishCrossRef.species IN (:querySpecies)")
    LiveData<List<String>> getFilteredLakesSpecies(String queryName, List<String> querySpecies);

    @Transaction
    @Query("SELECT DISTINCT lakes.name from lakes INNER JOIN LakeFishCrossRef ON lakes.name = LakeFishCrossRef.name " +
            "WHERE lakes.name LIKE :queryName AND (LakeFishCrossRef.species IN (:querySpecies)) AND (lakes.county IN (:queryCounties))")
    LiveData<List<String>> getFilteredLakes(String queryName, List<String> querySpecies, List<String> queryCounties);

    @Transaction
    @Query("SELECT * FROM lakes WHERE name LIKE :query LIMIT 1")
    LiveData<LakeWithFish> findLakeWithFish(String query);

    @Query("SELECT * FROM lakes WHERE name = :lakeName")
    LiveData<Lake> findLake(String lakeName);

    @Query("SELECT DISTINCT county FROM lakes WHERE county != '' ORDER BY county ASC")
    LiveData<List<String>> getAllCounties();
}
