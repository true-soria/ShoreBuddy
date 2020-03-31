package com.example.shorebuddy.data;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.shorebuddy.R;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.data.lakes.LakeDao;
import com.example.shorebuddy.utilities.Converters;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Lake.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ShoreBuddyDatabase extends RoomDatabase {

    public abstract LakeDao lakeDao();
    private static InputStream csvStream;
    private static volatile ShoreBuddyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ShoreBuddyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ShoreBuddyDatabase.class) {
                if (INSTANCE == null) {
                    csvStream = context.getResources().openRawResource(R.raw.top10lakes);
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ShoreBuddyDatabase.class, "shore_database").addCallback(dataBaseCallBack)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static ShoreBuddyDatabase.Callback dataBaseCallBack = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NotNull SupportSQLiteDatabase db){
            super.onOpen(db);
            databaseWriteExecutor.execute( ()->{
                LakeDao lakeDao = INSTANCE.lakeDao();
                lakeDao.removeAll();
                BufferedReader reader = null;
                List<Lake> lakes = new ArrayList<Lake>();
                String line ="";
                Lake currentLake;
                ArrayList<String> fishList = new ArrayList<String>();
                try {
                    reader = new BufferedReader(new InputStreamReader(csvStream, StandardCharsets.UTF_8));
                    line = reader.readLine();
                    String[] categories = line.split(",");
                    while((line = reader.readLine()) != null){
                        String[] values = line.split(",");
                        for(int i =1;i<values.length-2;i++){
                            if(values[i].equals("1")){
                                fishList.add(categories[i]);
                            }
                        }
                        currentLake = new Lake(values[0],Double.parseDouble(values[20]),Double.parseDouble(values[19]),fishList);
                        lakes.add(currentLake);
                        fishList.clear();
                    }
                    reader.close();
                } catch (IOException e) {
                    Log.wtf("Error reading File",e);
                    e.printStackTrace();
                }
                lakeDao.insertAll(lakes);
            });
        }
    };
}
