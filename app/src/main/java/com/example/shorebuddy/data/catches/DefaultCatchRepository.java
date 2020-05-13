package com.example.shorebuddy.data.catches;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.shorebuddy.data.ShoreBuddyDatabase;
import com.example.shorebuddy.data.relationships.CatchRecordWithPhotos;

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
    public LiveData<List<CatchRecord>> getCatchRecordsFiltered(CatchRecordFilter filter) {
        if (filter.getLake() == null && filter.getFish() == null) {
            return catchesDao.getAllRecordsDescending();
        } else if (filter.getLake() != null && filter.getFish() != null) {
            return catchesDao.getRecordsFilteredLakeFish(filter.getLake(), filter.getFish());
        } else if (filter.getLake() != null) {
            return catchesDao.getRecordsFilteredLake(filter.getLake());
        } else {
            return catchesDao.getRecordsFilteredFish(filter.getFish());
        }
    }

    @Override
    public LiveData<CatchRecordWithPhotos> getCatchRecordWithPhotos(int uid) {
        return catchesDao.getCatchRecordWithPhotos(uid);
    }

    @Override
    public void recordCatch(CatchRecordWithPhotos recordWithPhotos) {
        ShoreBuddyDatabase.databaseExecutor.execute(() -> {
            long rowId = catchesDao.insert(recordWithPhotos.record);
            int newId = catchesDao.getCatchRecordIdFromRow(rowId);
            for (CatchPhoto photo : recordWithPhotos.photos) {
                photo.catchRecordUid = newId;
                catchesDao.insert(photo);
            }
        });
    }

    @Override
    public void deleteCatch(CatchRecord record) {
        ShoreBuddyDatabase.databaseExecutor.execute(() -> catchesDao.delete(record));
    }

    @Override
    public void updateCatch(CatchRecordWithPhotos recordWithPhotos) {
        ShoreBuddyDatabase.databaseExecutor.execute(() -> {
            catchesDao.update(recordWithPhotos.record);
            for (CatchPhoto photo : recordWithPhotos.photos) {
                photo.catchRecordUid = recordWithPhotos.record.uid;
                catchesDao.insert(photo);
            }
        });
    }
}
