package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.utilities.SearchQuery;

import java.util.List;
import java.util.Vector;

public class DefaultLakeRepository implements LakeRepository {

    private LakeDao lakeDao;
    private final Vector<Lake> lakes = new Vector<>();
    private final MutableLiveData<List<Lake>> filteredLakes = new MutableLiveData<>();
    private MutableLiveData<List<Lake>> allLakes = new MutableLiveData<>();

    public DefaultLakeRepository(Application application){
        System.out.println("Creating the repo\n\n");
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        this.lakeDao = ShoreBuddyDatabase.LakeDao();
        this.allLakes = (MutableLiveData<List<Lake>>) lakeDao.getAllLakes();
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
        Vector<Lake> vec = new Vector<>();
        for (Lake lake: lakes) {
            if (lake.name.toLowerCase().contains(query.getRawString().toLowerCase())) {
                vec.add(lake);
            }
        }
        filteredLakes.setValue(vec);
        return filteredLakes;
    }
}
