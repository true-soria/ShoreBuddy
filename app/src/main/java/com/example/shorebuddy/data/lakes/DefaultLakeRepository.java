package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.relationships.LakeWithFish;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;

public class DefaultLakeRepository implements LakeRepository {

    private LakeDao lakeDao;

    public DefaultLakeRepository(Application application){
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        this.lakeDao = db.lakeDao();
    }

    @Override
    public LiveData<List<Lake>> getFilteredLakes(LakeFilter query) {
        if (query.county == null || query.county.equals("")) {
            return lakeDao.getFilteredLakes(query.name.getQuery());
        } else {
            return lakeDao.getFilteredLakes(query.name.getQuery(), query.county);
        }
    }

    @Override
    public LiveData<List<Fish>> getFishInLake(Lake lake) {
        LiveData<LakeWithFish> lakesWithFish = lakeDao.findLakeWithFish(lake.lakeName);
        return Transformations.map(lakesWithFish, lakes -> lakes.fish);
    }

    @Override
    public LiveData<List<String>> getAllCounties() {
        return lakeDao.getAllCounties();
    }
}
