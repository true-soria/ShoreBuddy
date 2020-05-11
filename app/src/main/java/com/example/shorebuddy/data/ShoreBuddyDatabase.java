package com.example.shorebuddy.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shorebuddy.data.catches.CatchPhoto;
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

@Database(entities = {Lake.class, Fish.class, LakeFishCrossRef.class, CatchRecord.class, CatchPhoto.class}, version = 5, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShoreBuddyDatabase extends RoomDatabase {

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
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

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

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS CatchPhotos (" +
                    "path TEXT PRIMARY KEY NOT NULL," +
                    "catchRecordUid INTEGER NOT NULL," +
                    "FOREIGN KEY (catchRecordUid) REFERENCES CatchRecords(uid) ON DELETE CASCADE)");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_CatchPhotos_catchRecordUid ON CatchPhotos(catchRecordUid)");
            database.execSQL("DELETE FROM lakes WHERE name='Casitas Lake'");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("PRAGMA foreign_keys=off");
            database.execSQL("BEGIN TRANSACTION");
            database.execSQL("ALTER TABLE CatchPhotos RENAME TO old_catch_photo");
            database.execSQL("DROP INDEX IF EXISTS index_CatchPhotos_catchRecordUid");
            database.execSQL("CREATE TABLE IF NOT EXISTS CatchPhotos (" +
                    "path TEXT NOT NULL," +
                    "catchRecordUid INTEGER NOT NULL," +
                    "FOREIGN KEY (catchRecordUid) REFERENCES CatchRecords(uid) ON DELETE CASCADE," +
                    "CONSTRAINT pk_catch_photos PRIMARY KEY (path, catchRecordUid))");
            database.execSQL("INSERT INTO CatchPhotos SELECT * FROM old_catch_photo");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_CatchPhotos_catchRecordUid ON CatchPhotos(catchRecordUid)");
            database.execSQL("DROP TABLE old_catch_photo");
            database.execSQL("COMMIT");
            database.execSQL("PRAGMA foreign_keys=on");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("PRAGMA foreign_keys=off");
            database.execSQL("BEGIN TRANSACTION");
            database.execSQL("ALTER TABLE LakeFishCrossRef RENAME TO old_lake_fish_cross_ref");
            database.execSQL("DROP INDEX IF EXISTS index_LakeFishCrossRef_species");
            database.execSQL("CREATE TABLE IF NOT EXISTS LakeFishCrossRef (" +
                    "name TEXT NOT NULL," +
                    "species TEXT NOT NULL," +
                    "FOREIGN KEY (name) REFERENCES lakes(name) ON DELETE CASCADE," +
                    "FOREIGN KEY (species) REFERENCES fish(species) ON DELETE CASCADE," +
                    "CONSTRAINT pk_lakefishcrossref PRIMARY KEY (name, species))");
            database.execSQL("INSERT INTO LakeFishCrossRef SELECT * FROM old_lake_fish_cross_ref");
            database.execSQL("CREATE INDEX IF NOT EXISTS index_Lake_FishCrossRef_species ON LakeFishCrossRef(species)");
            database.execSQL("DROP TABLE old_lake_fish_cross_ref");
            database.execSQL("COMMIT");
            database.execSQL("PRAGMA foreign_keys=on");
            database.execSQL("UPDATE lakes SET county='Butte' WHERE name='Sly Creek Reservoir'");
            database.execSQL("DELETE FROM lakes WHERE name='Casitas Lake'");
        }
    };
}
