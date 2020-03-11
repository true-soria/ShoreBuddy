package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

public class DefaultLakeRepository implements LakeRepository {

    private LakeDao lakeDao;
    private LiveData<List<Lake>> allLakes;

    DefaultLakeRepository(Application application){
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        this.lakeDao = db.LakeDao();
        this.allLakes = lakeDao.getAllLakes();
    }

    void insert(Lake lake){
        ShoreBuddyDatabase.databaseWriteExecutor.execute(()->{
            lakeDao.insert(lake);
        });
    }
    @Override
    public LiveData<List<Lake>> getAllLakes() {
        return allLakes;
    }

    @Override
    public LiveData<List<Lake>> getFilteredLakes(SearchQuery search) {
        return null;
    }
}
