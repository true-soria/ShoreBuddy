package com.example.shorebuddy.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.catches.CatchPhoto;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

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

    public LiveData<List<CatchPhoto>> getPhotos(int uid){
        LiveData<CatchRecordWithPhotos> catches = catchRepository.getCatchRecordWithPhotos(uid);
        return Transformations.map(catches, catchRecordWithPhotos -> catchRecordWithPhotos.photos);
    }

    public void deleteRecord(CatchRecord record) {
        catchRepository.deleteCatch(record);
    }
}
