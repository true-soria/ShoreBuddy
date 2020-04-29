package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.catches.CatchPhoto;
import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.data.fish.DefaultFishRepository;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.fish.FishRepository;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CatchEntryViewModel extends AndroidViewModel {
    private CatchRepository catchRepository;
    private FishRepository fishRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    private MediatorLiveData<Calendar> catchRecordCalendar = new MediatorLiveData<>();
    private MutableLiveData<Integer> inputMinute = new MutableLiveData<>();
    private MutableLiveData<Integer> inputHour = new MutableLiveData<>();
    private MutableLiveData<Integer> inputDay = new MutableLiveData<>();
    private MutableLiveData<Integer> inputMonth = new MutableLiveData<>();
    private MutableLiveData<Integer> inputYear = new MutableLiveData<>();
    private LiveData<String> dateText = Transformations.map(catchRecordCalendar, calendar -> dateFormat.format(calendar.getTime()));
    private LiveData<String> timeText = Transformations.map(catchRecordCalendar, calendar -> timeFormat.format(calendar.getTime()));

    private CatchRecordWithPhotos record;

    public CatchEntryViewModel(@NonNull Application application) {
        super(application);
        catchRepository = new DefaultCatchRepository(application);
        fishRepository = new DefaultFishRepository(application);

        catchRecordCalendar.addSource(inputMinute, value -> {
            Calendar updatedCalendar = catchRecordCalendar.getValue();
            updatedCalendar.set(Calendar.MINUTE, value);
            catchRecordCalendar.setValue(updatedCalendar);
        });
        catchRecordCalendar.addSource(inputHour, value -> {
            Calendar updatedCalendar = catchRecordCalendar.getValue();
            updatedCalendar.set(Calendar.HOUR_OF_DAY, value);
            catchRecordCalendar.setValue(updatedCalendar);
        });
        catchRecordCalendar.addSource(inputDay, value -> {
            Calendar updatedCalendar = catchRecordCalendar.getValue();
            updatedCalendar.set(Calendar.DAY_OF_MONTH, value);
            catchRecordCalendar.setValue(updatedCalendar);
        });
        catchRecordCalendar.addSource(inputMonth, value -> {
            Calendar updatedCalendar = catchRecordCalendar.getValue();
            updatedCalendar.set(Calendar.MONTH, value);
            catchRecordCalendar.setValue(updatedCalendar);
        });
        catchRecordCalendar.addSource(inputYear, value -> {
            Calendar updatedCalendar = catchRecordCalendar.getValue();
            updatedCalendar.set(Calendar.YEAR, value);
            catchRecordCalendar.setValue(updatedCalendar);
        });

        reset();
    }

    public void recordCatch() {
        record.record.timeCaught = catchRecordCalendar.getValue();
        catchRepository.recordCatch(record);
    }

    public void resetCalendar() {
        catchRecordCalendar.setValue(Calendar.getInstance());
    }

    private void reset() {
        resetCalendar();
        record = new CatchRecordWithPhotos();
    }

    public void setYear(int year) {
        inputYear.setValue(year);
    }

    public void setMonth(int month) {
        inputMonth.setValue(month);
    }

    public void setDay(int day) {
        inputDay.setValue(day);
    }

    public void setHour(int hour) {
        inputHour.setValue(hour);
    }

    public void setMinute(int minute) {
        inputMinute.setValue(minute);
    }

    public void setLake(String lake) {
        record.record.lake = lake;
    }

    public LiveData<Integer> getYear() {
        return Transformations.map(catchRecordCalendar, calendar -> calendar.get(Calendar.YEAR));
    }
    public LiveData<Integer> getMonth() {
        return Transformations.map(catchRecordCalendar, calendar -> calendar.get(Calendar.MONTH));
    }
    public LiveData<Integer> getDay() {
        return Transformations.map(catchRecordCalendar, calendar -> calendar.get(Calendar.DAY_OF_MONTH));
    }
    public LiveData<Integer> getHour() {
        return Transformations.map(catchRecordCalendar, calendar -> calendar.get(Calendar.HOUR_OF_DAY));
    }
    public LiveData<Integer> getMinute() {
        return Transformations.map(catchRecordCalendar, calendar -> calendar.get(Calendar.MINUTE));
    }

    public String getLake() {
        return record.record.lake;
    }

    public void setFish(String species) {
        record.record.fish = species;
    }

    public void setWeight(String weight) {
        try {
            record.record.weight = Double.parseDouble(weight);
        } catch (Exception e) {
            record.record.weight = 0;
        }
    }

    public void setLength(String length) {
        try {
            record.record.length = Double.parseDouble(length);
        } catch (Exception e) {
            record.record.length = 0;
        }
    }

    public void setComments(String comments) {
        record.record.comments = comments;
    }

    public void addPhoto(String path) {
        CatchPhoto newPhoto = new CatchPhoto(path);
        record.photos.add(newPhoto);
    }

    public LiveData<String> getCatchRecordDate() {
        return dateText;
    }

    public LiveData<String> getCatchRecordTime() {
        return timeText;
    }

    public LiveData<List<Fish>> getAllFish() { return fishRepository.getAllFish(); }
}
