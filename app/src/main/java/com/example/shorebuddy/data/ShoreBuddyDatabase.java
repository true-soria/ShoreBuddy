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
import com.opencsv.bean.OpencsvUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lake.class}, version = 1, exportSchema = false)
public abstract class ShoreBuddyDatabase extends RoomDatabase {

    public abstract LakeDao LakeDao();


    private static volatile ShoreBuddyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ShoreBuddyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoreBuddyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoreBuddyDatabase.class, "shore_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    public static ShoreBuddyDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (ShoreBuddyDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            ShoreBuddyDatabase.class, "lake_table").addCallback(new Callback() {
//                        @Override
//                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                            super.onCreate(db);
//                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        CSVReader reader = new CSVReader(new FileReader("filename.csv"));
//                                        String[] nextLine;
//                                        while((nextLine= reader.readNext())!=null){
//                                            System.out.println("the next line is "+nextLine);
//                                        }
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                    //getDatabase(context).getLakeDao().insertAll(listofLakes);
//                                }
//                            });
//                        }
//                    }).build();
//
//                }
//            }
//        }
//        return INSTANCE;
//  }

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
