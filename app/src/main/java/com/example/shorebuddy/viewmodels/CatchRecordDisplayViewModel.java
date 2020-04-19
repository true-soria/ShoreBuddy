package com.example.shorebuddy.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.shorebuddy.data.catches.CatchRecord;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static java.lang.String.*;

public class CatchRecordDisplayViewModel extends ViewModel {
    private MutableLiveData<CatchRecord> record = new MutableLiveData<>(new CatchRecord());

    public LiveData<String> recordSpecies = Transformations.map(record, r -> r.fish);
    public LiveData<String> recordLake = Transformations.map(record, r -> r.lake);
    public LiveData<String> recordWeight = Transformations.map(record, r -> format(Locale.US, "%f", r.weight));
    public LiveData<String> recordLength = Transformations.map(record, r -> format(Locale.US, "%f", r.length));
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a", Locale.US);
    public LiveData<String> recordDate = Transformations.map(record, r -> dateFormat.format(r.timeCaught.getTime()));
    public LiveData<String> recordComments = Transformations.map(record, r -> r.comments);

    public void setRecord(CatchRecord record) {
        this.record.setValue(record);
    }
}
