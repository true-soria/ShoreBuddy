package com.example.shorebuddy.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.R;
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

    private MutableLiveData<Event> recordChanged = new MutableLiveData<>(new Event());
    private LiveData<String> dateText = Transformations.map(recordChanged, event -> dateFormat.format(catchRecordWithPhotos.record.timeCaught.getTime()));
    private LiveData<String> timeText = Transformations.map(recordChanged, event -> timeFormat.format(catchRecordWithPhotos.record.timeCaught.getTime()));
    private LiveData<Integer> currentYear = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.YEAR));
    private LiveData<Integer> currentMonth = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.MONTH));
    private LiveData<Integer> currentDay = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.DAY_OF_MONTH));
    private LiveData<Integer> currentHour = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.HOUR_OF_DAY));
    private LiveData<Integer> currentMinute = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.timeCaught.get(Calendar.MINUTE));
    private LiveData<String> currentLake = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.lake);
    private LiveData<String> currentFish = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.fish);
    private LiveData<Double> currentWeight = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.weight);
    private LiveData<Double> currentLength = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.length);
    private LiveData<String> currentComments = Transformations.map(recordChanged, event -> catchRecordWithPhotos.record.comments);
    private MutableLiveData<Mode> entryMode = new MutableLiveData<>(Mode.CREATE);
    private LiveData<Integer> modeIcon = Transformations.map(entryMode, mode -> {
        if (mode == Mode.CREATE) {
            return R.drawable.ic_add_black_24dp;
        } else {
            return R.drawable.ic_save_black_24dp;
        }
    });

    public CatchEntryViewModel(@NonNull Application application) {
        super(application);
        catchRepository = new DefaultCatchRepository(application);
        fishRepository = new DefaultFishRepository(application);

        reset();
    }

    public void recordCatch() {
        if (entryMode.getValue() == Mode.CREATE) {
            catchRepository.recordCatch(catchRecordWithPhotos);
        } else {
            catchRepository.updateCatch(catchRecordWithPhotos);
        }
    }

    public void reset() {
        entryMode.setValue(Mode.CREATE);
        catchRecordWithPhotos = new CatchRecordWithPhotos();
        recordChanged.setValue(new Event());
    }

    public void editRecord(CatchRecordWithPhotos record) {
        catchRecordWithPhotos = record;
        entryMode.setValue(Mode.EDIT);
        recordChanged.setValue(new Event());
    }

    public LiveData<Integer> getModeIcon() {
        return modeIcon;
    }

    public void updateCalendar(CalendarField calendarField, int value) {
        catchRecordWithPhotos.record.timeCaught.set(calendarField.getField(), value);
        recordChanged.setValue(new Event());
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

    public LiveData<String> getLake() {
        return currentLake;
    }

    public LiveData<String> getFish() {
        return currentFish;
    }

    public LiveData<Double> getWeight() {
        return currentWeight;
    }

    public LiveData<Double> getLength() {
        return currentLength;
    }

    public LiveData<String> getComments() {
        return currentComments;
    }

    public void addPhoto(String path) {
        CatchPhoto newPhoto = new CatchPhoto(path);
        catchRecordWithPhotos.photos.add(newPhoto);
    }

    public void setLake(String lake) {
        catchRecordWithPhotos.record.lake = lake;
        recordChanged.setValue(new Event());
    }

    public void setFish(String species) {
        catchRecordWithPhotos.record.fish = species;
        recordChanged.setValue(new Event());
    }

    public void setWeight(String weight) {
        try {
            catchRecordWithPhotos.record.weight = Double.parseDouble(weight);
        } catch (Exception e) {
            catchRecordWithPhotos.record.weight = 0;
        }
        recordChanged.setValue(new Event());
    }

    public void setLength(String length) {
        try {
            catchRecordWithPhotos.record.length = Double.parseDouble(length);
        } catch (Exception e) {
            catchRecordWithPhotos.record.length = 0;
        }
        recordChanged.setValue(new Event());
    }

    public void setComments(String comments) {
        catchRecordWithPhotos.record.comments = comments;
        recordChanged.setValue(new Event());
    }

    public LiveData<CatchRecordWithPhotos> findCatchRecord(int recordUid) {
        return catchRepository.getCatchRecordWithPhotos(recordUid);
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

    public enum Mode {
        CREATE,
        EDIT,
    }
}
