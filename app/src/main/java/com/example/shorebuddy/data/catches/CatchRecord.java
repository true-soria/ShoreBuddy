package com.example.shorebuddy.data.catches;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.shorebuddy.data.fish.Fish;
import com.example.shorebuddy.data.lakes.Lake;
import com.example.shorebuddy.utilities.Converters;

import org.jetbrains.annotations.NotNull;

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
public class CatchRecord {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    final int uid;

    public String lake;
    public String fish;

    @TypeConverters({Converters.class})
    public Calendar timeCaught;

    public double weight;

    public String comments;

    public CatchRecord(int uid) {
        this.uid = uid;
    }
}
