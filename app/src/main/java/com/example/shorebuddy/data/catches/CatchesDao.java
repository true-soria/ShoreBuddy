package com.example.shorebuddy.data.catches;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

import java.util.List;

@Dao
public interface CatchesDao {
    @Insert
    long insert(CatchRecord record);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CatchPhoto photo);

    @Update
    void update(CatchRecord record);

    @Delete
    void delete(CatchRecord record);

    @Query("SELECT * FROM CatchRecords ORDER BY timeCaught DESC")
    LiveData<List<CatchRecord>> getAllRecordsDescending();

    @Query("SELECT * FROM CatchRecords WHERE lake=:lake AND fish IN (:fish) ORDER BY timeCaught DESC")
    LiveData<List<CatchRecord>> getRecordsFilteredLakeFish(String lake, List<String> fish);

    @Query("SELECT * FROM CatchRecords WHERE lake=:lake ORDER BY timeCaught DESC")
    LiveData<List<CatchRecord>> getRecordsFilteredLake(String lake);

    @Query("SELECT * FROM CatchRecords WHERE fish IN (:fish) ORDER BY timeCaught DESC")
    LiveData<List<CatchRecord>> getRecordsFilteredFish(List<String> fish);

    @Transaction
    @Query("SELECT * FROM CatchRecords WHERE uid=:uid")
    LiveData<CatchRecordWithPhotos> getCatchRecordWithPhotos(int uid);

    @Query("SELECT uid from CatchRecords WHERE rowid=:rowId")
    int getCatchRecordIdFromRow(long rowId);
}
