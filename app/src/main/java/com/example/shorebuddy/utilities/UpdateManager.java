package com.example.shorebuddy.utilities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UpdateManager {
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>(false);
    private boolean isUpdatingWeather = false;
    private boolean isUpdatingSolunar = false;

    public LiveData<Boolean> isUpdating() { return isUpdating; }

    public void weatherUpdateStarted() {
        isUpdatingWeather = true;
        update();
    }

    public void weatherUpdateFinished() {
        isUpdatingWeather = false;
        update();
    }

    public void solunarUpdateStarted() {
        isUpdatingSolunar = true;
        update();
    }

    public void solunarUpdateFinished() {
        isUpdatingSolunar = false;
        update();
    }

    private void update() {
        isUpdating.setValue(isUpdatingWeather || isUpdatingSolunar);
    }
}
