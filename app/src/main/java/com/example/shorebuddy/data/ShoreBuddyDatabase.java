package com.example.shorebuddy.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeDao;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lake.class}, version = 1, exportSchema = false)
public abstract class ShoreBuddyDatabase extends RoomDatabase {

    public static LakeDao LakeDao() {
        return null;
    }


    private static volatile ShoreBuddyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    public static ShoreBuddyDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (ShoreBuddyDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            ShoreBuddyDatabase.class, "shore_database")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    public static ShoreBuddyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoreBuddyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoreBuddyDatabase.class, "lake_table").addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {

                                    Lake casitas = new Lake("Casitas");
                                    casitas.setLatitude(34.3924);
                                    casitas.setLongitude(-119.3346);
                                    Lake pinecrest = new Lake("Pinecrest");
                                    pinecrest.setLatitude(38.19606115930);
                                    pinecrest.setLongitude(-119.98234788600);
                                    LakeDao().insert(casitas);
                                    LakeDao().insert(pinecrest);

//                                    lakes.add(casitas);
//                                    lakes.add(pinecrest);
//                                    allLakes.setValue(lakes);
//                                    try {
//                                        CSVReader reader = new CSVReader(new FileReader("filename.csv"));
//                                        String[] nextLine;
//                                        System.out.println("Checking this new database \n\n");
////                                        while((nextLine= reader.readNext())!=null){
////                                            System.out.println("the next line is "+nextLine);
////                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }

                                }
                            });
                        }
                    }).build();

                }
            }
        }
        return INSTANCE;
  }

    ShoreBuddyDatabase getDataBase(){return INSTANCE;}

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
