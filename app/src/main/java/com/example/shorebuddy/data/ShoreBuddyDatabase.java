package com.example.shorebuddy.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shorebuddy.data.catches.CatchRecord;
import com.example.shorebuddy.data.catches.CatchesDao;
import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.fish.FishDao;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeDao;
import com.example.shorebuddy.data.relationships.LakeFishCrossRef;
import com.example.shorebuddy.utilities.Converters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lake.class, Fish.class, LakeFishCrossRef.class, CatchRecord.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShoreBuddyDatabase extends RoomDatabase {

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS CatchRecords (" +
                    "uid INTEGER PRIMARY KEY NOT NULL," +
                    "lake TEXT," +
                    "fish TEXT," +
                    "timeCaught INTEGER," +
                    "weight REAL NOT NULL DEFAULT 0," +
                    "length REAL NOT NULL DEFAULT 0," +
                    "comments TEXT," +
                    "FOREIGN KEY (lake) REFERENCES lakes(name) ON DELETE RESTRICT," +
                    "FOREIGN KEY (fish) REFERENCES fish(species) ON DELETE RESTRICT)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_CatchRecords_lake ON CatchRecords (lake)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_CatchRecords_fish ON CatchRecords (fish)");
        }
    };

    public abstract LakeDao lakeDao();
    public abstract FishDao fishDao();
    public abstract CatchesDao catchesDao();
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
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
