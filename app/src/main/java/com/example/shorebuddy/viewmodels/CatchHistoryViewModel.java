package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;

import java.util.List;

public class CatchHistoryViewModel extends AndroidViewModel {
    private CatchRepository catchRepository;

    public CatchHistoryViewModel(@NonNull Application application) {
        super(application);
        catchRepository = new DefaultCatchRepository(application);
    }

    public LiveData<List<CatchRecord>> getRecords() {
        return catchRepository.getCatchRecordsDescending();
    }

    public void deleteRecord(CatchRecord record) {
        catchRepository.deleteCatch(record);
    }
}
