package com.example.shorebuddy.data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeDao;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lake.class}, version = 1, exportSchema = false)
public abstract class ShoreBuddyDatabase extends RoomDatabase {

    public abstract LakeDao lakeDao();
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
                                    Lake casitas = new Lake("Casitas",34.3924,-119.3346);
                                    Lake pinecrest = new Lake("Pinecrest",38.19606115930,-119.98234788600);
                                    List<Lake> lakes = new ArrayList<Lake>();
                                    lakes.add(casitas);
                                    lakes.add(pinecrest);
                                    getDatabase(context).lakeDao().insertAll(lakes);
//                                    try {
//                                        CSVReader reader = new CSVReader(new FileReader("top10lakes.csv"));
//                                        String[] nextline;
//                                        while((nextline = reader.readNext()) != null){
//                                            //String[] values = line.split(",");
//                                           // System.out.println("Current line is "+line);
//                                            System.out.println(nextline[0]+nextline[1]+nextline[2]);
//                                        }
//                                    } catch (IOException e) {
//                                        Log.wtf("My Activity", "Error reading csv file ", e);
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
