package com.example.shorebuddy.data.fish;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fish_table")
public class Fish {
    @PrimaryKey
    @NonNull
    @ColumnInfo(index = true)
    public String species;
}
