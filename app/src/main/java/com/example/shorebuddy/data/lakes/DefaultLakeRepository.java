package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.relationships.LakeWithFish;

import java.util.List;

public class DefaultLakeRepository implements LakeRepository {

    private LakeDao lakeDao;

    public DefaultLakeRepository(Application application){
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        this.lakeDao = db.lakeDao();
    }

    @Override
    public LiveData<List<String>> getFilteredLakes(LakeFilter query) {
        if (query.county == null && query.fish == null) {
            return lakeDao.getFilteredLakes(query.name.getQuery());
        } else if (query.county != null && query.fish != null) {
            return lakeDao.getFilteredLakes(query.name.getQuery(), query.getFishSpecies(), query.county);
        } else if (query.county != null) {
            return lakeDao.getFilteredLakesCounty(query.name.getQuery(), query.county);
        } else {
            return lakeDao.getFilteredLakesSpecies(query.name.getQuery(), query.getFishSpecies());
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

    @Override
    public LiveData<Lake> getLake(String lakeName) {
        return lakeDao.findLake(lakeName);
    }
}
