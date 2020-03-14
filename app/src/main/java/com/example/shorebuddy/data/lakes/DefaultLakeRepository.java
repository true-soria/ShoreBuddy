package com.example.shorebuddy.data.lakes;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.utilities.SearchQuery;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class DefaultLakeRepository implements LakeRepository {

    private LakeDao lakeDao;
    private MutableLiveData<List<Lake>> filteredLakes = new MutableLiveData<>();
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
        Vector<Lake> vec = new Vector<>();
        for (Lake lake: Objects.requireNonNull(this.allLakes.getValue())) {
            if (lake.name.toLowerCase().contains(query.getRawString().toLowerCase())) {
                vec.add(lake);
            }
        }
        filteredLakes.setValue(vec);
        return filteredLakes;
    }
}
