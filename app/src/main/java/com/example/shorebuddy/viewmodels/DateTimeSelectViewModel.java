package com.example.shorebuddy.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DateTimeSelectViewModel extends ViewModel {
    private MutableLiveData<Integer> year = new MutableLiveData<>();
    private MutableLiveData<Integer> month = new MutableLiveData<>();
    private MutableLiveData<Integer> day = new MutableLiveData<>();
    private MutableLiveData<Integer> hour = new MutableLiveData<>();
    private MutableLiveData<Integer> minute = new MutableLiveData<>();

    public int shownYear;
    public int shownMonth;
    public int shownDay;
    public int shownHour;
    public int shownMinute;

    public void setYear(int year) {
        this.year.setValue(year);
    }

    public void setMonth(int month) {
        this.month.setValue(month);
    }

    public void setDay(int day) {
        this.day.setValue(day);
    }

    public void setHour(int hour) {
        this.hour.setValue(hour);
    }

    public void setMinute(int minute) {
        this.minute.setValue(minute);
    }

    public LiveData<Integer> getYear() {
        return year;
    }

    public LiveData<Integer> getMonth() {
        return month;
    }

    public LiveData<Integer> getDay() {
        return day;
    }

    public LiveData<Integer> getHour() {
        return hour;
    }

    public LiveData<Integer> getMinute() {
        return minute;
    }
}
