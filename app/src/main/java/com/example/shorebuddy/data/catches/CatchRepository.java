package com.example.shorebuddy.data.catches;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface CatchRepository {
    LiveData<List<CatchRecord>> getCatchRecordsDescending();

    void recordCatch(CatchRecord record);

    void deleteCatch(CatchRecord record);

    void updateCatch(CatchRecord record);
}
