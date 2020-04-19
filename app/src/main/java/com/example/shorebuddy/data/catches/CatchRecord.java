package com.example.shorebuddy.data.catches;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.utilities.Converters;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Calendar;

@Entity(
        tableName = "CatchRecords",
        indices = {@Index(value="lake"),@Index(value="fish")},
        foreignKeys = { @ForeignKey(entity = Lake.class,
                                    parentColumns = "name",
                                    childColumns = "lake",
                                    onDelete = ForeignKey.RESTRICT),
                        @ForeignKey(entity = Fish.class,
                                    parentColumns = "species",
                                    childColumns = "fish",
                                    onDelete = ForeignKey.RESTRICT)})
public class CatchRecord implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    final int uid;

    public String lake;
    public String fish;

    @TypeConverters({Converters.class})
    public Calendar timeCaught;

    @ColumnInfo(defaultValue = "0")
    public double weight;

    @ColumnInfo(defaultValue = "0")
    public double length;

    public String comments;

    @Ignore
    public CatchRecord() {
        uid = 0;
    }

    CatchRecord(int uid) {
        this.uid = uid;
    }
}
