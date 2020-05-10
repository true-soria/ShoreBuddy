package com.example.shorebuddy.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.catches.CatchPhoto;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.lang.String.*;

public class CatchRecordDisplayViewModel extends AndroidViewModel {

    private CatchRepository catchRepo;
    public MutableLiveData<CatchRecordWithPhotos> record = new MutableLiveData<>(new CatchRecordWithPhotos());
    public LiveData<List<CatchPhoto>> photos = Transformations.map(record, r -> r.photos);
    public LiveData<String> recordSpecies = Transformations.map(record, r -> r.record.fish);
    public LiveData<String> recordLake = Transformations.map(record, r -> r.record.lake);
    public LiveData<String> recordWeight = Transformations.map(record, r -> format(Locale.US, "%f", r.record.weight));
    public LiveData<String> recordLength = Transformations.map(record, r -> format(Locale.US, "%f", r.record.length));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.US);
    public LiveData<String> recordDate = Transformations.map(record, r -> dateFormat.format(r.record.timeCaught.getTime()));
    public LiveData<String> recordComments = Transformations.map(record, r -> r.record.comments);

    public CatchRecordDisplayViewModel(@NonNull Application application) {
        super(application);
        catchRepo = new DefaultCatchRepository(application);
    }

    public void setRecord(CatchRecordWithPhotos record) {
        this.record.setValue(record);
    }

    public int getRecordUid() {
        return record.getValue().record.uid;
    }

    public LiveData<CatchRecordWithPhotos> lookupRecord(int recordUid) {
        return catchRepo.getCatchRecordWithPhotos(recordUid);
    }
}
