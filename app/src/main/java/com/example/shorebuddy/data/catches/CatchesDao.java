package com.example.shorebuddy.data.catches;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CatchesDao {
    @Insert
    void insert(CatchRecord record);

    @Update
    void update(CatchRecord record);

    @Delete
    void delete(CatchRecord record);

    @Query("SELECT * FROM CatchRecords ORDER BY uid DESC")
    LiveData<List<CatchRecord>> getAllRecordsDescending();
}
