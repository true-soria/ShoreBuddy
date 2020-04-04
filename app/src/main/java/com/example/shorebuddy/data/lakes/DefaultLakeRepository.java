package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.relationships.LakeWithFish;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.ArrayList;
import java.util.List;

public class DefaultLakeRepository implements LakeRepository {

    private LakeDao lakeDao;
    private LiveData<List<Lake>> allLakes;

    public DefaultLakeRepository(Application application){
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        this.lakeDao = db.lakeDao();
        this.allLakes = lakeDao.getAll();
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
    public LiveData<List<Lake>> getFilteredLakes(SearchQuery query) {
        return lakeDao.getFilteredLakes(query.getQuery());
    }

    @Override
    public List<Fish> getFishInLake(Lake lake) {
        List<LakeWithFish> lakesWithFish = lakeDao.findLakeWithFish(lake.lakeName);
        if (lakesWithFish.isEmpty()) {
            return new ArrayList<Fish>();
        } else {
            return lakesWithFish.get(0).fish;
        }
    }
}
