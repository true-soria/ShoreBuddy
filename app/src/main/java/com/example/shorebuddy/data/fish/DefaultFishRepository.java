package com.example.shorebuddy.data.fish;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.ShoreBuddyDatabase;

import java.util.List;

public class DefaultFishRepository implements FishRepository {
    private FishDao fishDao;

    public DefaultFishRepository(Application application) {
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        fishDao = db.fishDao();
    }

    @Override
    public LiveData<List<Fish>> getAllFish() {
        return fishDao.getAllFish();
    }
}
