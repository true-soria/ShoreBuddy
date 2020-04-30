package com.example.shorebuddy.data.catches;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

import java.util.List;

public interface CatchRepository {
    LiveData<List<CatchRecord>> getCatchRecordsDescending();

    LiveData<CatchRecordWithPhotos> getCatchRecordWithPhotos(int uid);

    void recordCatch(CatchRecordWithPhotos record);

    void deleteCatch(CatchRecord record);

    void updateCatch(CatchRecordWithPhotos record);

    CatchRecordWithPhotos getCatchRecordWithPhotosSync(int uid);
}
