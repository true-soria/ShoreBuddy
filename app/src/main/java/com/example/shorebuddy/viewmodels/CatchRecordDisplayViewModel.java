package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.lang.String.*;

public class CatchRecordDisplayViewModel extends AndroidViewModel {
    private MutableLiveData<CatchRecord> record = new MutableLiveData<>(new CatchRecord());
//    private CatchRepository catchRepo;
//    private LiveData<CatchRecordWithPhotos> recordWithPhotos = new MutableLiveData<>(new CatchRecordWithPhotos());
//    private LiveData<CatchRecord> record = Transformations.map(recordWithPhotos,r -> r.record);
//    public LiveData<List> photos = Transformations.map(recordWithPhotos, r ->r.photos);
    public LiveData<String> recordSpecies = Transformations.map(record, r -> r.fish);
    public LiveData<String> recordLake = Transformations.map(record, r -> r.lake);
    public LiveData<String> recordWeight = Transformations.map(record, r -> format(Locale.US, "%f", r.weight));
    public LiveData<String> recordLength = Transformations.map(record, r -> format(Locale.US, "%f", r.length));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.US);
    public LiveData<String> recordDate = Transformations.map(record, r -> dateFormat.format(r.timeCaught.getTime()));
    public LiveData<String> recordComments = Transformations.map(record, r -> r.comments);


    public CatchRecordDisplayViewModel(@NonNull Application application) {
        super(application);
        //catchRepo = new DefaultCatchRepository(application);
    }

    public void setRecord(CatchRecord record) {
        this.record.setValue(record);
        //this.recordWithPhotos=catchRepo.getCatchRecordWithPhotos(record.uid);
    }

    public int getRecordUid() {
        return record.getValue().uid;
    }
}
