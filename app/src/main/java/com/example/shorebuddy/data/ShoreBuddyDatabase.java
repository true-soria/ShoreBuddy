package com.example.shorebuddy.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.fish.FishDao;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeDao;
import com.example.shorebuddy.data.relationships.LakeFishCrossRef;
import com.example.shorebuddy.utilities.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lake.class, Fish.class, LakeFishCrossRef.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShoreBuddyDatabase extends RoomDatabase {

    public abstract LakeDao lakeDao();
    public abstract FishDao fishDao();
    private static volatile ShoreBuddyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ShoreBuddyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoreBuddyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoreBuddyDatabase.class, "shore_database")
                            .createFromAsset("ShoreBuddy.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
