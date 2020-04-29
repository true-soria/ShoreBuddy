package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.catches.CatchPhoto;
import com.example.shorebuddy.data.catches.CatchRepository;
import com.example.shorebuddy.data.catches.DefaultCatchRepository;
import com.example.shorebuddy.data.fish.DefaultFishRepository;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.fish.FishRepository;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;
import com.example.shorebuddy.utilities.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CatchEntryViewModel extends AndroidViewModel {
    private CatchRepository catchRepository;
    private FishRepository fishRepository;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.US);

    private CatchRecordWithPhotos catchRecordWithPhotos = new CatchRecordWithPhotos();

    private MutableLiveData<Event> calendarChanged = new MutableLiveData<>(new Event());
    private LiveData<String> dateText = Transformations.map(calendarChanged, event -> dateFormat.format(catchRecordWithPhotos.record.timeCaught.getTime()));
    private LiveData<String> timeText = Transformations.map(calendarChanged, event -> timeFormat.format(catchRecordWithPhotos.record.timeCaught.getTime()));
    private LiveData<Integer> currentYear = Transformations.map(calendarChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.YEAR));
    private LiveData<Integer> currentMonth = Transformations.map(calendarChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.MONTH));
    private LiveData<Integer> currentDay = Transformations.map(calendarChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.DAY_OF_MONTH));
    private LiveData<Integer> currentHour = Transformations.map(calendarChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.HOUR_OF_DAY));
    private LiveData<Integer> currentMinute = Transformations.map(calendarChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.MINUTE));

    public CatchEntryViewModel(@NonNull Application application) {
        super(application);
        catchRepository = new DefaultCatchRepository(application);
        fishRepository = new DefaultFishRepository(application);

        reset();
    }

    public void recordCatch() {
        catchRepository.recordCatch(catchRecordWithPhotos);
    }

    public void reset() {
        catchRecordWithPhotos = new CatchRecordWithPhotos();
        calendarChanged.setValue(new Event());
    }

    public void updateCalendar(CalendarField calendarField, int value) {
        catchRecordWithPhotos.record.timeCaught.set(calendarField.getField(), value);
        calendarChanged.setValue(new Event());
    }

    public LiveData<Integer> getYear() {
        return currentYear;
    }

    public LiveData<Integer> getMonth() {
        return currentMonth;
    }

    public LiveData<Integer> getDay() {
        return currentDay;
    }

    public LiveData<Integer> getHour() {
        return currentHour;
    }

    public LiveData<Integer> getMinute() {
        return currentMinute;
    }

    public LiveData<String> getCatchRecordDate() {
        return dateText;
    }

    public LiveData<String> getCatchRecordTime() {
        return timeText;
    }

    public LiveData<List<Fish>> getAllFish() { return fishRepository.getAllFish(); }

    public String getLake() {
        return catchRecordWithPhotos.record.lake;
    }

    public void addPhoto(String path) {
        CatchPhoto newPhoto = new CatchPhoto(path);
        catchRecordWithPhotos.photos.add(newPhoto);
    }

    public void setLake(String lake) {
        catchRecordWithPhotos.record.lake = lake;
    }

    public void setFish(String species) {
        catchRecordWithPhotos.record.fish = species;
    }

    public void setWeight(String weight) {
        try {
            catchRecordWithPhotos.record.weight = Double.parseDouble(weight);
        } catch (Exception e) {
            catchRecordWithPhotos.record.weight = 0;
        }
    }

    public void setLength(String length) {
        try {
            catchRecordWithPhotos.record.length = Double.parseDouble(length);
        } catch (Exception e) {
            catchRecordWithPhotos.record.length = 0;
        }
    }

    public void setComments(String comments) {
        catchRecordWithPhotos.record.comments = comments;
    }

    public enum CalendarField {
        YEAR (Calendar.YEAR),
        MONTH (Calendar.MONTH),
        DAY (Calendar.DAY_OF_MONTH),
        HOUR (Calendar.HOUR_OF_DAY),
        MINUTE (Calendar.MINUTE);

        private int field;

        CalendarField(int field) {
            this.field = field;
        }

        private int getField() {
            return field;
        }
    }
}
