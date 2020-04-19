package com.example.shorebuddy.data.catches;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.ShoreBuddyDatabase;

import java.util.List;

public class DefaultCatchRepository implements CatchRepository {
    private CatchesDao catchesDao;

    public DefaultCatchRepository(Application application) {
        ShoreBuddyDatabase db = ShoreBuddyDatabase.getDatabase(application);
        this.catchesDao = db.catchesDao();
    }

    @Override
    public LiveData<List<CatchRecord>> getCatchRecordsDescending() {
        return catchesDao.getAllRecordsDescending();
    }

    @Override
    public void recordCatch(CatchRecord record) {
        ShoreBuddyDatabase.databaseExecutor.execute(() -> catchesDao.insert(record));
    }

    @Override
    public void deleteCatch(CatchRecord record) {
        ShoreBuddyDatabase.databaseExecutor.execute(() -> catchesDao.delete(record));
    }

    @Override
    public void updateCatch(CatchRecord record) {
        ShoreBuddyDatabase.databaseExecutor.execute(() -> catchesDao.update(record));
    }
}
